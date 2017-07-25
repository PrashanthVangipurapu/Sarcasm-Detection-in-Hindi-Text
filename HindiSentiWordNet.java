
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
public class HindiSentiWordNet {
    ArrayList<HSWNNode> hswnNodes = new ArrayList<>();
    public static HashMap<Long,HSWNNode> finalHindiWordNet = new HashMap<>(); //This variable will hold the english senti wordnet in the map.
    public static void main(String[] args) throws Exception{
        HindiSentiWordNet h = new HindiSentiWordNet();
        h.listOfTextFiles();
        h.display();
    }
    public void listOfTextFiles() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        list.add("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\Hindi Senti WordNet\\Hindi_SentiWordNet\\HN_AMBI.txt");
        list.add("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\Hindi Senti WordNet\\Hindi_SentiWordNet\\HN_NEG.txt");
        list.add("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\Hindi Senti WordNet\\Hindi_SentiWordNet\\HN_NEU.txt");
        list.add("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\Hindi Senti WordNet\\Hindi_SentiWordNet\\HN_POS.txt");
        buildSentiWordNet(list);

    }
    public void buildSentiWordNet(ArrayList<String> textList) throws  Exception{
        int count =0;
        String _split[];
        BufferedReader br;
        HindiWOrdNet hw;
        for(String txt: textList) {
            br = new BufferedReader(new FileReader(txt));
            hw = new HindiWOrdNet();
            String line = br.readLine();
            String hindiWord;
            String englishWord;
            Translator t = new Translator();
            while (line != null) {
                System.out.println("Count is " + count);
                if (line.length() != 1) {
                    hindiWord = line.substring(2, line.length());
                    if(hindiWord.contains("_")) {
                        _split = hindiWord.split("_");
                        for (int i = 0; i < _split.length; i++) {
                            englishWord = t.translateToEnglish(_split[i]);
                            hw.synsetsPosGetter(_split[i], englishWord);
                        }
                    }
                    else {
                        englishWord = t.translateToEnglish(hindiWord);
                        hw.synsetsPosGetter(hindiWord, englishWord);
                    }
                }
                count++;
                line = br.readLine();
            }
        }
    }

    public void display() throws Exception{
        System.out.println("IN DISPLAY FUNCTION");
        BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\Hindi Senti WordNet\\Hindi_Senti_Word_Net.txt"));
        finalHindiWordNet = FindWordPresentOrNot.HindiWordNet;
        System.out.println("Final hindi word net size is "+finalHindiWordNet.size());
        for(Long l : finalHindiWordNet.keySet()){
            bw.write(l+" "+finalHindiWordNet.get(l));
            bw.newLine();
        }
        bw.close();
    }
}
