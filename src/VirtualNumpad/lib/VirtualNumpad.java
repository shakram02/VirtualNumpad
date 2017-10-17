package VirtualNumpad.lib;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

@SuppressWarnings("ALL")
/**
 * Simulates a numpad
 */
public class VirtualNumpad {
    private Robot bot;
    private int betweenStrokeDelay;

    public VirtualNumpad(int betweenStrokeDelay) {
        this();
        this.betweenStrokeDelay = betweenStrokeDelay;
    }

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
    public void type(char key) throws UnsupportedOperationException, InterruptedException {
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
    public void type(String str) throws InterruptedException {
        for (char letter : str.toCharArray()) {
            this.type(letter);
        }
    }

    /**
     * Sometimes it's desirable to press enter after typing some numbers
     */
    public void pressEnter() throws InterruptedException {
        this.hit(KeyEvent.VK_ENTER);
    }

    /**
     * Avoid using this function, it's there for corner cases and mainly special keys
     * those like Fx where x = {1,2,3,...,12}
     *
     * @param keyEventCode
     * @throws InterruptedException
     */
    public void unsafePressKey(int keyEventCode) throws InterruptedException {
        this.hit(keyEventCode);
    }

    /**
     * Converts a given char to the equivalent keycode
     *
     * @param number character to convert
     * @return mapped keycode
     */
    private int convertToKeyCode(char number) {
        try {
            String code = "VK_" + number;
            Field f = KeyEvent.class.getField(code);
            return f.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UnsupportedOperationException("Invalid key with code [" + (int) number + "]");
        }
    }

    /**
     * Performs actual key pressing, a key press is followed by a key release
     * separated by some delay
     *
     * @param key Keycode to hit
     */
    private void hit(int key) throws InterruptedException {
        bot.keyPress(key);

        if (this.betweenStrokeDelay != 0) {
            Thread.sleep(this.betweenStrokeDelay);    // Some delay for the sake of being realistic
        }

        bot.keyRelease(key);
    }
}
