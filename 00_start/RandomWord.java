import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {

        double i = 0.0;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String ln = StdIn.readString();

            String[] words = ln.split(" ");
            for (String word : words) {
                i++;
                if (StdRandom.bernoulli(1 / i)) {
                    champion = word;
                }
            }
        }

        StdOut.println(champion);
    }
}
