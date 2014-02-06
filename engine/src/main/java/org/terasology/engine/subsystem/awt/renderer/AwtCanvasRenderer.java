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
package org.terasology.engine.subsystem.awt.renderer;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.JFrame;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.terasology.engine.subsystem.awt.assets.AwtFont;
import org.terasology.engine.subsystem.awt.assets.AwtMaterial;
import org.terasology.engine.subsystem.awt.assets.AwtTexture;
import org.terasology.engine.subsystem.awt.devices.AwtDisplayDevice;
import org.terasology.math.Rect2f;
import org.terasology.math.Rect2i;
import org.terasology.math.Vector2i;
import org.terasology.rendering.assets.font.Font;
import org.terasology.rendering.assets.material.Material;
import org.terasology.rendering.assets.mesh.Mesh;
import org.terasology.rendering.assets.texture.Texture;
import org.terasology.rendering.assets.texture.TextureRegion;
import org.terasology.rendering.nui.Color;
import org.terasology.rendering.nui.HorizontalAlign;
import org.terasology.rendering.nui.ScaleMode;
import org.terasology.rendering.nui.VerticalAlign;
import org.terasology.rendering.nui.internal.CanvasRenderer;

/**
 * @author Immortius
 */
public class AwtCanvasRenderer implements CanvasRenderer {

    private JFrame window;

    private Graphics drawGraphics;
    private AwtDisplayDevice awtDisplayDevice;

    public AwtCanvasRenderer(JFrame window, AwtDisplayDevice awtDisplayDevice) {
        this.window = window;
        this.awtDisplayDevice = awtDisplayDevice;
    }

    @Override
    public void preRender() {
        drawGraphics = awtDisplayDevice.getDrawGraphics();
        drawGraphics.setPaintMode();
    }

    @Override
    public void postRender() {
        awtDisplayDevice.show();
    }

    @Override
    public void drawMesh(Mesh mesh, Material material, Rect2i drawRegion, Rect2i cropRegion, Quat4f rotation, Vector3f offset, float scale, float alpha) {
        AwtMaterial awtMaterial = (AwtMaterial) material;
        Texture texture = awtMaterial.getTexture("texture");
        if (null == texture) {
            throw new RuntimeException("unsupported");
        }

        float ux = 0f;
        float uy = 0f;
        float uw = 1f;
        float uh = 1f;
        drawTexture(texture, Color.WHITE, ScaleMode.STRETCH, drawRegion, ux, uy, uw, uh, alpha);
    }

    @Override
    public Vector2i getTargetSize() {
        return new Vector2i(window.getContentPane().getWidth(), window.getContentPane().getHeight());
    }

    @Override
    public void drawMaterialAt(Material material, Rect2i drawRegion) {
        throw new RuntimeException("unsupported");
    }

