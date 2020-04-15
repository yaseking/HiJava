package concurrent;

public class ThreadTest {

    private static boolean flag1 = true;
    private static boolean flag2 = false;
    final static private Object lock = new Object();

    public static void main(String[] args) {
        Runnable task1 = () -> {
            for(int i=0; i<5; i++) {
                synchronized (lock) {
                    while(!flag1) {
                        try {
                            lock.wait();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("a");
                    flag1 = false;
                    flag2 = true;
                    lock.notifyAll();
                }
            }
        };

        Runnable task2 = () -> {
            for(int i=0; i<5; i++) {
                synchronized (lock) {
                    while(!flag2) {
                        try {
                            lock.wait();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("b");
                    flag1 = true;
                    flag2 = false;
                    lock.notifyAll();
                }
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();
    }
}
