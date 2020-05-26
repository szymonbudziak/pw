public class Main {

    public static void main(String[] args) {

        int n = 10;
        int numThreads = 3;
        Thr[] NewThr = new Thr[numThreads];
        Vector vector1 = new Vector(n);
        Vector vector2 = new Vector(n);
        Vector vector3 = new Vector(n);
        int basicChunk = n / numThreads;
        int[] chunks = new int[numThreads];
        for (int i = 0; i < numThreads; i++) {
            chunks[i] = basicChunk;
        }
        int leftover = n % numThreads;
        if (leftover != 0) {
            int i = 0;
            int j = 0;
            while (i < leftover) {
                chunks[j] += 1;
                j++;
                if (j == numThreads - 1) {
                    j = 0;
                }
                i++;
            }
        }

        int startTask = 0;
        int endTask = chunks[0] - 1;

        vector1.initiateVector();
        vector2.initiateVector();
        vector3.resetVector();
        for (int i = 0; i < numThreads; i++) {
            (NewThr[i] = new Thr(vector1, vector2, vector3, startTask, endTask)).start();
            startTask = endTask + 1;
            if (i == numThreads - 1) {
                endTask = n - 1;
            } else {
                endTask += chunks[i + 1];
            }
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                NewThr[i].join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("vector 1");
        for (int i = 0; i < n; i++) {
            System.out.println(vector1.getContent().get(i));
        }
        System.out.println("----------------");
        System.out.println("vector 2");
        for (int i = 0; i < n; i++) {
            System.out.println(vector2.getContent().get(i));
        }
        System.out.println("----------------");
        System.out.println("vector 3");
        for (int i = 0; i < n; i++) {
            System.out.println(vector3.getContent().get(i));
        }
    }
}
