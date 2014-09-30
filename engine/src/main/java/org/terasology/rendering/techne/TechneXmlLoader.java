/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.rendering.techne;

import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.vecmath.Vector3f;

import org.eaxy.Document;
import org.eaxy.Element;
import org.eaxy.ElementSet;
import org.eaxy.Xml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.rendering.assets.skeletalmesh.SkeletalMeshDataBuilder;

/**
 * Importer for Techne model files.
 * 
 * http://schwarzeszeux.tumblr.com/post/13854104445/minecraft-modeling-tutorial-part-1-of-x
 * The upper box is at origin (0, 0, 0), the lower one directly on the ground (0, 23, 0).
 * 
 * mlk: (Note that a block is not used directly in these models -- just for comparison.)
 * 
 * A block measures 16 x 16 x 16 pixels which translates to real 1m x 1m x 1m.
 * 
 * new ModelRenderer(this, 18, 4); will create us a new instance of ModelRenderer,
 *   you pass an instance of your model-class as first argument, texture-offset-X as second
 *   and texture-offset-Y as third.
 * 
 * addBox takes 7 arguments, in order: offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ and
 *   extension. I’ll explain what “offset” does (compared to “position”
 *   as set by “setRotationPoint”) later and we can ignore “extension” for now.
 * 
 * setRotationPoint will set the position of this box, the rotation-point (or anchor-point)
 *   is the point in space around which the model will rotate.
 * 
 * To rotate a box you use the public fields rotateAngleX-Z.
 *   Let’s say we want the tail to be rotated by -45°, as shown in the above image.
 *   It’s important to note that rotateAngleX-Z take radians. 360° == 2pi in radians,
 *   a quick calculation of pi/180 * -45 gives us -0.7854 which means that we end up with.
 *   
 * From TheyCallMeDanger:
 *  +x is to the right.
 *  +z goes back away.
 *  +y goes down!
 *  
 *  mlk: (Need to negate y coordinate to match block coordinate system)
 *  
 * @author mkienenb@gmail.com
 */

public class TechneXmlLoader {

    private static final Logger logger = LoggerFactory.getLogger(TechneXmlLoader.class);

    protected TFloatList vertices;
    protected TFloatList texCoord0;
    protected TFloatList texCoord1;
    protected TFloatList normals;
    protected TFloatList colors;
    protected TIntList indices;
    protected double unitsPerMeter;

    protected SkeletalMeshDataBuilder skeletonBuilder;

    protected void parseSkeletalMeshData(InputStream inputStream) throws TechneParseException, IOException {
        Document document = Xml.readAndClose(inputStream);
        Element rootElement = document.getRootElement();

        parseMeshData(rootElement);
        parseSkeletalMeshData(rootElement);
    }

    protected void parseMeshData(InputStream inputStream) throws TechneParseException, IOException {
        Document document = Xml.readAndClose(inputStream);
        Element rootElement = document.getRootElement();

        parseMeshData(rootElement);
    }

    protected void parseSkeletalMeshData(Element rootElement) throws TechneParseException {
        // Unimplemented
    }

