/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LectoresEscritores {
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        Runnable lector = () -> {
            while (true) {
                lock.readLock().lock();
                System.out.println("lector " + Thread.currentThread().getId() + " leyendo");
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                lock.readLock().unlock();
            }
        };

        Runnable escritor = () -> {
            while (true) {
                lock.writeLock().lock();
                System.out.println("escritor " + Thread.currentThread().getId() + " escribiendo");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                lock.writeLock().unlock();
            }
        };

        new Thread(lector).start();
        new Thread(lector).start();
        new Thread(escritor).start();
    }
}

