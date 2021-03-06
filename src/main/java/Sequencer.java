package main.java;

import java.util.function.Consumer;

public class Sequencer extends Thread {
    private volatile int nextNumber = 0;                    //next number for check
    private final int limit;                                //the maximum value in the sequence of numbers
    private volatile Object buffer = new Object();          //next buffered value for output to console
    private volatile Integer daemonCounter = 0;             //counter of finished cycles of the daemons threads
    private final Object monitor = new Object();            //Object-monitors
    //threads daemons to determine the output value
    private final Thread thread_A = new Thread(this::fizz);
    private final Thread thread_B = new Thread(this::buzz);
    private final Thread thread_C = new Thread(this::fizzbuzz);
    private final Thread thread_D = new Thread(this::number);

    public Sequencer(int limit) {
        this.limit = (limit >=0) ? limit : 0;
        thread_A.setDaemon(true);
        thread_B.setDaemon(true);
        thread_C.setDaemon(true);
        thread_D.setDaemon(true);
    }

    private void fizz() {
        processing(number -> {
            if (!buffer.equals("fizzbuzz") && nextNumber % 3 == 0) buffer = "fizz";
        });
    }

    private void buzz() {
        processing(number -> {
            if (!buffer.equals("fizzbuzz") && nextNumber % 5 == 0) buffer = "buzz";
        });
    }

    private void fizzbuzz() {
        processing(number -> {
            if (nextNumber % 15 == 0) buffer = "fizzbuzz";
        });
    }

    private void number() {
        processing(number -> {
            if (buffer.equals(0)) buffer = number;
        });
    }

    private void processing(Consumer<Integer> procedure) {
        while (true) {
            procedure.accept(nextNumber);
            synchronized (monitor) {
                try {
                    --daemonCounter;
                    synchronized (this) {
                        if (daemonCounter == 0) this.notify();
                    }
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        thread_A.start();
        thread_B.start();
        thread_C.start();
        thread_D.start();

        for (int i = 1; i <= this.limit; i++) {
            buffer = 0;
            nextNumber = i;
            synchronized (monitor) {
                daemonCounter = 4;
                monitor.notifyAll();
            }
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(buffer);
            if (i != this.limit) System.out.print(", ");
        }
    }
}
