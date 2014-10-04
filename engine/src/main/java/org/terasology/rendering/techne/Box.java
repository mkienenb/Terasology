/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
// $Id: Box.java 4131 2009-03-19 20:15:28Z blaine.dev $
package org.terasology.rendering.techne;

import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

/**
 * Using box generator constants from 
 * https://raw.githubusercontent.com/jMonkeyEngine/jmonkeyengine/master/jme3-core/src/main/java/com/jme3/scene/shape/Box.java
 */
public class Box extends AbstractBox {
    
    private static final int[] GEOMETRY_INDICES_DATA = {
         2,  1,  0,  3,  2,  0, // back
         6,  5,  4,  7,  6,  4, // right
        10,  9,  8, 11, 10,  8, // front
        14, 13, 12, 15, 14, 12, // left
        18, 17, 16, 19, 18, 16, // top
        22, 21, 20, 23, 22, 20  // bottom
    };

    private static final float[] GEOMETRY_NORMALS_DATA = {
        0,  0, -1,  0,  0, -1,  0,  0, -1,  0,  0, -1, // back
        1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0, // right
        0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1, // front
       -1,  0,  0, -1,  0,  0, -1,  0,  0, -1,  0,  0, // left
        0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0, // top
        0, -1,  0,  0, -1,  0,  0, -1,  0,  0, -1,  0  // bottom
    };

    private static final float[] GEOMETRY_TEXTURE_DATA = {
        1, 0, 0, 0, 0, 1, 1, 1, // back
        1, 0, 0, 0, 0, 1, 1, 1, // right
        1, 0, 0, 0, 0, 1, 1, 1, // front
        1, 0, 0, 0, 0, 1, 1, 1, // left
        1, 0, 0, 0, 0, 1, 1, 1, // top
        1, 0, 0, 0, 0, 1, 1, 1  // bottom
    };

    /**
     * Creates a new box.
     * <p>
     * The box has a center of 0,0,0 and extends in the out from the center by
     * the given amount in <em>each</em> direction. So, for example, a box
     * with extent of 0.5 would be the unit cube.
     *
     * @param x the size of the box along the x axis, in both directions.
     * @param y the size of the box along the y axis, in both directions.
     * @param z the size of the box along the z axis, in both directions.
     */
    public Box(float x, float y, float z) {
        super();
        updateGeometry(JME_Vector3f.ZERO, x, y, z);
    }

    /**
     * Creates a new box.
     * <p>
     * The box has the given center and extends in the out from the center by
     * the given amount in <em>each</em> direction. So, for example, a box
     * with extent of 0.5 would be the unit cube.
     * 
     * @deprecated Due to constant confusion of geometry centers and the center
     * of the box mesh this method has been deprecated.
     * 
     * @param center the center of the box.
     * @param x the size of the box along the x axis, in both directions.
     * @param y the size of the box along the y axis, in both directions.
     * @param z the size of the box along the z axis, in both directions.
     */
    @Deprecated
    public Box(JME_Vector3f center, float x, float y, float z) {
        super();
        updateGeometry(center, x, y, z);
    }

    public Box(Vector3f center, float x, float y, float z) {
        super();
        updateGeometry(new JME_Vector3f(center.x, center.y, center.z), x, y, z);
    }

    /**
     * Constructor instantiates a new <code>Box</code> object.
     * <p>
     * The minimum and maximum point are provided, these two points define the
     * shape and size of the box but not it's orientation or position. You should
     * use the {@link com.jme3.scene.Spatial#setLocalTranslation(com.JME_Vector3f.math.Vector3f) }
     * and {@link com.jme3.scene.Spatial#setLocalRotation(com.jme3.math.Quaternion) }
     * methods to define those properties.
     * 
     * @param min the minimum point that defines the box.
     * @param max the maximum point that defines the box.
     */
    public Box(JME_Vector3f min, JME_Vector3f max) {
        super();
        updateGeometry(min, max);
    }

    /**
     * Empty constructor for serialization only. Do not use.
     */
    public Box(){
        super();
    }

    public Box(Vector3f origin, Vector3f sizeVector) {
        JME_Vector3f min = new JME_Vector3f(
                Math.min(origin.x, origin.x + sizeVector.x), 
                Math.min(origin.y, origin.y + sizeVector.y), 
                Math.min(origin.z, origin.z + sizeVector.z));
        JME_Vector3f max = new JME_Vector3f(
                Math.max(origin.x, origin.x + sizeVector.x), 
                Math.max(origin.y, origin.y + sizeVector.y), 
                Math.max(origin.z, origin.z + sizeVector.z));
        updateGeometry(min, max);
    }

    /**
     * Creates a clone of this box.
     * <p>
     * The cloned box will have '_clone' appended to it's name, but all other
     * properties will be the same as this box.
     */
    @Override
    public Box clone() {
        return new Box(center.clone(), xExtent, yExtent, zExtent);
    }

