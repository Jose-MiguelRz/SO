
public class TurnoAlternado {
    static final Object lock = new Object();
    static boolean turno = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!turno) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    System.out.println("Proceso 1: Hola");
                    turno = false;
                    lock.notifyAll();
                }
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (turno) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    System.out.println("Proceso 2: Mundo");
                    turno = true;
                    lock.notifyAll();
                }
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        });

        t1.start();
        t2.start();
    }
}