    protected void parseMeshData(Element rootElement) throws TechneParseException {

        vertices = new TFloatArrayList();
        texCoord0 = new TFloatArrayList();
        texCoord1 = new TFloatArrayList();
        normals = new TFloatArrayList();
        colors = new TFloatArrayList();
        indices = new TIntArrayList();

        // unknown yet what the default scale is
        unitsPerMeter = 0.05d;
        boolean yUp = true;
        boolean zUp = false;
        boolean xUp = false;

        ElementSet shapeSet = rootElement.find("Models", "Model", "Geometry", "Shape");
        for (Element shape : shapeSet) {

            logger.info("Parsing shape name=" + shape.name());
            
            String shapeType = shape.attr("type");
            if (!"d9e621f7-957f-4b77-b1ae-20dcd0da7751".equals(shapeType)) {
                throw new TechneParseException("Found unsupported " + shapeType + " shape type for shape name=" + shape.name());
            }

            ElementSet positionElementSet = shape.find("Position");
            if (1 != positionElementSet.size()) {
                throw new TechneParseException("Found multiple position asset values for shape name=" + shape.name());
            }
            Element positionElement = positionElementSet.first();
            String positionString = positionElement.text();
            String[] positionArray = getItemsInString(positionString, ",");
            if (positionArray.length != 3) {
                throw new TechneParseException("Did not find three coordinates for position  " + positionString + " for shape name=" + shape.name());
            }

            ElementSet rotationElementSet = shape.find("Rotation");
            if (1 != rotationElementSet.size()) {
                throw new TechneParseException("Found multiple rotation asset values for shape name=" + shape.name());
            }
            Element rotationElement = rotationElementSet.first();
            String rotationString = rotationElement.text();
            String[] rotationArray = getItemsInString(rotationString, ",");
            if (rotationArray.length != 3) {
                throw new TechneParseException("Did not find three coordinates for rotation  " + rotationString + " for shape name=" + shape.name());
            }

            ElementSet sizeElementSet = shape.find("Size");
            if (1 != sizeElementSet.size()) {
                throw new TechneParseException("Found multiple size asset values for shape name=" + shape.name());
            }
            Element sizeElement = sizeElementSet.first();
            String sizeString = sizeElement.text();
            String[] sizeArray = getItemsInString(sizeString, ",");
            if (sizeArray.length != 3) {
                throw new TechneParseException("Did not find three coordinates for size  " + sizeString + " for shape name=" + shape.name());
            }

            ElementSet textureOffsetElementSet = shape.find("TextureOffset");
            if (1 != textureOffsetElementSet.size()) {
                throw new TechneParseException("Found multiple textureOffset asset values for shape name=" + shape.name());
            }
            Element textureOffsetElement = textureOffsetElementSet.first();
            String textureOffsetString = textureOffsetElement.text();
            String[] textureOffsetArray = getItemsInString(textureOffsetString, ",");
            if (textureOffsetArray.length != 2) {
                throw new TechneParseException("Did not find two coordinates for Texture Offset  " + textureOffsetString + " for shape name=" + shape.name());
            }

            ElementSet offsetElementSet = shape.find("Offset");
            if (1 != offsetElementSet.size()) {
                throw new TechneParseException("Found multiple offset asset values for shape name=" + shape.name());
            }
            Element offsetElement = offsetElementSet.first();
            String offsetString = offsetElement.text();
            String[] offsetArray = getItemsInString(offsetString, ",");
            if (offsetArray.length != 3) {
                throw new TechneParseException("Did not find three coordinates for offset  " + offsetString + " for shape name=" + shape.name());
            }

            ElementSet isMirroredElementSet = shape.find("IsMirrored");
            if (1 != isMirroredElementSet.size()) {
                throw new TechneParseException("Found multiple isMirrored asset values for shape name=" + shape.name());
            }
            Element isMirroredElement = isMirroredElementSet.first();
            String isMirroredString = isMirroredElement.text();
            boolean isMirrored;
            if ("True".equals(isMirroredString)) {
                isMirrored = true;
            } else if ("False".equals(isMirroredString)) {
                isMirrored = false;
            } else {
                throw new TechneParseException("Unknown value for IsMirrored " + isMirroredString + " for shape name=" + shape.name());
            }
            
            float positionX = Float.parseFloat(positionArray[0]);
            float positionY = Float.parseFloat(positionArray[1]);
            float positionZ = Float.parseFloat(positionArray[2]);
            Vector3f positionVector = new Vector3f(positionX, positionY, positionZ);
            
            float sizeX = Float.parseFloat(sizeArray[0]);
            float sizeY = Float.parseFloat(sizeArray[1]);
            float sizeZ = Float.parseFloat(sizeArray[2]);
            Vector3f sizeVector = new Vector3f(sizeX, sizeY, sizeZ);

            float rotationX = Float.parseFloat(rotationArray[0]);
            float rotationY = Float.parseFloat(rotationArray[1]);
            float rotationZ = Float.parseFloat(rotationArray[2]);
            Vector3f rotationVector = new Vector3f(rotationX, rotationY, rotationZ);
            
            // Offset is rotational center
            float offsetX = Float.parseFloat(offsetArray[0]);
            float offsetY = Float.parseFloat(offsetArray[1]);
            float offsetZ = Float.parseFloat(offsetArray[2]);
            Vector3f offsetVector = new Vector3f(offsetX, offsetY, offsetZ);
            
            int vertexCountBefore = vertices.size();
            
            // divide by 2 for extent
            Box box = new Box(positionVector, sizeX * 0.5f, sizeY * 0.5f, sizeZ * 0.5f);
            // TODO: scale
            // TODO: rotate
            // TODO: mirror
            
            box.addMeshData(indices, vertices, normals, rotationVector, offsetVector);
            
            int vertexCountAfter = vertices.size();
            int newVertexTriplets = vertexCountAfter - vertexCountBefore;

            // for now, fake color vertexes until we figure out textures
            float redColor = (float)Math.random();
            float greenColor = (float)Math.random();
            float blueColor = (float)Math.random();
            
            // From when we created all colors after all parts of the model were done.
            // int colorsToCreate = vertices.size() / 3;
            int colorsToCreate = newVertexTriplets / 3;
            for (int i = 0; i < colorsToCreate; i++) {
                colors.add(redColor);
                colors.add(greenColor);
                colors.add(blueColor);
                colors.add(1.0f);
            }
        }
    }

    private String[] getItemsInString(String dataString, String separator) {
        String string = dataString.replaceAll("\n", " ");
        string = string.replaceAll("\t", " ");
        string = string.replaceAll("\r", " ");
        while (string.contains("  ")) {
            string = string.replaceAll("  ", " ");
        }
        string = string.trim();
        String[] floatStrings = string.split(separator);
        return floatStrings;
    }

    // http://math.stackexchange.com/questions/304700/how-to-generate-an-ordered-list-of-vertices-of-a-cube-from-a-face-and-a-normal-v
    
    
    public static void main(String[] args) {
        
        
        
        TechneXmlLoader loader = new TechneXmlLoader();
        try {
            File file = new File("/home/mkienenb/workspaces/Terasology-checkout/sometechnesourcefilesfromorespawn/ant.tcn_FILES/model.xml");
            loader.parseMeshData(new FileInputStream(file));
        } catch (IOException | TechneParseException e) {
            e.printStackTrace();
        }
    }

    protected class TechneParseException extends Exception {
        private static final long serialVersionUID = 1L;

        public TechneParseException(String msg) {
            super(msg);
        }
    }
}
