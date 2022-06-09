package jdev.dto;

public class ParallelPriority {


    public static void main (String[] args) {

        TestThread t1 = new TestThread("t1");
        TestThread t2 = new TestThread("t2");

        Thread thread1 = new Thread(t1);
        thread1.setPriority(Thread.MIN_PRIORITY);
        Thread thread2 = new Thread(t2);
        thread2.setPriority(Thread.MAX_PRIORITY);

        thread1.start();
        thread2.start();

    }

}


class TestThread implements Runnable {
    String message;

    TestThread(String message) {
        this.message = message;
    }
    @Override
    public void run() {
        int i = 0;

        while (i < 10) {
            System.out.println(message + " => "+ i);
            for(int x = 0; x < 1000000000; x++) {

            }
            i++;
        }

    }
}