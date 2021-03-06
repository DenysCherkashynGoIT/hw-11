package main.java;

public class SecondsCounter implements Runnable {
    private final int notifyPeriod;
    private int count = 0;
    private final Thread messenger = new Thread(this::periodMessage);

    public SecondsCounter(int notifyPeriod) {
        this.notifyPeriod = (notifyPeriod > 0) ? notifyPeriod : 5;
        messenger.setDaemon(true);
    }

    public SecondsCounter() {
        this(5);
    }

    private void periodMessage() {
        while (true) {
            for (int i = 0; i < notifyPeriod; i++) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.printf("\nПрошло %d секунд\n", notifyPeriod);
        }
    }

    @Override
    public void run() {
        messenger.start();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                this.count++;
                System.out.print(this.count + "\t");
                synchronized (this) {
                    this.notify();
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

