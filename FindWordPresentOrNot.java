//Required
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class FindWordPresentOrNot {

    static HashMap<Long,SentiWordNetNode> englishToHindiList;
    static HashMap<Long, HSWNNode> HindiWordNet = new HashMap<>();
    double positiveScore,negativeScore;
    public static void main(String[] args) throws Exception
    {
        FindWordPresentOrNot f = new FindWordPresentOrNot();
        //f.find("wrong","NOUN");
        f.getEnglishToHindi();
    }
    public void getEnglishToHindi() throws Exception{
        englishToHindiList = new HashMap<>();
        CreateSentiHashMap c = new CreateSentiHashMap();
        englishToHindiList = c.create();
    }
    public void find(long hindiOffset,String hindiWord, String englishWord, String pos, ArrayList<String> synonyms, String gloss) throws Exception {
        JWNL.initialize(new FileInputStream("F:\\TwiiterSarcasmProject\\Topic segmentation\\Topic Segmentation\\src\\file_properties.xml"));
        Dictionary dict = Dictionary.getInstance();
        IndexWord word;
        if(pos.equals("ADJECTIVE"))
            word = dict.lookupIndexWord(POS.ADJECTIVE, englishWord);
        else if(pos.equals("ADVERB"))
            word = dict.lookupIndexWord(POS.ADVERB, englishWord);
        else
            word = dict.lookupIndexWord(POS.NOUN, englishWord);
        long offset;
        try{
            Synset[] s = word.getSenses();
            for (int x = 0; x < s.length; x++) {
                offset = s[x].getOffset();
                positiveScore = englishToHindiList.get(offset).positiveScore;
                negativeScore = englishToHindiList.get(offset).negativeScore;
                HSWNNode h = new HSWNNode(s[x].getPOS().toString(), positiveScore, negativeScore, synonyms, gloss);
                //System.out.println("Inserting in HindiWordNet. The word is "+word+" and offset is "+hindiOffset);
                HindiWordNet.put(hindiOffset, h);
            }
        }
        catch(Exception e ){
            System.out.println("ERROR");
        }
    }
}





