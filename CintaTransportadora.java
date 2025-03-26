/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.LinkedList;

public class CintaTransportadora {
    static final int MAX = 5;
    static final LinkedList<Integer> buffer = new LinkedList<>();
    static final Object lock = new Object();

    static class Productor extends Thread {
        int id;
        Productor(int id) { this.id = id; }
        public void run() {
            int item = 0;
            while (true) {
                synchronized (lock) {
                    while (buffer.size() == MAX) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    buffer.add(item);
                    System.out.println("Productor " + id + " produjo: " + item++);
                    lock.notifyAll();
                }
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        }
    }

    static class Consumidor extends Thread {
        int id;
        Consumidor(int id) { this.id = id; }
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (buffer.isEmpty()) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    int item = buffer.removeFirst();
                    System.out.println("Consumidor " + id + " consumio: " + item);
                    lock.notifyAll();
                }
                try { Thread.sleep(700); } catch (InterruptedException e) {}
            }
        }
    }

    public static void main(String[] args) {
        new Productor(1).start();
        new Productor(2).start();
        new Consumidor(1).start();
        new Consumidor(2).start();
    }
}
