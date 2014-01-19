package org.terasology.logic.behavior;

import java.io.IOException;

import org.terasology.logic.behavior.asset.BehaviorTreeLoader;
import org.terasology.logic.behavior.tree.Node;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public interface RenderableNodeFactory {
	public Node getNode(Node node, BehaviorNodeComponent nodeComponent);
	public void JsonWriterWrite(JsonWriter out, Node value) throws IOException;
	public void JsonWriterRead(BehaviorTreeLoader loader, JsonReader in, Node value) throws IOException;
}
