/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.logic.behavior.asset;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.reflections.Reflections;
import org.terasology.asset.AssetLoader;
import org.terasology.engine.CoreRegistry;
import org.terasology.engine.module.Module;
import org.terasology.engine.module.ModuleManager;
import org.terasology.logic.behavior.BehaviorNodeComponent;
import org.terasology.logic.behavior.BehaviorNodeFactory;
import org.terasology.logic.behavior.RenderableNodeFactory;
import org.terasology.logic.behavior.tree.Node;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author synopia
 */
public class BehaviorTreeLoader implements AssetLoader<BehaviorTreeData> {
    private BehaviorTreeGson treeGson = new BehaviorTreeGson();
    private RenderableBehaviorTreeGson renderableTreeGson = new RenderableBehaviorTreeGson(this);
    private Reflections reflections;

    public void save(OutputStream stream, BehaviorTreeData data) throws IOException {
        try (JsonWriter write = new JsonWriter(new OutputStreamWriter(stream))) {
            write.setIndent("  ");
            write.beginObject().name("model");
            treeGson.saveTree(write, data.getRoot());
            if (data.hasRenderable()) {
                write.name("renderer");
                renderableTreeGson.saveTree(write, data.getRenderableRoot());
            }
            write.endObject();
        }
    }

    @Override
    public BehaviorTreeData load(Module module, InputStream stream, List<URL> urls) throws IOException {
        BehaviorTreeData data = new BehaviorTreeData();
        try (JsonReader reader = new JsonReader(new InputStreamReader(stream))) {
            reader.setLenient(true);
            reader.beginObject();
            nextName(reader, "model");
            data.setRoot(treeGson.loadTree(reader));
            if (reader.hasNext()) {
                nextName(reader, "renderer");
                data.setRenderableRoot(renderableTreeGson.loadTree(reader));
            }
            reader.endObject();
        }
        return data;
    }

    public String nextName(JsonReader in, String expectedName) throws IOException {
        String name = in.nextName();
        if (!expectedName.equals(name)) {
            throw new RuntimeException(expectedName + " expected!");
        }
        return name;
    }

    private class BehaviorTreeGson {
        private int currentId;
        private Map<Node, Integer> nodeIds = Maps.newHashMap();
        private Map<Integer, Node> idNodes = Maps.newHashMap();

        private Gson gsonNode;

        private BehaviorTreeGson() {
            gsonNode = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapterFactory(new NodeTypeAdapterFactory())
                    .create();
        }

        public Node loadTree(JsonReader reader) {
            resetIds();
            return gsonNode.fromJson(reader, Node.class);
        }

        public void saveTree(JsonWriter writer, Node root) {
            resetIds();
            gsonNode.toJson(root, Node.class, writer);
        }

        public Node getNode(int id) {
            return idNodes.get(id);
        }

        public int getId(Node node) {
            return nodeIds.get(node);
        }

        private void resetIds() {
            idNodes.clear();
            nodeIds.clear();
            currentId = 0;
        }

        private class NodeTypeAdapterFactory implements TypeAdapterFactory {
            @Override
            public <T> TypeAdapter<T> create(final Gson gson, TypeToken<T> type) {
                final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
                return new TypeAdapter<T>() {
                    @Override
                    public void write(JsonWriter out, T value) throws IOException {
                        if (value instanceof Node) {
                            idNodes.put(currentId, (Node) value);
                            nodeIds.put((Node) value, currentId);

                            TypeAdapter<T> delegateAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(NodeTypeAdapterFactory.this, TypeToken.get(value.getClass()));
                            out.beginObject()
                                    .name("nodeType").value(value.getClass().getCanonicalName())
                                    .name("nodeId").value(currentId);
                            currentId++;
                            out.name("node");
                            delegateAdapter.write(out, value);
                            out.endObject();
                        } else {
                            delegate.write(out, value);
                        }

                    }

                    @Override
                    public T read(JsonReader in) throws IOException {
                        if (in.peek() == JsonToken.BEGIN_OBJECT) {
                            in.beginObject();
                            nextName(in, "nodeType");
                            String nodeType = in.nextString();
                            ClassLoader[] classLoaders = CoreRegistry.get(ModuleManager.class).getActiveModuleReflections().getConfiguration().getClassLoaders();
                            Class cls = null;
                            for (ClassLoader classLoader : classLoaders) {
                                try {
                                    cls = (Class<? extends Node>) classLoader.loadClass(nodeType);
                                    break;
                                } catch (ClassNotFoundException e) {
                                }
                            }
                            TypeAdapter<T> delegateAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(NodeTypeAdapterFactory.this, TypeToken.get(cls));
                            nextName(in, "nodeId");
                            int id = in.nextInt();
                            nextName(in, "node");
                            T read = delegateAdapter.read(in);
                            idNodes.put(id, (Node) read);
                            nodeIds.put((Node) read, id);
                            in.endObject();
                            return read;
                        } else {
                            return delegate.read(in);
                        }
                    }
                };
            }
        }
    }

    private class RenderableBehaviorTreeGson {
        private Gson gsonNode;
        private BehaviorTreeLoader loader;
        
        private RenderableBehaviorTreeGson(BehaviorTreeLoader loader) {
        	this.loader = loader;
            gsonNode = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeHierarchyAdapter(Node.class, new NodeTypeAdapter())
                    .registerTypeHierarchyAdapter(Node.class, new RenderableNodeTypeAdapter())
                    .create();

        }

        public Node loadTree(JsonReader reader) {
            return gsonNode.fromJson(reader, Node.class);
        }

        public void saveTree(JsonWriter writer, Node node) {
            gsonNode.toJson(node, Node.class, writer);
        }


        private class NodeTypeAdapter extends TypeAdapter<Node> {
            @Override
            public void write(JsonWriter out, Node value) throws IOException {
                int id = treeGson.getId(value);
                out.value(id);
            }

            @Override
            public Node read(JsonReader in) throws IOException {
                int id = in.nextInt();
                return treeGson.getNode(id);
            }
        }

        private class RenderableNodeTypeAdapter extends TypeAdapter<Node> {

        	@Override
            public void write(JsonWriter out, Node value) throws IOException {
            	
        		out.beginObject()
                        .name("node").value(treeGson.getId(value));
        				CoreRegistry.get(RenderableNodeFactory.class).JsonWriterWrite(out, value);
                        out.name("children");
                gsonNode.toJson(value.children(), List.class, out);
                out.endObject();
            }

            @Override
            public Node read(JsonReader in) throws IOException {
                in.beginObject();
                nextName(in, "node");
                int id = in.nextInt();
                Node node = treeGson.getNode(id);
                BehaviorNodeComponent nodeComponent = CoreRegistry.get(BehaviorNodeFactory.class).getNodeComponent(node);
				Node renderableNode = CoreRegistry.get(RenderableNodeFactory.class).getNode(node, nodeComponent);

				CoreRegistry.get(RenderableNodeFactory.class).JsonWriterRead(loader, in, renderableNode);
                
                nextName(in, "children");
                in.beginArray();
                int i = 0;
                while (in.hasNext()) {
                    Node child = gsonNode.fromJson(in, Node.class );
                    renderableNode.setChild(i, child);
                    i++;
                }
                in.endArray();
                in.endObject();
                return renderableNode;
            }
        }
    }
}
