package org.terasology.logic.behavior.tree;

import java.util.List;

public interface Node {

    public interface Visitor<T> {
        T visit(T item, Node node);
    }

	public void insertChild(int index, Node child);

	public void setChild(int index, Node child);

	public Node removeChild(int index);

	public Node getChild(int index);

	public int getChildrenCount();

	public int getMaxChildren();

	/**
	 * Visitor pattern to visit the complete tree.
	 */
	public <T> T visit(T item, Visitor<T> visitor);

    public List<? extends Node> children();

    public Task createTask();
}