package VirtualNumpad;

import VirtualNumpad.lib.VirtualNumpad;

import java.awt.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            run();
        } catch (AWTException | InterruptedException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static void run() throws AWTException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        final VirtualNumpad virtualNumpad = new VirtualNumpad();
        Scanner s = new Scanner(System.in);

        System.out.print("What should I type [numeric strings only ex. \"123\"]? ");
        String inputString = s.nextLine();

        System.out.print("How many seconds should I wait before typing? ");
        final long secCount = s.nextLong();

        System.out.println(String.format("Okay, I Will wait %s second(s)", secCount));

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(secCount * 1000);
                virtualNumpad.type(inputString);
                virtualNumpad.pressEnter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        t.start();
    }
}
