import java.util.ArrayList;

public class Thr extends Thread {

    private final ArrayList<Character> characters;
    private ArrayList<Character> usedChars;
    private final int number;
    private final int size;

    Thr(ArrayList<Character> characters, ArrayList<Character> usedChars, int number, int size) {
        this.characters = characters;
        this.usedChars = usedChars;
        this.number = number;
        this.size = size;
    }


    private void countChar(Character freeChar) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (characters.get(i).equals(freeChar)) {
                counter++;
            }
        }
        System.out.println("Watek " + number + ": " + freeChar + " " + counter + "x");
    }

    private Character findFreeCharacter() {
        synchronized (usedChars) {
            for (int i = 0; i < size; i++) {
                if (!usedChars.contains(characters.get(i))) {
                    usedChars.add(characters.get(i));
                    return characters.get(i);
                }
            }
            return null;
        }
    }

    @Override
    public void run() {
        Character freeChar = findFreeCharacter();
        while (freeChar != null) {
            countChar(freeChar);
            freeChar = findFreeCharacter();
        }
    }

}
