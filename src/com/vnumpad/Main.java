package com.vnumpad;

import com.lib.NumberWriter;

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
        final NumberWriter numberWriter = new NumberWriter();
        Scanner s = new Scanner(System.in);

        System.out.print("What should I type [numeric strings only ex. \"123\"]? ");
        String inputString = s.nextLine();

        System.out.print("How many seconds should I wait before typing? ");
        final long secCount = s.nextLong();

        System.out.println(String.format("Okay, I Will wait %s second(s)", secCount));

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(secCount * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numberWriter.type(inputString);
            numberWriter.pressEnter();
        });
        t.start();
    }
}
