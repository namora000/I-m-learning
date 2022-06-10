package jdev.dto;

public class ParallelJoinMillis {

    /*
    Этот класс будет стадионом, на котором вот вот состоится забег на 400 метров
    есть три спринтера, двое получили штрафные секунды в прошлых забегах,
    по этому фаворит стартует первым, а двое других через 3 секунды штрафного времени.
     */

    public static void main (String[] args) throws InterruptedException {

        Sprinter sprinter1 = new Sprinter("Sprinter1");
        Sprinter sprinter2 = new Sprinter("Sprinter2");
        Sprinter sprinter3 = new Sprinter("Sprinter3");

        Thread t1 = new Thread(sprinter1);
        Thread t2 = new Thread(sprinter2);
        Thread t3 = new Thread(sprinter3);

        t1.start();
        t1.join(3000);
        t2.start();
        t3.start();


    }


}

class Sprinter implements Runnable {

    private String name;
    private int distance = 0;


    Sprinter (String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (distance < 400) {
            System.out.println(name+" пробежал -> "+distance+" метров");
            distance++;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
