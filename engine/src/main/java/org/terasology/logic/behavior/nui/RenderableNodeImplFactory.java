package org.terasology.logic.behavior.nui;

import java.io.IOException;

import javax.vecmath.Vector2f;

import org.terasology.logic.behavior.BehaviorNodeComponent;
import org.terasology.logic.behavior.RenderableNodeFactory;
import org.terasology.logic.behavior.asset.BehaviorTreeLoader;
import org.terasology.logic.behavior.tree.Node;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class RenderableNodeImplFactory implements RenderableNodeFactory {

	@Override
	public Node getNode(Node node, BehaviorNodeComponent nodeComponent) {
		return new RenderableNodeImpl(node, nodeComponent);
	}

	@Override
	public void JsonWriterRead(BehaviorTreeLoader loader, JsonReader in, Node value) throws IOException {
    	RenderableNodeImpl impl = (RenderableNodeImpl)value;
    	loader.nextName(in, "position");
        in.beginArray();
        float x = (float) in.nextDouble();
        float y = (float) in.nextDouble();
        in.endArray();
        impl.setPosition(x, y);
        loader.nextName(in, "size");
        in.beginArray();
        x = (float) in.nextDouble();
        y = (float) in.nextDouble();
        in.endArray();
        impl.setSize(new Vector2f(x, y));
	}

	@Override
	public void JsonWriterWrite(JsonWriter out, Node value) throws IOException {
    	RenderableNodeImpl impl = (RenderableNodeImpl)value;
    	out.name("position").beginArray().value(impl.getPosition().x).value(impl.getPosition().y).endArray()
        .name("size").beginArray().value(impl.getSize().x).value(impl.getSize().y).endArray();
	}
}