    protected void duUpdateGeometryIndices() {
//        if (getBuffer(Type.Index) == null){
//            setBuffer(Type.Index, 3, BufferUtils.createShortBuffer(GEOMETRY_INDICES_DATA));
//        }
    }

    protected void duUpdateGeometryNormals() {
//        if (getBuffer(Type.Normal) == null){
//            setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(GEOMETRY_NORMALS_DATA));
//        }
    }

    protected void duUpdateGeometryTextures() {
//        if (getBuffer(Type.TexCoord) == null){
//            setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(GEOMETRY_TEXTURE_DATA));
//        }
    }

    protected void duUpdateGeometryVertices() {
//        FloatBuffer fpb = BufferUtils.createVector3Buffer(24);
        JME_Vector3f[] v = computeVertices();
//        fpb.put(new float[] {
//                v[0].x, v[0].y, v[0].z, v[1].x, v[1].y, v[1].z, v[2].x, v[2].y, v[2].z, v[3].x, v[3].y, v[3].z, // back
//                v[1].x, v[1].y, v[1].z, v[4].x, v[4].y, v[4].z, v[6].x, v[6].y, v[6].z, v[2].x, v[2].y, v[2].z, // right
//                v[4].x, v[4].y, v[4].z, v[5].x, v[5].y, v[5].z, v[7].x, v[7].y, v[7].z, v[6].x, v[6].y, v[6].z, // front
//                v[5].x, v[5].y, v[5].z, v[0].x, v[0].y, v[0].z, v[3].x, v[3].y, v[3].z, v[7].x, v[7].y, v[7].z, // left
//                v[2].x, v[2].y, v[2].z, v[6].x, v[6].y, v[6].z, v[7].x, v[7].y, v[7].z, v[3].x, v[3].y, v[3].z, // top
//                v[0].x, v[0].y, v[0].z, v[5].x, v[5].y, v[5].z, v[4].x, v[4].y, v[4].z, v[1].x, v[1].y, v[1].z  // bottom
//        });
//        setBuffer(Type.Position, 3, fpb);
//        updateBound();
    }


    public void addMeshData(TIntList indices, TFloatList vertices, TFloatList normals) {
        int vertexesSoFar = vertices.size() / 3;
        for (int GEOMETRY_INDEX_DATA : GEOMETRY_INDICES_DATA) {
            indices.add(GEOMETRY_INDEX_DATA + vertexesSoFar);
        }
        JME_Vector3f[] v = computeVertices();
        float[] vertexes = new float[] {
          v[0].x, v[0].y, v[0].z, v[1].x, v[1].y, v[1].z, v[2].x, v[2].y, v[2].z, v[3].x, v[3].y, v[3].z, // back
          v[1].x, v[1].y, v[1].z, v[4].x, v[4].y, v[4].z, v[6].x, v[6].y, v[6].z, v[2].x, v[2].y, v[2].z, // right
          v[4].x, v[4].y, v[4].z, v[5].x, v[5].y, v[5].z, v[7].x, v[7].y, v[7].z, v[6].x, v[6].y, v[6].z, // front
          v[5].x, v[5].y, v[5].z, v[0].x, v[0].y, v[0].z, v[3].x, v[3].y, v[3].z, v[7].x, v[7].y, v[7].z, // left
          v[2].x, v[2].y, v[2].z, v[6].x, v[6].y, v[6].z, v[7].x, v[7].y, v[7].z, v[3].x, v[3].y, v[3].z, // top
          v[0].x, v[0].y, v[0].z, v[5].x, v[5].y, v[5].z, v[4].x, v[4].y, v[4].z, v[1].x, v[1].y, v[1].z  // bottom
        };
        vertices.add(vertexes);
        normals.add(GEOMETRY_NORMALS_DATA);
    }

