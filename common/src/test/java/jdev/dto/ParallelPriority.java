package jdev.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ParallelPriority {


    public static void main (String[] args) {
        // Создаём экземпляры класса
        TestThread t1 = new TestThread("t1");
        TestThread t2 = new TestThread("t2");

        //Подготавливаем потоки для каждого экземпляра класса
        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);

        //Назначаем приоритеты
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);

        //Запускаем на выполнение
        thread1.start();
        thread2.start();

    }

}


class TestThread extends Thread {
    String message;

    TestThread(String message) {
        this.message = message;
    }
    @Override
    public void run() {
        int i = 0;
        ArrayList<Double> maxResults = new ArrayList<>();
        double max = 0.0;
        double min = 0.0;
        double difference = 0.0;

        while (i < 3000) {
            System.out.println(message + " => "+ i);

            ArrayList<Double> results = new ArrayList<>();
            double a = Math.random()*1000;
            double b = Math.random()*2000;
            double c = Math.random()*100;

            for(int x = 0; x < 100000; x++) {
                double temp = a*c*b+(Math.sqrt(a)+Math.sqrt(b)+Math.sqrt(c))/10;
                results.add(temp);
            }
            Collections.sort(results);

            maxResults.add(Collections.max(results));

            //это условие для проверки работы метода yield()
            if(message.equalsIgnoreCase("t2")) {
                yield();
            }


            i++;
        }
        Collections.sort(maxResults);

        max = Collections.max(maxResults);
        min = Collections.min(maxResults);
        difference = max - min;
    }
}