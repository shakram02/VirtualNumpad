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
        // write your code here
        final NumberWriter numberWriter = new NumberWriter();
        Scanner s = new Scanner(System.in);

        System.out.println("What shout I type?:");
        String inputString = s.nextLine();

        System.out.println("How many seconds should I wait?:");
        final long secCount = s.nextLong();

        System.out.println(String.format("Will wait %s second(s)", secCount));

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