    public void addMeshData(TIntList indices, TFloatList vertices, TFloatList normals, 
                            Vector3f rotationAngleInDegrees, Vector3f rotationalOffset,
                            int textureOffsetX, int textureOffsetY) {
        
        int invertPivot = 1;
        int yAdjust = 0;
                
        Vector3f translation1 = new Vector3f(
                center.x,
                center.y + yAdjust,
                center.z);

        int vertexesSoFar = vertices.size() / 3;
        for (int GEOMETRY_INDEX_DATA : GEOMETRY_INDICES_DATA) {
            indices.add(GEOMETRY_INDEX_DATA + vertexesSoFar);
        }
        JME_Vector3f[] JME_v = computeVertices();
        
        Matrix4f rotXMatrix = new Matrix4f();
        rotXMatrix.rotX((float)(rotationAngleInDegrees.x * Math.PI / 180));
        Matrix4f rotYMatrix = new Matrix4f();
        rotYMatrix.rotY((float)(rotationAngleInDegrees.y * Math.PI / 180));
        Matrix4f rotZMatrix = new Matrix4f();
        rotZMatrix.rotZ((float)(rotationAngleInDegrees.z * Math.PI / 180));
        
        Vector3f[] v = new Vector3f[JME_v.length];

        for (int i = 0; i < v.length; i++) {

            // Rotating
            JME_Vector3f JME_point = JME_v[i];
            
            // translating both center and rotational offset
            
//          Vector3f point = new Vector3f(
//              JME_point.x,
//              JME_point.y,
//              JME_point.z);


            Vector3f point = new Vector3f(
                    JME_point.x - translation1.x * invertPivot,
                    JME_point.y - translation1.y * invertPivot,
                    JME_point.z - translation1.z * invertPivot);

//            Vector3f point = new Vector3f(
//                    JME_point.x - rotationalOffset.x * invertPivot,
//                    JME_point.y - rotationalOffset.y * invertPivot,
//                    JME_point.z - rotationalOffset.z * invertPivot);

            Vector3f rotatedPoint1 = new Vector3f();
            rotZMatrix.transform(point, rotatedPoint1);

            Vector3f rotatedPoint2 = new Vector3f();
            rotYMatrix.transform(rotatedPoint1, rotatedPoint2);

            Vector3f rotatedPoint3 = new Vector3f();
            rotXMatrix.transform(rotatedPoint2, rotatedPoint3);

            // trying pre-both, post-none 
            // trying pre-both, post-both
            // trying pre-both, post-rotationalOffset
            // trying pre-both, post-translation1

            // pre-rotationalOffset, post-center
            // pre-rotationalOffset, post-none
            // pre-rotationalOffset, post-rotationalOffset
            // pre-rotationalOffset, post-both
            
            // pre-none, post-both

            // pre-center, post-both

            Vector3f newPoint0 = new Vector3f(
                    rotatedPoint3.x + rotationalOffset.x * invertPivot,
                    rotatedPoint3.y + rotationalOffset.y * invertPivot,
                    rotatedPoint3.z + rotationalOffset.z * invertPivot);

            Vector3f newPoint = new Vector3f(
                    newPoint0.x + translation1.x * invertPivot,
                    newPoint0.y + translation1.y * invertPivot,
                    newPoint0.z + translation1.z * invertPivot);
            
//            Vector3f newPoint1 = new Vector3f(
//                    newPoint.x * xExtent,
//                    newPoint.y * yExtent,
//                    newPoint.z * zExtent );
            
            v[i] = newPoint;
            
            // Non-rotating
//            Vector3f point = new Vector3f(
//                    JME_point.x,
//                    JME_point.y,
//                    JME_point.z);
//            v[i] = point;
        }
        
//        // http://www.cprogramming.com/tutorial/3d/rotation.html
//        double theta;
//        double c = Math.cos(theta);
//        double s = Math.sin(theta);
//        double t = 1 - Math.cos(theta);
//        double X, Y, Z;
//        
//        Matrix4d rotationMatrix = new Matrix4d(new double[] {
//                t * X * X + c,          t * X * Y + s * Z,      t * X * Z - s * Y,      0,
//                t * Y * Z - s * Z,      t * Y * Y + c,          t * Y * Z + s * X,      0,
//                t * X * Z + s * Y,      t * Y * Z - s * X,      t * Z * Z + c,          0,
//                0,                      0,                      0,                      1
//        });
        
        float[] vertexes = new float[] {
          v[0].x, v[0].y, v[0].z, v[1].x, v[1].y, v[1].z, v[2].x, v[2].y, v[2].z, v[3].x, v[3].y, v[3].z, // back
          v[1].x, v[1].y, v[1].z, v[4].x, v[4].y, v[4].z, v[6].x, v[6].y, v[6].z, v[2].x, v[2].y, v[2].z, // right
          v[4].x, v[4].y, v[4].z, v[5].x, v[5].y, v[5].z, v[7].x, v[7].y, v[7].z, v[6].x, v[6].y, v[6].z, // front
          v[5].x, v[5].y, v[5].z, v[0].x, v[0].y, v[0].z, v[3].x, v[3].y, v[3].z, v[7].x, v[7].y, v[7].z, // left
          v[2].x, v[2].y, v[2].z, v[6].x, v[6].y, v[6].z, v[7].x, v[7].y, v[7].z, v[3].x, v[3].y, v[3].z, // top
          v[0].x, v[0].y, v[0].z, v[5].x, v[5].y, v[5].z, v[4].x, v[4].y, v[4].z, v[1].x, v[1].y, v[1].z  // bottom
        };
        vertices.add(vertexes);
        normals.add(GEOMETRY_NORMALS_DATA);
    }

    
}