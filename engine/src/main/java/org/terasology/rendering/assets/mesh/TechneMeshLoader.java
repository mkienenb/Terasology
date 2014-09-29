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
package org.terasology.rendering.assets.mesh;

import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.asset.AssetLoader;
import org.terasology.module.Module;
import org.terasology.rendering.techne.TechneXmlLoader;

/**
 * Importer for Techne model files.  Supports mesh data
 *
 * @author mkienenb@gmail.com
 */

public class TechneMeshLoader extends TechneXmlLoader implements AssetLoader<MeshData> {

    private static final Logger logger = LoggerFactory.getLogger(TechneMeshLoader.class);

    @Override
    public MeshData load(Module module, InputStream s, List<URL> urls, List<URL> deltas) throws IOException {
        logger.info("Loading mesh for " + urls);

        ZipInputStream zipStream = new ZipInputStream(s);

        try {
            ZipEntry entry;
            while((entry = zipStream.getNextEntry())!=null) {
                if ("model.xml".equals(entry.getName())) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zipStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    
                    byte[] bytes = baos.toByteArray();
                    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                    
                    try {
                        parseMeshData(bais);
                    } catch (TechneParseException e) {
                        logger.error("Unable to load mesh for " + urls, e);
                        return null;
                    }
                }
            }
        }
        finally
        {
            // we must always close the zip file.
            zipStream.close();
        }        

        MeshData data = new MeshData();
        TFloatList colorsMesh = data.getColors();
        TFloatList verticesMesh = data.getVertices();
        TFloatList texCoord0Mesh = data.getTexCoord0();
        TFloatList normalsMesh = data.getNormals();
        TIntList indicesMesh = data.getIndices();

        // Scale vertices coordinates by unitsPerMeter
        for (int i = 0; i < this.vertices.size(); i++) {
            float originalVertexValue = this.vertices.get(i);
            float adjustedVertexValue = (float) (originalVertexValue * unitsPerMeter);
            verticesMesh.add(adjustedVertexValue);
        }

        colorsMesh.addAll(this.colors);
        texCoord0Mesh.addAll(this.texCoord0);
        normalsMesh.addAll(this.normals);
        indicesMesh.addAll(this.indices);

        if (data.getVertices() == null) {
            throw new IOException("No vertices define");
        }
        //if (data.getNormals() == null || data.getNormals().size() != data.getVertices().size()) {
        //    throw new IOException("The number of normals does not match the number of vertices.");
        //}

        if (((null == data.getColors()) || (0 == data.getColors().size()))
            && ((null == data.getTexCoord0()) || (0 == data.getTexCoord0().size()))) {
            throw new IOException("There must be either texture coordinates or vertex colors provided.");
        }

        if ((null != data.getTexCoord0()) && (0 != data.getTexCoord0().size())) {
            if (data.getTexCoord0().size() / 2 != data.getVertices().size() / 3) {
                throw new IOException("The number of tex coords (" + data.getTexCoord0().size() / 2
                                      + ") does not match the number of vertices (" + data.getVertices().size() / 3
                                      + ").");
            }
        }

        if ((null != data.getColors()) && (0 != data.getColors().size())) {
            if (data.getColors().size() / 4 != data.getVertices().size() / 3) {
                throw new IOException("The number of vertex colors (" + data.getColors().size() / 4
                                      + ") does not match the number of vertices (" + data.getVertices().size() / 3
                                      + ").");
            }
        }

        return data;
    }

}
