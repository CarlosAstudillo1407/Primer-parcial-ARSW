package edu.eci.arsw.math;

import java.util.concurrent.atomic.AtomicBoolean;

public class PiDigitThread extends Thread {
    private final int start;
    private final int count;
    private final byte[] result;
    private final int resultOffset;
    private static final AtomicBoolean pauseFlag = new AtomicBoolean(false);

    public PiDigitThread(int start, int count, byte[] result, int resultOffset) {
        this.start = start;
        this.count = count;
        this.result = result;
        this.resultOffset = resultOffset;
    }

    @Override
    public void run() {
        int processed = 0;
        for (int i = 0; i < count; i++) {
            if (pauseFlag.get()) {
                synchronized (pauseFlag) {
                    try {
                        pauseFlag.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            // Use the getDigits method to calculate the digits
            byte[] digits = PiDigits.getDigits(start + i, 1);
            result[resultOffset + i] = digits[0];
            processed++;
            if (processed % 1000 == 0) {
                System.out.println("Thread " + getId() + " processed " + processed + " digits.");
            }
        }
    }

    public static void pauseAll() {
        pauseFlag.set(true);
    }

    public static void resumeAll() {
        synchronized (pauseFlag) {
            pauseFlag.set(false);
            pauseFlag.notifyAll();
        }
    }
}