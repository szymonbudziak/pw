public class Main {

    public static void main(String[] args) {

        int size = 100000;
        int numThreads = 8;
        Boolean[] sieve = new Boolean[size];
        sieve[0] = false;
        sieve[1] = false;
        for (int i = 2; i < size; i++) {
            sieve[i] = true;
        }
        double sizeSqrt = Math.sqrt(size);

        Thr[] newThr = new Thr[numThreads];
        int start = 2;
        double chunk = sizeSqrt / numThreads;
        double end = chunk;
        for (int i = 0; i < numThreads; i++) {
            (newThr[i] = new Thr(start, end, size, sieve)).start();
            start = (int) end + 1;
            if (i == numThreads - 1) {
                end = sizeSqrt;
            } else {
                end += chunk;
            }
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                newThr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int counter = 0;
        Boolean[] result = newThr[numThreads - 1].getSieve();
        for (int i = 0; i < size; i++) {
            if (result[i]) {
                counter++;
                System.out.println(i);
            }
        }
        System.out.println("znalezionych liczb pierwszych: " + counter);
    }
}
