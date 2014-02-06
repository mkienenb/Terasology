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
package org.terasology.engine.subsystem.awt.devices;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;

import javax.swing.JFrame;

import org.terasology.input.ButtonState;
import org.terasology.input.InputType;
import org.terasology.input.device.InputAction;
import org.terasology.input.device.KeyboardDevice;

import com.google.common.collect.Queues;

public class AwtKeyboardDevice implements KeyboardDevice {

    private JFrame mainWindow;

    private Queue<InputAction> inputQueue = Queues.newArrayDeque();
    private Object inputQueueLock = new Object();
    private int keyDown;

    public AwtKeyboardDevice(JFrame mainFrame) {
        this.mainWindow = mainFrame;
        keyDown = -1;

        mainWindow.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            //            // TODO Auto-generated method stub
            //            ButtonState state;
            //            if (Keyboard.isRepeatEvent()) {
            //                state = ButtonState.REPEAT;
            //            } else {
            //                state = (Keyboard.getEventKeyState()) ? ButtonState.DOWN : ButtonState.UP;
            //            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyDown = -1;
                InputAction event = new InputAction(InputType.KEY.getInput(e.getKeyCode()), ButtonState.UP, e.getKeyChar());
                synchronized (inputQueueLock) {
                    inputQueue.add(event);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyDown = e.getKeyCode();
                InputAction event = new InputAction(InputType.KEY.getInput(keyDown), ButtonState.DOWN, e.getKeyChar());
                synchronized (inputQueueLock) {
                    inputQueue.add(event);
                }
            }

        });
    }

    @Override
    public boolean isKeyDown(int key) {
        return key == keyDown;
    }

    @Override
    public Queue<InputAction> getInputQueue() {
        Queue<InputAction> oldInputQueue;
        synchronized (inputQueueLock) {
            oldInputQueue = inputQueue;
            inputQueue = Queues.newArrayDeque();
        }
        return oldInputQueue;
    }
}
