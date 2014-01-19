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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.FixedNodeExtentProvider;
import org.terasology.asset.AssetData;
import org.terasology.engine.CoreRegistry;
import org.terasology.logic.behavior.BehaviorNodeComponent;
import org.terasology.logic.behavior.BehaviorNodeFactory;
import org.terasology.logic.behavior.RenderableNodeFactory;
import org.terasology.logic.behavior.nui.RenderableNodeImpl;
import org.terasology.logic.behavior.tree.Node;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author synopia
 */
public class BehaviorTreeData implements AssetData {
    private Map<Node, Node> renderableNodes = Maps.newHashMap();
    private Node root;
    private Node renderableRoot;

    public void setRoot(Node root) {
        this.root = root;
    }

    public void setRenderableRoot(Node renderableRoot) {
        this.renderableRoot = renderableRoot;
    }

    public Node createNode(Node node) {
        BehaviorNodeComponent nodeComponent = CoreRegistry.get(BehaviorNodeFactory.class).getNodeComponent(node);
        Node self = CoreRegistry.get(RenderableNodeFactory.class).getNode(node, nodeComponent);
        renderableNodes.put(node, self);
        return self;
    }

    public void createRenderable() {
        renderableRoot = createRenderable(root);
    }

    public Node createRenderable(Node root) {
        return root.visit(null, new Node.Visitor<Node>() {
            @Override
            public Node visit(Node parent, Node node) {
                Node self = createNode(node);
                if (parent != null) {
                	RenderableNodeImpl parentImpl = (RenderableNodeImpl)parent;
                	parentImpl.withoutModel().insertChild(-1, self);
                }
                return self;
            }
        });
    }

    public void layout(Node start) {
        if( start==null ) {
            start = renderableRoot;
        }
        TreeLayout<Node> layout = new TreeLayout<>(new LayoutTree(start), new FixedNodeExtentProvider(10,5), new DefaultConfiguration(4,2));
        Map<Node,Rectangle2D.Double> bounds = layout.getNodeBounds();
        for (Map.Entry<Node, Rectangle2D.Double> entry : bounds.entrySet()) {
            Node node = entry.getKey();
            Rectangle2D.Double rect = entry.getValue();
        	RenderableNodeImpl impl = (RenderableNodeImpl)node;
        	impl.setPosition((float) rect.getX(), (float) rect.getY() );
        }
    }

    public boolean hasRenderable() {
        return renderableRoot != null;
    }

    public Node getRoot() {
        return root;
    }

    public Node getRenderableRoot() {
        return renderableRoot;
    }

    public List<Node> getRenderableNodes() {
        return Lists.newArrayList(renderableNodes.values());
    }

    public Node getRenderableNode(Node node) {
        return renderableNodes.get(node);
    }

    private static class LayoutTree implements TreeForTreeLayout<Node> {
        private Node root;

        private LayoutTree(Node root) {
            this.root = root;
        }

        @Override
        public Node getRoot() {
            return root;
        }

        @Override
        public boolean isLeaf(Node uiWidgets) {
            return root.getMaxChildren()==0;
        }

        @Override
        public boolean isChildOfParent(Node node, Node parentNode) {
            return parentNode.children().contains(node);
        }

        @Override
        public Iterable<Node> getChildren(Node parentNode) {
            return (List<Node>)parentNode.children();
        }

        @Override
        public Iterable<Node> getChildrenReverse(Node parentNode) {
            ArrayList<Node> list = Lists.newArrayList(parentNode.children());
            Collections.reverse(list);
            return list;
        }

        @Override
        public Node getFirstChild(Node parentNode) {
            return parentNode.getChild(0);
        }

        @Override
        public Node getLastChild(Node parentNode) {
            return parentNode.getChild(Math.max(0,parentNode.getChildrenCount()-1));
        }
    }
}
