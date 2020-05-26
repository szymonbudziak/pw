import java.util.ArrayList;
import java.util.Random;

public class Main {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        int n = 1000;
        int m = 555;
        int size = n * m;
        Character[][] chars = new Character[n][m];
        int numThreads = 4;

        char[] charsForFilling = {'!', '@', '#', '$', '%', '^', '&', '*'};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int generate = RANDOM.nextInt(charsForFilling.length);
                chars[i][j] = charsForFilling[generate];
            }
        }

        ArrayList<Character> charsArray = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                charsArray.add(chars[i][j]);
            }
        }
        ArrayList<Character> usedChars = new ArrayList<>();
        Thr[] newThr = new Thr[numThreads];
        for (int i = 0; i < numThreads; i++) {
            (newThr[i] = new Thr(charsArray, usedChars, i, size)).start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                newThr[i].join();
            } catch (InterruptedException e) {
            }
        }

    }

}

