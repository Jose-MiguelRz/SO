
import java.util.LinkedList;

public class BarberoDormilon {
    static final int SILLAS = 3;
    static LinkedList<Integer> espera = new LinkedList<>();
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread barbero = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (espera.isEmpty()) {
                        System.out.println("Barbero dormido...");
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    int cliente = espera.removeFirst();
                    System.out.println("Barbero atiende al cliente " + cliente);
                }
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
            }
        });

        barbero.start();

        for (int i = 0; i < 10; i++) {
            final int id = i;
            new Thread(() -> {
                synchronized (lock) {
                    if (espera.size() < SILLAS) {
                        espera.add(id);
                        System.out.println("Cliente " + id + " esperando");
                        lock.notify();
                    } else {
                        System.out.println("Cliente " + id + " (no hay lugar)");
                    }
                }
            }).start();

            try { Thread.sleep(700); } catch (InterruptedException e) {}
        }
    }
}
