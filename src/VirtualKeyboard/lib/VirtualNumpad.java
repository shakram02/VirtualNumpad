package VirtualKeyboard.lib;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

@SuppressWarnings("ALL")
/**
 * Simulates a numpad
 */
public class VirtualNumpad {
    private Robot bot;

    public VirtualNumpad() {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Types a numeric character
     *
     * @param key numeric character to be typed
     * @throws UnsupportedOperationException when the character given is not a digit
     */
    public void type(char key) throws UnsupportedOperationException {
        if (!Character.isDigit(key)) {
            throw new UnsupportedOperationException("Can't type non-digit key with code [" + (int) key + "]");
        }

        this.hit(convertToKeyCode(key));
    }

    /**
     * Types a given numeric string
     *
     * @param str numeric string to be typed
     * @throws UnsupportedOperationException when the character given is not a digit
     */
    public void type(String str) {
        for (char letter : str.toCharArray()) {
            this.type(letter);
        }
    }

    /**
     * Sometimes it's desirable to press enter after typing some numbers
     */
    public void pressEnter() {
        this.hit(KeyEvent.VK_ENTER);
    }

    /**
     * Converts a given char to the equivalent keycode
     *
     * @param letter character to convert
     * @return mapped keycode
     */
    private int convertToKeyCode(char letter) {
        try {
            String code = "VK_" + letter;
            Field f = KeyEvent.class.getField(code);
            return f.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UnsupportedOperationException("Invalid key with code [" + (int) letter + "]");
        }
    }

    /**
     * Performs actual key pressing, a key press is followed by a key release
     * separated by some delay
     *
     * @param key Keycode to hit
     */
    private void hit(int key) {
        try {
            bot.keyPress(key);
            Thread.sleep(5);    // Some delay for the sake of being realistic
            bot.keyRelease(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
