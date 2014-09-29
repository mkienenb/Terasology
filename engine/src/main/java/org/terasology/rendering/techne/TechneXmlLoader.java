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

import gnu.trove.iterator.TFloatIterator;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        boolean yUp = false;
        boolean zUp = true;
        boolean xUp = false;

        ElementSet shapeSet = rootElement.find("Models", "Model", "Geometry", "Shape");
        for (Element shape : shapeSet) {

            logger.info("Parsing shape name=" + shape.name());
            
            String shapeType = shape.attr("type");
            if (!"d9e621f7-957f-4b77-b1ae-20dcd0da7751".equals(shapeType)) {
                throw new TechneParseException("Found unsupported " + shapeType + " shape type for shape name=" + shape.name());
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

            ElementSet positionElementSet = shape.find("Position");
            if (1 != positionElementSet.size()) {
                throw new TechneParseException("Found multiple position asset values for shape name=" + shape.name());
            }
            Element positionElement = positionElementSet.first();
            String positionString = offsetElement.text();
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
            
            
            Box box = new Box(positionVector, sizeVector);
            // TODO: scale
            // TODO: rotate
            // TODO: mirror
            
            box.addMeshData(indices, vertices, normals);
        }
        
        // for now, fake color vertexes until we figure out textures
        int colorsToCreate = vertices.size() * 4 / 3;
        for (int i = 0; i < colorsToCreate; i++) {
            colors.add(0.5f);
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
