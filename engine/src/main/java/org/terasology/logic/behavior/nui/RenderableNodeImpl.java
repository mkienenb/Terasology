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
package org.terasology.logic.behavior.nui;

import java.util.List;

import javax.vecmath.Vector2f;

import org.terasology.asset.Assets;
import org.terasology.entitySystem.Component;
import org.terasology.input.MouseInput;
import org.terasology.logic.behavior.BehaviorNodeComponent;
import org.terasology.logic.behavior.tree.Node;
import org.terasology.logic.behavior.tree.Status;
import org.terasology.logic.behavior.tree.Task;
import org.terasology.logic.behavior.tree.TreeAccessor;
import org.terasology.math.Vector2i;
import org.terasology.rendering.assets.TextureRegion;
import org.terasology.rendering.nui.BaseInteractionListener;
import org.terasology.rendering.nui.Canvas;
import org.terasology.rendering.nui.CoreWidget;
import org.terasology.rendering.nui.InteractionListener;
import org.terasology.rendering.nui.baseLayouts.ZoomableLayout;

import com.google.common.collect.Lists;

/**
 * @author synopia
 */
public class RenderableNodeImpl extends CoreWidget implements Node, ZoomableLayout.PositionalWidget<BehaviorEditor>, Component, TreeAccessor<Node> {
    private transient TextureRegion texture = Assets.getTextureRegion("engine:button");

    private final List<RenderableNodeImpl> children = Lists.newArrayList();
    private transient PortList portList;

    private Node node;
    private Vector2f position;
    private Vector2f size;
    private transient TreeAccessor<Node> withoutModel;
    private transient TreeAccessor<Node> withModel;
    private transient BehaviorNodeComponent data;
    private transient Vector2i last;
    private transient BehaviorEditor editor;
    private transient boolean dragged;
    private transient Status status;

    private transient InteractionListener moveListener = new BaseInteractionListener() {
        @Override
        public void onMouseOver(Vector2i pos, boolean topMostElement) {
        }

        @Override
        public boolean onMouseClick(MouseInput button, Vector2i pos) {
            last = pos;
            dragged = false;
            return true;
        }

        @Override
        public void onMouseRelease(MouseInput button, Vector2i pos) {
            if( !dragged ) {
                editor.nodeClicked(RenderableNodeImpl.this);
            }
            dragged = false;
        }

        @Override
        public void onMouseDrag(Vector2i pos) {
            Vector2f diff = editor.screenToWorld(pos);
            diff.sub(editor.screenToWorld(last));
            if( diff.lengthSquared()!=0 ) {
                dragged = true;
            }
            move(diff);

            last = pos;
        }
    };

    public RenderableNodeImpl(Node node, BehaviorNodeComponent data) {
    	this.node = node;
    	this.data = data;
        position = new Vector2f();
        size = new Vector2f(10, 5);
        portList = new PortList(this);
        withoutModel = new ChainedTreeAccessor<>(this, portList);
        withModel = new ChainedTreeAccessor<>(this, portList, new NodeTreeAccessor());
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawTexture(texture);
        String text = getData().name + " "+(status!=null?status:"");
        canvas.drawText(text);

        if( editor!=null ) {
            canvas.addInteractionRegion(moveListener);
        }
        portList.onDraw(canvas);
    }

    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint) {
        return sizeHint;
    }

    public void update() {
        List<RenderableNodeImpl> all = Lists.newArrayList(children);
        children.clear();
        for (Node renderableNode : all) {
            withoutModel.insertChild(-1, renderableNode);
        }
    }

    @Override
    public void onAdded(BehaviorEditor layout) {
        this.editor = layout;
    }

    @Override
    public void onRemoved(BehaviorEditor layout) {
        this.editor = null;
    }

    public TreeAccessor<Node> withoutModel() {
        return withoutModel;
    }

    public TreeAccessor<Node> withModel() {
        return withModel;
    }

    public PortList getPortList() {
        return portList;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        position = new Vector2f(x, y);
    }

    public void move(Vector2f diff) {
        position = new Vector2f(position);
        position.add(diff);
        for (Node child : children) {
        	RenderableNodeImpl impl = (RenderableNodeImpl)child;
        	impl.move(diff);
        }
    }

    public BehaviorEditor getEditor() {
        return editor;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

   private Node getNode() {
        return node;
    }

    public BehaviorNodeComponent getData() {
        return data;
    }

    public Port.InputPort getInputPort() {
        return getPortList().getInputPort();
    }

    public Iterable<Port> getPorts() {
        return getPortList().ports();
    }

    public void insertChild(int index, Node child) {
    	RenderableNodeImpl childImpl = (RenderableNodeImpl)child;
        if (index == -1) {
            children.add(childImpl);
        } else {
            children.add(index, childImpl);
        }
    }

    public void setChild(int index, Node child) {
        if (children.size() == index) {
            children.add(null);
        }
        if (children.get(index) != null) {
            Node childAtIndex = children.get(index);
        	RenderableNodeImpl impl = (RenderableNodeImpl)childAtIndex;
			Port.InputPort inputPort = impl.getInputPort();
            inputPort.setTarget(null);
        }
    	RenderableNodeImpl childImpl = (RenderableNodeImpl)child;
        children.set(index, childImpl);
    }

    public Node removeChild(int index) {
    	Node remove = children.remove(index);
    	RenderableNodeImpl impl = (RenderableNodeImpl)remove;
    	impl.getInputPort().setTarget(null);
        return remove;
    }

    public Node getChild(int index) {
        if (children.size() > index) {
            return children.get(index);
        }
        return null;
    }

    public int getChildrenCount() {
        return children.size();
    }

    @Override
    public List<? extends Node> children() {
        return children;
    }

    @Override
    public int getMaxChildren() {
        return getNode().getMaxChildren();
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public Vector2f getSize() {
        return size;
    }

    @Override
    public String toString() {
        return getNode() != null ? getNode().toString() : "";
    }

	public <T> T visit(T item, Visitor<T> visitor) {
		T visit = visitor.visit(item, this);
        for (Node child : children) {
            child.visit(visit, visitor);
        }
        return visit;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public class NodeTreeAccessor implements TreeAccessor<Node> {
        @Override
        public void insertChild(int index, Node child) {
            getNode().insertChild(index, child);
        }

        @Override
        public void setChild(int index, Node child) {
            getNode().setChild(index, child);
        }

        @Override
        public RenderableNodeImpl removeChild(int index) {
            getNode().removeChild(index);
            return null;
        }

        @Override
        public RenderableNodeImpl getChild(int index) {
            return null;
        }

        @Override
        public int getChildrenCount() {
            return getNode().getChildrenCount();
        }

        @Override
        public int getMaxChildren() {
            return getNode().getMaxChildren();
        }
    }

	@Override
	public Task createTask() {
		return getNode().createTask();
	}
}
