public class Thr extends Thread {

    public Thr(Vector vector1, Vector vector2, Vector vector3, int start, int end) {
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.vector3 = vector3;
        this.start = start;
        this.end = end;
    }

    private final Vector vector1;
    private final Vector vector2;
    private final int start;
    private final int end;
    private Vector vector3;

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            vector3.getContent().set(i, vector1.getContent().get(i) + vector2.getContent().get(i));
        }
    }
}
