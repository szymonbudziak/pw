public class Thr extends Thread {

    private final int start;
    private final double end;
    private final int size;
    private Boolean[] sieve;

    Thr(int start, double end, int size, Boolean[] sieve) {
        this.start = start;
        this.end = end;
        this.size = size;
        this.sieve = sieve;
    }

    Boolean[] getSieve() {
        return this.sieve;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (sieve[i]) {
                for (int j = i * i; j < size; j += i) {
                    sieve[j] = false;
                }
            }
        }
    }


}
