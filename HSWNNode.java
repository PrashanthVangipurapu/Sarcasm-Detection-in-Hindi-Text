
import java.util.ArrayList;

public class HSWNNode {
    double positiveScore, negativeScore;
    String POS;
    ArrayList<String> synsets = new ArrayList<>();
    String gloss;
    public HSWNNode(String pos, double positiveScore, double negativeScore, ArrayList<String > synsets,String gloss)
    {
        this.positiveScore = positiveScore;
        this.negativeScore = negativeScore;
        this.POS = pos;
        this.synsets = synsets;
        this.gloss = gloss;
    }
    public String toString(){
        String s;
        s= POS + " "+ String.valueOf(positiveScore)+" "+String.valueOf(negativeScore)+" "+synsets+" "+gloss;
        return s;
    }

 }
