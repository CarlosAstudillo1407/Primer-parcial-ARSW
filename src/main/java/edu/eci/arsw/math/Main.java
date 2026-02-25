/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.math;
import java.util.Scanner;
import java.util.Arrays;

/**
 *
 * @author hcadavid
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread monitor = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    Thread.sleep(5000);
                    PiDigitThread.pauseAll();
                    System.out.println("Paused. Press Enter to continue...");
                    scanner.nextLine();
                    PiDigitThread.resumeAll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        monitor.start();
        byte[] digits = PiDigits.getDigits(0, 10000, 4);
        monitor.interrupt();
        System.out.println("Calculation complete.");
    }
}