    @Override
    public void drawLine(int sx, int sy, int ex, int ey, Color color) {
        drawGraphics.setColor(getAwtColor(color));
        Graphics2D g2 = (Graphics2D)drawGraphics;
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(2));
        drawGraphics.drawLine(sx, sy, ex, ey);
        g2.setStroke(originalStroke);
    }

    private java.awt.Color getAwtColor(Color color) {
        if (null == color) {
            return null;
        }
        return new java.awt.Color(color.r(), color.g(), color.b(), color.a());
    }

    private java.awt.Color getAwtColor(Color color, float alpha) {
        if (null == color) {
            return null;
        }
        return new java.awt.Color(color.r(), color.g(), color.b(), Math.round(color.a() * alpha));
    }

    /**
     * This content is from Stack Overflow.
     * http://stackoverflow.com/questions/13323701/align-text-to-the-right-in-a-textlayout-using-java-graphics2d-api/13325210#13325210
     * http://creativecommons.org/licenses/by-sa/3.0/
     * by giorgiline
     * http://stackoverflow.com/users/1136158/giorgiline
     * Note that this code has been modified from the original.
     * 
     * Apparently, it's ok to use this code without doing anything further than the above:
     * http://meta.stackoverflow.com/questions/139698/re-using-ideas-or-small-pieces-of-code-from-stackoverflow-com#139701
     * Ideally we would contact giorgiline and request an ASF-compatible license, 
     * but StackOverflow makes it impossible for me to communicate with giorgiline directly.
     * 
     * Draw paragraph.
     * Pinta un parrafo segun las localizaciones pasadas como parametros.
     *
     * @param g2 Drawing graphic.
     * @param text String to draw.
     * @param vAlign 
     * @param width Paragraph's desired width.
     * @param x Start paragraph's X-Position.
     * @param y Start paragraph's Y-Position.
     * @param dir Paragraph's alignment.
     * @return Next line Y-position to write to.
     */
    private float drawParagraph(Graphics2D g2, java.awt.Font font, String text,
                                Rect2i absoluteRegion,
                                HorizontalAlign alignment, VerticalAlign vAlign) {
        boolean isCalculating = true;
        float newY = drawParagraph(g2, font, text, absoluteRegion.width(), absoluteRegion.minX(), absoluteRegion.minY(), alignment, isCalculating);
        int vOffset = vAlign.getOffset(((int) newY) - absoluteRegion.minY(), absoluteRegion.height());
        isCalculating = false;
        return drawParagraph(g2, font, text, absoluteRegion.width(), absoluteRegion.minX(), absoluteRegion.minY() + vOffset, alignment, isCalculating);
    }

    private float drawParagraph(Graphics2D g2, java.awt.Font font, String originalText,
                                int widthInt, int xInt, int yInt,
                                HorizontalAlign alignment, boolean isCalculating) {

        float width = (float) widthInt;
        float x = (float) xInt;
        float y = (float) yInt;

        float drawPosY = y;

        String[] textArray = originalText.split("\n");
        for (int index = 0; index < textArray.length; index++) {
            String text = textArray[index];

            if (text.length() == 0) {
                return (float) yInt;
            }

            AttributedString attstring = new AttributedString(text);
            attstring.addAttribute(TextAttribute.FONT, font);
            AttributedCharacterIterator paragraph = attstring.getIterator();
            int paragraphStart = paragraph.getBeginIndex();
            int paragraphEnd = paragraph.getEndIndex();
            FontRenderContext frc = g2.getFontRenderContext();
            LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, frc);

            // Set break width to width of Component.
            float breakWidth = width;
            // Set position to the index of the first character in the paragraph.
            lineMeasurer.setPosition(paragraphStart);

            // Get lines until the entire paragraph has been displayed.
            while (lineMeasurer.getPosition() < paragraphEnd) {
                // Retrieve next layout. A cleverer program would also cache
                // these layouts until the component is re-sized.
                TextLayout layout = lineMeasurer.nextLayout(breakWidth);
                // Compute pen x position. 
                float drawPosX;
                switch (alignment) {
                    case RIGHT:
                        drawPosX = (float) x + breakWidth - layout.getAdvance();
                        break;
                    case CENTER:
                        drawPosX = (float) x + (breakWidth - layout.getAdvance()) / 2;
                        break;
                    default:
                        drawPosX = (float) x;
                }
                // Move y-coordinate by the ascent of the layout.
                drawPosY += layout.getAscent();

                if (!isCalculating) {
                    // Draw the TextLayout at (drawPosX, drawPosY).
                    layout.draw(g2, drawPosX, drawPosY);
                }

                // Move y-coordinate in preparation for next layout.
                drawPosY += layout.getDescent() + layout.getLeading();
            }
        }
        return drawPosY;
    }

    @Override
    public void drawText(String text, Font font,
                         HorizontalAlign hAlign, VerticalAlign vAlign,
                         Rect2i absoluteRegion,
                         Color color, Color shadowColor, float alpha) {
        java.awt.Font javaAwtFont = ((AwtFont) font).getAwtFont();

        if (shadowColor.a() != 0) {
            drawGraphics.setColor(getAwtColor(shadowColor, alpha));
            Rect2i shadowRegion = Rect2i.createFromMinAndSize(new Vector2i(absoluteRegion.minX() + 1, absoluteRegion.minY() + 1), absoluteRegion.size());
            drawParagraph((Graphics2D) drawGraphics, javaAwtFont, text, shadowRegion, hAlign, vAlign);
        }

        drawGraphics.setColor(getAwtColor(color, alpha));
        drawParagraph((Graphics2D) drawGraphics, javaAwtFont, text, absoluteRegion, hAlign, vAlign);

    }

    @Override
    public void crop(Rect2i cropRegion) {
        drawGraphics.setClip(null);
        drawGraphics.clipRect(cropRegion.minX(), cropRegion.minY(), cropRegion.width() + 1, cropRegion.height() + 1);
    }

    @Override
    public void drawTexture(TextureRegion textureRegion, Color color, ScaleMode mode,
                            Rect2i absoluteRegion,
                            float ux, float uy, float uw, float uh, float alpha) {

        //        vec4 pos = gl_Vertex;
        //        pos.xy *= scale;
        //        pos.xy += offset;
        //        relPos = pos.xy;
        //            gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * pos;
        //        gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;
        //        gl_TexCoord[0].xy = texOffset + gl_TexCoord[0].xy * (texSize) ;
        //        gl_FrontColor = color;

        //      textureMat.setFloat2("scale", scale);
        //      textureMat.setFloat2("offset",
        //              absoluteRegion.minX() + 0.5f * (absoluteRegion.width() - scale.x),
        //              absoluteRegion.minY() + 0.5f * (absoluteRegion.height() - scale.y));
        //      textureMat.setFloat2("texOffset", textureArea.minX() + ux * textureArea.width(), textureArea.minY() + uy * textureArea.height());
        //      textureMat.setFloat2("texSize", uw * textureArea.width(), uh * textureArea.height());
        //      textureMat.setTexture("texture", texture.getTexture());
        //      textureMat.setFloat4("color", color.rf(), color.gf(), color.bf(), color.af() * alpha);
        //      textureMat.bindTextures();

        Rect2f textureAreaFloat = textureRegion.getRegion(); // This is in 0...1 float coordinates
        Rect2i pixelRegion = textureRegion.getPixelRegion();

        // TODO color
        Texture texture = textureRegion.getTexture();
        AwtTexture awtTexture = (AwtTexture) texture;

        BufferedImage bufferedImage = awtTexture.getBufferedImage(texture.getWidth(), texture.getHeight(), alpha, color);

        Vector2f scale = mode.scaleForRegion(absoluteRegion, textureRegion.getWidth(), textureRegion.getHeight());

        // This is in 0...1 float coordinates
        Rect2i textureArea = Rect2i.createFromMinAndSize(
                Math.round(textureAreaFloat.minX() + ux * textureAreaFloat.width()),
                Math.round(textureAreaFloat.minY() + uy * textureAreaFloat.height()),
                Math.round(uw * textureAreaFloat.width()),
                Math.round(uh * textureAreaFloat.height()));

        //        Rect2i absoluteRegionScaleAdjustment = Rect2i.createFromMinAndSize(
        //                new Vector2i((int)(absoluteRegion.minX() * scale.x),
        //                        (int)(absoluteRegion.minY() * scale.y)),
        //                absoluteRegion.size());

        Rect2i absoluteRegionScaleAdjustment = Rect2i.createFromMinAndSize(absoluteRegion.min(),
                new Vector2i(Math.round(scale.x), Math.round(scale.y)));

        Rect2i absoluteRegionOffsetAdjustment = Rect2i.createFromMinAndSize(
                new Vector2i(Math.round(absoluteRegionScaleAdjustment.minX() + 0.5f * (absoluteRegionScaleAdjustment.width() - scale.x)),
                        Math.round(absoluteRegionScaleAdjustment.minY() + 0.5f * (absoluteRegionScaleAdjustment.height() - scale.y))),
                absoluteRegionScaleAdjustment.size());

        switch (mode) {
            case SCALE_FILL:
                drawImageInternal(bufferedImage, absoluteRegionOffsetAdjustment, pixelRegion);
                break;
            case SCALE_FIT:
                drawImageInternal(bufferedImage, absoluteRegionOffsetAdjustment, pixelRegion);
                break;
            case STRETCH:
                drawImageInternal(bufferedImage, absoluteRegion, pixelRegion);
                break;
            case TILED:
                int xInc = absoluteRegion.width();
                int yInc = absoluteRegion.height();
                for (int x = absoluteRegion.minX(); x < xInc; x += xInc) {
                    for (int y = absoluteRegion.maxX(); y < yInc; y += yInc) {
                        Rect2i absoluteRegionTileOffset = Rect2i.createFromMinAndSize(new Vector2i(x, y), textureArea.size());
                        drawImageInternal(bufferedImage, absoluteRegionTileOffset, pixelRegion);
                    }
                }
                break;
            default:
                throw new RuntimeException("Unsupported mode: " + mode);
        }
    }

    private void drawImageInternal(BufferedImage bufferedImage, Rect2i destinationRegion, Rect2i sourceRegion) {

        ImageObserver observer = null;
        drawGraphics.drawImage(bufferedImage,
                destinationRegion.minX(), destinationRegion.minY(), destinationRegion.maxX(), destinationRegion.maxY(),
                (int) sourceRegion.minX(), sourceRegion.minY(), (int) sourceRegion.maxX(), sourceRegion.maxY(),
                observer);
    }

}
