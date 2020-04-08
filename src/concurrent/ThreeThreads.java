package concurrent;

public class ThreeThreads {

    private static Boolean flagA=true;
    private static Boolean flagB=false;
    private static Boolean flagC=false;

    public static void main(String[] args) {
        Object lock = new Object();

        Thread a = new Thread() {
            @Override
            public void run() {
                for(int i=0; i<10; ) {
                    synchronized (lock) {
                        while(!flagA) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        System.out.print("A");
                        flagA = false;
                        flagB = true;
                        flagC = false;
                        lock.notifyAll();
                        i++;
                    }
                }
            }
        };

        Thread b = new Thread() {
            @Override
            public void run() {
                for(int i=0; i<10; ) {
                    synchronized (lock) {
                        if(flagB) {
                            System.out.print("B");
                            flagA = false;
                            flagB = false;
                            flagC = true;
                            lock.notifyAll();
                            i++;
                        }else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        Thread c = new Thread() {
            @Override
            public void run() {
                for(int i=0; i<10; ) {
                    synchronized (lock) {
                        if(flagC) {
                            System.out.print("C");
                            flagA = true;
                            flagB = false;
                            flagC = false;
                            lock.notifyAll();
                            i++;
                        }else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        a.start();
        b.start();
        c.start();
    }
}
