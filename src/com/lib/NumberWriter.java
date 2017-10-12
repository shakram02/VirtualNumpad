package com.lib;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

@SuppressWarnings("ALL")
public class NumberWriter {
    private Robot bot;

    public NumberWriter() {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void hit(int key) {
        try {
            bot.keyPress(key);
            Thread.sleep(5);    // Some delay for the sake of being realistic
            bot.keyRelease(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void type(char key) {
        if (!Character.isDigit(key)) {
            throw new UnsupportedOperationException("Can't type non-digit key with code [" + (int) key + "]");
        }

        this.hit(convertToKeyCode(key));
    }

    public void type(String str) {
        for (char letter : str.toCharArray()) {
            this.type(letter);
        }
    }

    public void pressEnter() {
        this.hit(KeyEvent.VK_ENTER);
    }

    private int convertToKeyCode(char letter) {
        try {
            String code = "VK_" + letter;
            Field f = KeyEvent.class.getField(code);
            return f.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UnsupportedOperationException("Invalid key with code [" + (int) letter + "]");
        }
    }
}
