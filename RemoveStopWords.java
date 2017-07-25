//Required.
import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class RemoveStopWords {

    public static void main(String[] args) throws Exception{
        RemoveStopWords r = new RemoveStopWords();
        r.removeStopWords();
    }
    public void removeStopWords() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\English To Hindi Sarcasm Data.txt"));
        BufferedWriter bw =new BufferedWriter(new FileWriter("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\Stop Words Removed English to Hindi Data.txt"));
        String line = br.readLine();
        ArrayList<String> stopWords= new ArrayList<>();
        HindiStopWordsArray h = new HindiStopWordsArray();
        stopWords = h.getArray();
        String[] tokens;
        ArrayList<String> tokenList;
        ArrayList<String > removeList;
        while(line!=null){
            //System.out.println(line);
            tokens = line.split(" ");
            System.out.println(tokens.length);
            tokenList = new ArrayList<>(Arrays.asList(tokens));
            removeList = new ArrayList<>();
            for(String str: tokenList){
                if(stopWords.contains(str))
                    removeList.add(str);
            }
            line = "";
            tokenList.removeAll(removeList);
            for (String str: tokenList)
                line = line + str+" ";
            //System.out.println(line.trim());
            bw.write(line);
            bw.newLine();
            line = br.readLine();

        }
    }

}
