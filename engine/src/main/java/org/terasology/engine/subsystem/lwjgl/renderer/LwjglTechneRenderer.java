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
package org.terasology.engine.subsystem.lwjgl.renderer;

import org.terasology.rendering.techne.TechneRenderer;

public class LwjglTechneRenderer implements TechneRenderer {
//    <html>
//
//    <head>
//    <title>Learning WebGL &mdash; lesson 4</title>
//    <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
//
//    <script type="text/javascript" src="glMatrix-0.9.5.min.js"></script>
//    <script type="text/javascript" src="webgl-utils.js"></script>
//
//    <script id="shader-fs" type="x-shader/x-fragment">
//        precision mediump float;
//
//        varying vec4 vColor;
//
//        void main(void) {
//            gl_FragColor = vColor;
//        }
//    </script>
//
//    <script id="shader-vs" type="x-shader/x-vertex">
//        attribute vec3 aVertexPosition;
//        attribute vec4 aVertexColor;
//
//        uniform mat4 uMVMatrix;
//        uniform mat4 uPMatrix;
//
//        varying vec4 vColor;
//
//        void main(void) {
//            gl_Position = uPMatrix * uMVMatrix * vec4(aVertexPosition, 1.0);
//            vColor = aVertexColor;
//        }
//    </script>
//
//
//    <script type="text/javascript">
//            
//        var gl;
//
//        function initGL(canvas) {
//            try {
//                gl = canvas.getContext("experimental-webgl");
//                gl.viewportWidth = canvas.width;
//                gl.viewportHeight = canvas.height;
//            } catch (e) {
//            }
//            if (!gl) {
//                alert("Could not initialise WebGL, sorry :-(");
//            }
//        }
//
//
//        function getShader(gl, id) {
//            var shaderScript = document.getElementById(id);
//            if (!shaderScript) {
//                return null;
//            }
//
//            var str = "";
//            var k = shaderScript.firstChild;
//            while (k) {
//                if (k.nodeType == 3) {
//                    str += k.textContent;
//                }
//                k = k.nextSibling;
//            }
//
//            var shader;
//            if (shaderScript.type == "x-shader/x-fragment") {
//                shader = gl.createShader(gl.FRAGMENT_SHADER);
//            } else if (shaderScript.type == "x-shader/x-vertex") {
//                shader = gl.createShader(gl.VERTEX_SHADER);
//            } else {
//                return null;
//            }
//
//            gl.shaderSource(shader, str);
//            gl.compileShader(shader);
//
//            if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
//                alert(gl.getShaderInfoLog(shader));
//                return null;
//            }
//
//            return shader;
//        }
//
//
//        var shaderProgram;
//
//        function initShaders() {
//            var fragmentShader = getShader(gl, "shader-fs");
//            var vertexShader = getShader(gl, "shader-vs");
//
//            shaderProgram = gl.createProgram();
//            gl.attachShader(shaderProgram, vertexShader);
//            gl.attachShader(shaderProgram, fragmentShader);
//            gl.linkProgram(shaderProgram);
//
//            if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
//                alert("Could not initialise shaders");
//            }
//
//            gl.useProgram(shaderProgram);
//
//            shaderProgram.vertexPositionAttribute = gl.getAttribLocation(shaderProgram, "aVertexPosition");
//            gl.enableVertexAttribArray(shaderProgram.vertexPositionAttribute);
//
//            shaderProgram.vertexColorAttribute = gl.getAttribLocation(shaderProgram, "aVertexColor");
//            gl.enableVertexAttribArray(shaderProgram.vertexColorAttribute);
//
//            shaderProgram.pMatrixUniform = gl.getUniformLocation(shaderProgram, "uPMatrix");
//            shaderProgram.mvMatrixUniform = gl.getUniformLocation(shaderProgram, "uMVMatrix");
//        }
//
//
//        var mvMatrix = mat4.create();
//        var mvMatrixStack = [];
//        var pMatrix = mat4.create();
//
//        function mvPushMatrix() {
//            var copy = mat4.create();
//            mat4.set(mvMatrix, copy);
//            mvMatrixStack.push(copy);
//        }
//
//        function mvPopMatrix() {
//            if (mvMatrixStack.length == 0) {
//                throw "Invalid popMatrix!";
//            }
//            mvMatrix = mvMatrixStack.pop();
//        }
//
//
//        function setMatrixUniforms() {
//            gl.uniformMatrix4fv(shaderProgram.pMatrixUniform, false, pMatrix);
//            gl.uniformMatrix4fv(shaderProgram.mvMatrixUniform, false, mvMatrix);
//        }
//
//
//        function degToRad(degrees) {
//            return degrees * Math.PI / 180;
//        }
//
//
//        var cubeVertexPositionBuffer;
//        var cubeVertexColorBuffer;
//        var cubeVertexIndexBuffer;
//
//        function initBuffers() {
//            cubeVertexPositionBuffer = gl.createBuffer();
//            gl.bindBuffer(gl.ARRAY_BUFFER, cubeVertexPositionBuffer);
//            vertices = [
//                // Front face
//                -1.0, -1.0,  1.0,
//                 1.0, -1.0,  1.0,
//                 1.0,  1.0,  1.0,
//                -1.0,  1.0,  1.0,
//
//                // Back face
//                -1.0, -1.0, -1.0,
//                -1.0,  1.0, -1.0,
//                 1.0,  1.0, -1.0,
//                 1.0, -1.0, -1.0,
//
//                // Top face
//                -1.0,  1.0, -1.0,
//                -1.0,  1.0,  1.0,
//                 1.0,  1.0,  1.0,
//                 1.0,  1.0, -1.0,
//
//                // Bottom face
//                -1.0, -1.0, -1.0,
//                 1.0, -1.0, -1.0,
//                 1.0, -1.0,  1.0,
//                -1.0, -1.0,  1.0,
//
//                // Right face
//                 1.0, -1.0, -1.0,
//                 1.0,  1.0, -1.0,
//                 1.0,  1.0,  1.0,
//                 1.0, -1.0,  1.0,
//
//                // Left face
//                -1.0, -1.0, -1.0,
//                -1.0, -1.0,  1.0,
//                -1.0,  1.0,  1.0,
//                -1.0,  1.0, -1.0
//            ];
//                    for( var v in vertices ) {
//                            vertices[v] = vertices[v]/2
//                    }
//            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertices), gl.STATIC_DRAW);
//            cubeVertexPositionBuffer.itemSize = 3;
//            cubeVertexPositionBuffer.numItems = 24;
//
//            cubeVertexColorBuffer = gl.createBuffer();
//            gl.bindBuffer(gl.ARRAY_BUFFER, cubeVertexColorBuffer);
//            colors = [
//                [1.0, 0.0, 0.0, 1.0], // Front face
//                [1.0, 1.0, 0.0, 1.0], // Back face
//                [0.0, 1.0, 0.0, 1.0], // Top face
//                [1.0, 0.5, 0.5, 1.0], // Bottom face
//                [1.0, 0.0, 1.0, 1.0], // Right face
//                [0.0, 0.0, 1.0, 1.0]  // Left face
//            ];
//            var unpackedColors = [];
//            for (var i in colors) {
//                var color = colors[i];
//                for (var j=0; j < 4; j++) {
//                    unpackedColors = unpackedColors.concat(color);
//                }
//            }
//            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(unpackedColors), gl.STATIC_DRAW);
//            cubeVertexColorBuffer.itemSize = 4;
//            cubeVertexColorBuffer.numItems = 24;
//
//            cubeVertexIndexBuffer = gl.createBuffer();
//            gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, cubeVertexIndexBuffer);
//            var cubeVertexIndices = [
//                0, 1, 2,      0, 2, 3,    // Front face
//                4, 5, 6,      4, 6, 7,    // Back face
//                8, 9, 10,     8, 10, 11,  // Top face
//                12, 13, 14,   12, 14, 15, // Bottom face
//                16, 17, 18,   16, 18, 19, // Right face
//                20, 21, 22,   20, 22, 23  // Left face
//            ];
//            gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(cubeVertexIndices), gl.STATIC_DRAW);
//            cubeVertexIndexBuffer.itemSize = 1;
//            cubeVertexIndexBuffer.numItems = 36;
//        }
//
//
//        var rCube = 0;
//
//        function drawScene() {
//            gl.viewport(0, 0, gl.viewportWidth, gl.viewportHeight);
//            gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
//
//            mat4.perspective(45, gl.viewportWidth / gl.viewportHeight, 0.1, 100.0, pMatrix);
//
//            mat4.identity(mvMatrix);
//
//                    
//            mat4.translate(mvMatrix, [0, 0.0, -8.0]);
//                    mat4.scale(mvMatrix, [0.1, 0.1, 0.1]);
//                    mat4.rotate(mvMatrix, degToRad(rCube), [1, 1, 1]);
//                    
//                    for( var i in this.boxes ) {
//                            var box = boxes[i];
//                            mvPushMatrix();                 
//
//                            
//                            mat4.translate(mvMatrix, box.position);                 
//                            mat4.rotate(mvMatrix, degToRad(box.rotation[2]), [0,0,1]);
//                            mat4.rotate(mvMatrix, degToRad(box.rotation[1]), [0,1,0]);
//                            mat4.rotate(mvMatrix, degToRad(box.rotation[0]), [1,0,0]);                              
//                            //mat4.translate(mvMatrix, [-box.position[0], -box.position[1], -box.position[2]])
//
//
//    mat4.translate(mvMatrix, [box.offset[0]+box.size[0]/2, box.offset[1]+box.size[1]/2, box.offset[2]+box.size[2]/2]);                      
//    mat4.scale(mvMatrix, [box.size[0], box.size[1], box.size[2]] );                                                                 
//                            
//                            //mat4.scale(mvMatrix, [1/2,1/2,1/2]);
//                            
//                            
//                            drawBox();
//                            mvPopMatrix();
//                            
//                    }
//        }
//            
//            function drawBox() {
//            gl.bindBuffer(gl.ARRAY_BUFFER, cubeVertexPositionBuffer);
//            gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, cubeVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);
//
//            gl.bindBuffer(gl.ARRAY_BUFFER, cubeVertexColorBuffer);
//            gl.vertexAttribPointer(shaderProgram.vertexColorAttribute, cubeVertexColorBuffer.itemSize, gl.FLOAT, false, 0, 0);
//
//            gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, cubeVertexIndexBuffer);
//            setMatrixUniforms();
//            gl.drawElements(gl.TRIANGLES, cubeVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
//            }
//            
//            var boxes = [];
//            function addBox(doc) {
//                    var box = {};
//                    if( typeof doc==='object' ) {
//                            box.offset = floatArray( doc.getElementsByTagName("Offset")[0].innerHTML.split(","));
//                            box.position = floatArray( doc.getElementsByTagName("Position")[0].innerHTML.split(","));
//                            box.rotation = floatArray( doc.getElementsByTagName("Rotation")[0].innerHTML.split(","));
//                            box.size = floatArray( doc.getElementsByTagName("Size")[0].innerHTML.split(","));
//                            boxes.push(box);
//                    }
//            }
//            
//            function floatArray(array) {
//                    for( var v in array ) {
//                            array[v] = parseFloat(array[v]);
//                    }
//                    return array;
//            }
//            function addBox2(offset, size) {
//                    var box = {}
//                    box.offset = offset;
//                    box.size = size;
//                    box.position = [0,17,0]
//                    box.rotation  = [0,0,0]
//                    boxes.push(box);
//            }
//
//
//        var lastTime = 0;
//
//        function animate() {
//            var timeNow = new Date().getTime();
//            if (lastTime != 0) {
//                var elapsed = timeNow - lastTime;
//
//                rCube -= (75 * elapsed) / 1000.0;
//            }
//            lastTime = timeNow;
//        }
//
//
//        function tick() {
//            requestAnimFrame(tick);
//            drawScene();
//            animate();
//        }
//
//
//        function webGLStart() {
//            var canvas = document.getElementById("lesson04-canvas");
//            initGL(canvas);
//            initShaders()
//            initBuffers();
//
//            gl.clearColor(0.0, 0.0, 0.0, 1.0);
//            gl.enable(gl.DEPTH_TEST);
//                    
//                    var that = this;
//                    var button = document.getElementById("load")
//                    button.onclick = function() {
//                            //xhttp = new XMLHttpRequest();
//                            //xhttp.open("GET", "model.xml", false);
//                            //xhttp.send()
//                            //var doc = xhttp.responseXML
//                            var model = document.getElementById("model").value
//                            var doc = new DOMParser().parseFromString(model, "text/xml");
//                            var shapes = doc.getElementsByTagName("Shape");
//                            that.boxes = [];
//                            for( var shape in shapes ) {
//                                    that.addBox(shapes[shape]);
//                            }
//                            
//                    }
//                    //addBox2([-12,-14,-1], [24,12,2]);
//                    //addBox2([-1,-2,-1], [2, 14, 2]);
//                    
//                    //addBox2([0,0,0], [3,3,3]);
//                    //addBox2([1,1,-1], [1,1,1]);
//                    //addBox2([1,1,3], [1,1,1]);
//
//            tick();
//        }
//
//    </script>
//
//
//    </head>
//
//
//    <body onload="webGLStart();">
//        <canvas id="lesson04-canvas" style="border: none;" width="500" height="500"></canvas>
//            <textarea id="model">
//    <Techne Version="2.2">
//      <Author>ZeuX</Author>
//      <DateCreated></DateCreated>
//      <Description></Description>
//      <Models>
//        <Model texture="Anttexture.png">
//          <BaseClass>ModelBase</BaseClass>
//          <Geometry>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="thorax">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>0,0,0</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>3,3,3</Size>
//              <TextureOffset>22,0</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="thorax1">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>1,1,-1</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>1,1,1</Size>
//              <TextureOffset>18,0</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="thorax3">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>1,1,3</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>1,1,1</Size>
//              <TextureOffset>34,0</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="abdomen">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>0,0,4</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>3,3,5</Size>
//              <TextureOffset>38,0</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="abdomen1">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>1,1,9</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>1,1,1</Size>
//              <TextureOffset>54,0</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="head">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>0,-1,-4</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>3,3,3</Size>
//              <TextureOffset>6,0</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="jawsr">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>-1,0,-6</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>1,1,3</Size>
//              <TextureOffset>0,9</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="jawsl">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>3,0,-6</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,0</Rotation>
//              <Size>1,1,3</Size>
//              <TextureOffset>0,14</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="llegtop1">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>3,1,1</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,22</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>15,10</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="llegbot1">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>5,-3,1</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,61</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>15,19</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="llegtop2">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>3,1,2</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,-12,22</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>15,13</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="llegbot2">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>5,-3,2</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,-13,61</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>15,22</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="llegtop3">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>3,1,0</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,20,22</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>15,16</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="llegbot3">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>5,-3,0</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,20,61</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>15,25</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="rlegtop1">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>-4,2,1</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,-27</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>25,10</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="rlegbot1">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>-7,0,1</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,0,-56</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>25,19</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="rlegtop2">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>-4,2,0</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,-34,-27</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>25,13</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="rlegbot2">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>-7,0,0</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,-34,-56</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>25,22</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="rlegtop3">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>-4,2,2</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,24,-27</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>25,16</TextureOffset>
//            </Shape>
//            <Shape type="d9e621f7-957f-4b77-b1ae-20dcd0da7751" name="rlegbot3">
//              <Animation>
//                <AnimationAngles>0,0,0</AnimationAngles>
//                <AnimationDuration>0,0,0</AnimationDuration>
//                <AnimationType>0,0,0</AnimationType>
//              </Animation>
//              <IsDecorative>False</IsDecorative>
//              <IsFixed>False</IsFixed>
//              <IsMirrored>False</IsMirrored>
//              <Offset>-7,0,2</Offset>
//              <Position>0,17,0</Position>
//              <Rotation>0,24,-56</Rotation>
//              <Size>3,1,1</Size>
//              <TextureOffset>25,25</TextureOffset>
//            </Shape>
//          </Geometry>
//          <GlScale>1,1,1</GlScale>
//          <Name>Ant</Name>
//          <TextureSize>64,32</TextureSize>
//        </Model>
//      </Models>
//      <Name>Ant</Name>
//      <PreviewImage></PreviewImage>
//      <ProjectName>Ant</ProjectName>
//      <ProjectType>Minecraft</ProjectType>
//    </Techne>
//            </textarea>
//            <input id="load" type="button" value="Load" />
//    </body>
//
//    </html>


}
