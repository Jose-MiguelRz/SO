/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
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
