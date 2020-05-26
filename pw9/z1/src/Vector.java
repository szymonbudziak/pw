import java.util.ArrayList;
import java.util.Random;

class Vector {

    private final int size;
    private ArrayList<Integer> content = new ArrayList<>();
    private static final Random RANDOM = new Random();


    ArrayList<Integer> getContent() {
        return content;
    }

    Vector(int size) {
        this.size = size;
    }


    void initiateVector() {
        for (int i = 0; i < size; i++) {
            content.add(RANDOM.nextInt(20));
        }
    }

    void resetVector() {
        for (int i = 0; i < size; i++) {
            content.add(null);
        }
    }


}
