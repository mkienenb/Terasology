/*
 * Copyright 2014 MovingBlocks
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
package org.terasology.engine.subsystem.awt.assets;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DirectColorModel;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.terasology.asset.AbstractAsset;
import org.terasology.asset.AssetUri;
import org.terasology.math.Rect2f;
import org.terasology.math.Rect2i;
import org.terasology.math.Vector2i;
import org.terasology.rendering.assets.texture.Texture;
import org.terasology.rendering.assets.texture.TextureData;
import org.terasology.rendering.nui.Color;

public class AwtTexture extends AbstractAsset<TextureData> implements Texture {

    private static int idCounter;

    private TextureData textureData;
    private int id;

    private Map<String, BufferedImage> bufferedImageByParametersMap = new HashMap<String, BufferedImage>();

    public AwtTexture(AssetUri uri, TextureData textureData) {
        super(uri);
        reload(textureData);

        // TODO: this might need to be synchronized at some point
        id = idCounter++;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void reload(TextureData data) {
        this.textureData = data;
    }

    @Override
    public TextureData getData() {
        return textureData;
    }

    @Override
    public void dispose() {
        this.textureData = null;
        this.bufferedImageByParametersMap = new HashMap<String, BufferedImage>();
    }

    @Override
    public boolean isDisposed() {
        return textureData == null;
    }

    @Override
    public Texture getTexture() {
        return this;
    }

    @Override
    public Rect2f getRegion() {
        return FULL_TEXTURE_REGION;
    }

    @Override
    public int getWidth() {
        return textureData.getWidth();
    }

    @Override
    public int getHeight() {
        return textureData.getHeight();
    }

    @Override
    public Vector2i size() {
        return new Vector2i(getWidth(), getHeight());
    }

    @Override
    public WrapMode getWrapMode() {
        return textureData.getWrapMode();
    }

    @Override
    public FilterMode getFilterMode() {
        return textureData.getFilterMode();
    }

    public String getKey(int width, int height, float alpha, Color color) {
        StringBuilder sb = new StringBuilder();
        sb.append(width);
        sb.append("|");
        sb.append(width);
        sb.append("|");
        sb.append(height);
        sb.append("|");
        sb.append(alpha);
        sb.append("|");
        sb.append(color.toHex());

        String key = sb.toString();
        return key;
    }

    public BufferedImage getBufferedImage(int width, int height, float alpha, Color color) {
        String key = getKey(width, height, alpha, color);

        BufferedImage bufferedImage = bufferedImageByParametersMap.get(key);

        if (null == bufferedImage) {
            // TODO: adjust for float ux, float uy, float uw, float uh
            //          textureMat.setFloat2("texOffset", textureArea.minX() + ux * textureArea.width(), textureArea.minY() + uy * textureArea.height());
            //          textureMat.setFloat2("texSize", uw * textureArea.width(), uh * textureArea.height());

            ByteBuffer[] buffers = getData().getBuffers();
            ByteBuffer byteBuffer = buffers[0];

            final IntBuffer buf = byteBuffer.asIntBuffer();
            DataBuffer dataBuffer;
            if (!color.equals(Color.WHITE)) {
                dataBuffer = new IntBufferBackedDataBufferAlphaAndColor(buf, alpha, color);
            } else if (alpha != 1f) {
                dataBuffer = new IntBufferBackedDataBufferAlphaOnly(buf, alpha);
            } else {
                dataBuffer = new IntBufferBackedDataBufferUnmodified(buf);
            }
            SampleModel sm = new SinglePixelPackedSampleModel(
                    DataBuffer.TYPE_INT,
                    width,
                    height,
                    new int[]{0xFF000000, 0xFF0000, 0xFF00, 0xFF});
            // WritableRaster raster = Raster.createWritableRaster(sm, dataBuffer, null);
            WritableRaster raster = new WritableRaster(sm, dataBuffer, new java.awt.Point()) {
            };
            bufferedImage = new BufferedImage(
                    new DirectColorModel(32, 0xFF000000, 0xFF0000, 0xFF00, 0xFF),
                    raster, false, null);

            bufferedImageByParametersMap.put(key, bufferedImage);
        }

        return bufferedImage;
    }

    @Override
    public Rect2i getPixelRegion() {
        return Rect2i.createFromMinAndSize(0, 0, getWidth(), getHeight());
    }

    public class IntBufferBackedDataBufferAlphaAndColor extends DataBuffer {
        private final IntBuffer buf;
        private final float red;
        private final float green;
        private final float blue;
        private final float alpha;

        public IntBufferBackedDataBufferAlphaAndColor(IntBuffer buf, float alpha, Color color) {
            super(DataBuffer.TYPE_INT, buf.limit());
            this.buf = buf;
            this.red = color.rf();
            this.green = color.gf();
            this.blue = color.bf();
            this.alpha = alpha * color.af();
        }

        @Override
        public int getElem(int bank, int i) {
            int v = buf.get(i);
            return ((int) ((((v & 0xFF000000) >> 24) * red)) << 24)
                   | ((int) ((((v & 0xFF0000) >> 16) * green)) << 16)
                   | ((int) ((((v & 0xFF00) >> 8) * blue)) << 8)
                   | ((int) ((v & 0xFF) * alpha));
        }

        @Override
        public void setElem(int bank, int i, int val) {
        }
    }

    public final class IntBufferBackedDataBufferAlphaOnly extends DataBuffer {
        private final IntBuffer buf;
        private final float alpha;

        public IntBufferBackedDataBufferAlphaOnly(IntBuffer buf, float alpha) {
            super(DataBuffer.TYPE_INT, buf.limit());
            this.buf = buf;
            this.alpha = alpha;
        }

        @Override
        public int getElem(int bank, int i) {
            int v = buf.get(i);
            return (v & 0xFFFFFF00)
                   | ((int) ((v & 0xFF) * alpha));
        }

        @Override
        public void setElem(int bank, int i, int val) {
        }
    }

    public final class IntBufferBackedDataBufferUnmodified extends DataBuffer {
        private final IntBuffer buf;

        public IntBufferBackedDataBufferUnmodified(IntBuffer buf) {
            super(DataBuffer.TYPE_INT, buf.limit());
            this.buf = buf;
        }

        @Override
        public int getElem(int bank, int i) {
            int v = buf.get(i);
            return v;
        }

        @Override
        public void setElem(int bank, int i, int val) {
        }
    }

}
