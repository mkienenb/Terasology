/*
 * Copyright 2014 MovingBlocks
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
package org.terasology.rendering.techne;

import javax.vecmath.Vector3f;

public class TechneBox {
    // Unfortunately, modders store the texture size in this class.
    // Maybe we don't really need it though.  It seems like it's also being set here.
    private TechneModel techneModel;

    private Vector3f origin;
    private Vector3f size;
    
    private int textureSizeX;
    private int textureSizeY;
    
    // For now, allow the following to be stored and directly accessed by modules,
    // but they should be replaced by accessors as time goes by.

    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;

    public boolean mirror;

    private int textureOffsetX;
    private int textureOffsetY;

    // Accessors
    
    public TechneModel getTechneModel() {
        return techneModel;
    }

    public void setTechneModel(TechneModel techneModel) {
        this.techneModel = techneModel;
    }

    public Vector3f getOrigin() {
        return origin;
    }
    public void setOrigin(Vector3f origin) {
        this.origin = origin;
    }
    public Vector3f getSize() {
        return size;
    }
    public void setSize(Vector3f size) {
        this.size = size;
    }
    
    // For now, we have to use the public accessors for backwards compatibility
    public Vector3f getRotationPoint() {
        return new Vector3f(rotationPointX, rotationPointY, rotationPointZ);
    }
    public void setRotationPoint(Vector3f rotationPoint) {
        this.rotationPointX = rotationPoint.x;
        this.rotationPointY = rotationPoint.y;
        this.rotationPointZ = rotationPoint.z;
    }

    public Vector3f getRotationAngle() {
        return new Vector3f(rotateAngleX, rotateAngleY, rotateAngleZ);
    }
    public void setRotationAngle(Vector3f rotationAngle) {
        this.rotateAngleX = rotationAngle.x;
        this.rotateAngleY = rotationAngle.y;
        this.rotateAngleZ = rotationAngle.z;
    }
    public boolean isMirror() {
        return mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    public float getRotationPointX() {
        return rotationPointX;
    }

    public void setRotationPointX(float rotationPointX) {
        this.rotationPointX = rotationPointX;
    }

    public float getRotationPointY() {
        return rotationPointY;
    }

    public void setRotationPointY(float rotationPointY) {
        this.rotationPointY = rotationPointY;
    }

    public float getRotationPointZ() {
        return rotationPointZ;
    }

    public void setRotationPointZ(float rotationPointZ) {
        this.rotationPointZ = rotationPointZ;
    }

    public int getTextureOffsetX() {
        return textureOffsetX;
    }

    public void setTextureOffsetX(int textureOffsetX) {
        this.textureOffsetX = textureOffsetX;
    }

    public int getTextureOffsetY() {
        return textureOffsetY;
    }

    public void setTextureOffsetY(int textureOffsetY) {
        this.textureOffsetY = textureOffsetY;
    }

    public int getTextureSizeX() {
        return textureSizeX;
    }

    public void setTextureSizeX(int textureSizeX) {
        this.textureSizeX = textureSizeX;
    }

    public int getTextureSizeY() {
        return textureSizeY;
    }

    public void setTextureSizeY(int textureSizeY) {
        this.textureSizeY = textureSizeY;
    }

    public void setTextureSize(int textureSizeX, int textureSizeY) {
        this.textureSizeX = textureSizeX;
        this.textureSizeY = textureSizeY;
    }

    // model creation and maintenance
    
    public void addBox(float originX, float originY, float originZ, int sizeX, int sizeY, int sizeZ) {
        // TODO: Maybe we need to handle the case where more than one box is added to a model down the road?
        setOrigin(new Vector3f(originX, originY, originZ));
        setSize(new Vector3f(sizeX, sizeY, sizeZ));
    }

    public void setRotationPoint(float rotationPointX, float rotationPointY, float rotationPointZ) {
        setRotationPoint(new Vector3f(rotationPointX, rotationPointY, rotationPointZ));
    }


}
