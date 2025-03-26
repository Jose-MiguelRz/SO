/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.concurrent.locks.ReentrantLock;

public class Filosofos {
    static final int N = 5;
    static ReentrantLock[] palillos = new ReentrantLock[N];

    public static void main(String[] args) {
        for (int i = 0; i < N; i++)
            palillos[i] = new ReentrantLock();

        for (int i = 0; i < N; i++) {
            final int id = i;
            new Thread(() -> {
                while (true) {
                    System.out.println("Filosofo " + id + " pensando");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    
                    palillos[id].lock();
                    palillos[(id + 1) % N].lock();
                    System.out.println("Filosofo " + id + " comiendo");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    palillos[id].unlock();
                    palillos[(id + 1) % N].unlock();
                }
            }).start();
        }
    }
}
