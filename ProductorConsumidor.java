
import java.util.LinkedList;

public class ProductorConsumidor {
    private static final int MAX = 5;
    private final LinkedList<Integer> buffer = new LinkedList<>();

    public synchronized void producir(int item) throws InterruptedException {
        while (buffer.size() == MAX) wait();
        buffer.add(item);
        System.out.println("Productor produjo: " + item);
        notifyAll();
    }

    public synchronized void consumir() throws InterruptedException {
        while (buffer.isEmpty()) wait();
        int item = buffer.removeFirst();
        System.out.println("Consumidor consumio: " + item);
        notifyAll();
    }

    public static void main(String[] args) {
        ProductorConsumidor pc = new ProductorConsumidor();

        Thread productor = new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    pc.producir(i++);
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        });

        Thread consumidor = new Thread(() -> {
            while (true) {
                try {
                    pc.consumir();
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        });

        productor.start();
        consumidor.start();
    }
}
