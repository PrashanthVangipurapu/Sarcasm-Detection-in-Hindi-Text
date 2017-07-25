import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.*;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;
import net.didion.jwnl.data.PointerUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
public class HindiWOrdNet {
    IndexWordSet demoIWSet;
    IndexWord[] demoIndexWord;
    Synset[] synsetArray;
    ArrayList<String> synsetList;
    BufferedWriter br;

    /*public ArrayList<String> getSynsets(Synset synsetWord)
    {  //This function is used for getting synonym words of a given word.
        return synsetWord.getWords();
    }*/
    public void synsetsPosGetter(String word, String englishWord) throws Exception
      {
          //synsetList = new ArrayList<>();
          JHWNL.initialize();

        //long[] synsetOffsets;
        FindWordPresentOrNot f =new FindWordPresentOrNot();
        f.getEnglishToHindi();
        try {
                demoIWSet = Dictionary.getInstance().lookupAllIndexWords(word);          //	 Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags
                //System.out.println(demoIWSet.size());
                if(demoIWSet.size() == 0){
                    br = new BufferedWriter(new FileWriter("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\Hindi Senti WordNet\\UnprocessedWords.txt",true));
                    br.write(word);
                    br.close();
                    return ;
                }

                demoIndexWord = new IndexWord[demoIWSet.size()];
                demoIndexWord  = demoIWSet.getIndexWordArray(); //This return the different pos forms of a single word.
                //System.out.println(demoIndexWord.length);
                for ( int i = 0;i < demoIndexWord.length;i++ )
                  {
                    int size = demoIndexWord[i].getSenseCount();
                    //System.out.println("size is "+size);
                    //synsetOffsets = demoIndexWord[i].getSynsetOffsets(); //Synonyms of the word of a particular pos.
                    for (int k = 0 ;k < size; k++ )
                    {
                        synsetArray = demoIndexWord[i].getSenses();
                        //System.out.println("synsetarray[k] is "+synsetArray[k]);
                        f.find(synsetArray[k].getOffset(), word, englishWord, synsetArray[k].getPOS().toString(),synsetArray[k].getWords(),synsetArray[k].getGloss());
                        //synsetArray[k].getPOS().toString()
                        //System.out.println("Synset POS: " + synsetArray[k].getPOS());
                        //System.out.println("Synset POS: " + synsetArray[k].getWords());
                    }
                }
               // System.out.println(synsetArray.length);
                /*for(Synset each: synsetArray){
                    System.out.println("each is "+each.getGloss());
                }*/

        } catch (Exception e) {
            System.err.println("EXCEPTION. ERROR IN INPUT/OUTPUT");
            e.printStackTrace();
        }

        //return posList;
    }

    public static void main(String args[]) throws Exception {
        HindiWOrdNet h = new HindiWOrdNet();
        h.synsetsPosGetter("जोखिम भरा","good");
        Translator t = new Translator();
        String word=t.translateToEnglish("जोखिम भरा");
        System.out.println("English word is "+word);

        //h.getEnglishToHindi();
    }
}