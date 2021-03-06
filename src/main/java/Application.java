package main.java;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        //* ***************************************************************************************** *//
        // Задание 1#
        // Напишите программу, которая каждую секунду отображает на экране данные о времени,
        // прошедшем от начала сессии (запуска программы).
        // Другой ее поток выводит каждые 5 секунд сообщение "Прошло 5 секунд". Предусмотрите возможность ежесекундного
        // оповещения потока, воспроизводящего сообщение, потоком, отсчитывающим время.

        //Стандартное 5-секундное оповещение
        Runnable counter = new SecondsCounter();
        Thread secondsCounter = new Thread(counter);
        secondsCounter.start();
        Thread.sleep(15_500);
        secondsCounter.interrupt();

        //Опцинально, 1-секундное оповещение
        counter = new SecondsCounter(1);
        secondsCounter = new Thread(counter);
        secondsCounter.start();
        Thread.sleep(5_500);
        secondsCounter.interrupt();

        //Опцинально, 10-секундное оповещение
        counter = new SecondsCounter(10);
        secondsCounter = new Thread(counter);
        secondsCounter.start();
        Thread.sleep(30_500);
        secondsCounter.interrupt();

        //* ***************************************************************************************** *//
        System.out.print("\n");
        //* ***************************************************************************************** *//
        // Задание 2#
        // Напишите программу, которая выводит в консоль строку, состоящую из чисел от 1 до n, но с заменой
        // некоторых значений:
        //     - если число делится на 3 - вывести "fizz"
        //     - если число делится на 5 - вывести "buzz"
        //     - если число делится на 3 и на 5 - вывести "fizzbuzz"
        // Программа должна быть многопоточной, работать с 4 потоками:
        //   - поток A вызывает fizz() чтобы проверить делимость на 3 и вывести fizz.
        //   - поток B вызывает buzz() чтобы проверить делимость на 5 и вывести buzz.
        //   - поток C вызывает fizzbuzz() чтобы проверить делимость на 3 и 5 и вывести fizzbuzz.
        //   - поток D вызывает number() чтобы вывести число.

        new Sequencer(15).start();
    }
}

