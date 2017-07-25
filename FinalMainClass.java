import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class FinalMainClass {

    ArrayList<String> tokenList;
    ArrayList<String> threeGram;
    public  HashMap<Long,HSWNNode> finalHindiSentiWordNet =  new HashMap<>();
    ArrayList<String> positiveFeatures = new ArrayList<>();
    ArrayList<String> negativeFeatures = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        FinalMainClass m = new FinalMainClass();
        m.getHindiSentiWordNet();
        m.findFeaturesAfterWord();
        m.findFeaturesBeforeWord();
        m.findSarcasticTweets();
        //m.display();
    }


    public void getHindiSentiWordNet() throws Exception{
        CreateHashMap c = new CreateHashMap();
        finalHindiSentiWordNet = c.create();
    }

    public void findFeaturesAfterWord() throws Exception {
        int flag = 0;
        ArrayList<String> firstWord = new ArrayList<>();
        ArrayList<String> listOfTokens ;
        firstWord.add("प्यार");
        firstWord.add("प्रेम");
        BufferedReader br = new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\English To Hindi Sarcasm Data.txt"));
        String line = br.readLine();
        while (line != null) {
            threeGram = new ArrayList<>();
            if (!line.equals("")) {
                listOfTokens = getListOfTokens(line);
                if (listOfTokens.contains(firstWord.get(0))) {
                    flag = 1;
                    //System.out.println(line);
                    threeGram = getThreeGramAfterWord(listOfTokens, firstWord.get(0));
                } else if (listOfTokens.contains(firstWord.get(1))) {
                    flag = 1;
                    threeGram = getThreeGramAfterWord(listOfTokens, firstWord.get(1));
                    //System.out.println("thregram size is "+threeGram.size());
                }
            }

            if (flag == 1)
                getFeatures(threeGram);

            line = br.readLine();
        }
    }


    public void findFeaturesBeforeWord() throws  Exception {
        int flag = 0;
        ArrayList<String> firstWord = new ArrayList<>(); //Search for those lines which have these words or tokens in them.
        ArrayList<String> listOfTokens;
        firstWord.add("प्यार");
        firstWord.add("प्रेम");
        BufferedReader br = new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\English To Hindi Sarcasm Data.txt"));
        String line = br.readLine();
        while (line != null) {
            threeGram = new ArrayList<>();
            if (!line.equals("")) {
                listOfTokens = getListOfTokens(line);
                if (listOfTokens.contains(firstWord.get(0))) {
                    flag = 1;
                    threeGram = getThreeGramBeforeWord(listOfTokens, firstWord.get(0));
                } else if (listOfTokens.contains(firstWord.get(1))) {
                    flag = 1;
                    threeGram = getThreeGramBeforeWord(listOfTokens, firstWord.get(1));
                }
            }

            if (flag == 1)
                getFeatures(threeGram);

            line = br.readLine();
        }
    }



   public void getFeatures(ArrayList<String> threeGram) throws Exception {
       ArrayList<Long> offsetList = new ArrayList<>();
       ArrayList<String> synset = new ArrayList<>();
       if (threeGram.size() != 0) {
           double positiveScore = 0.0, negativeScore = 0.0;
           for (String eachString : threeGram) {
               offsetList = getOffsetList(eachString);
               for (Long each : offsetList) {
                   try {
                       synset = finalHindiSentiWordNet.get(each).synsets;
                       positiveScore = +finalHindiSentiWordNet.get(each).positiveScore;
                       negativeScore = +finalHindiSentiWordNet.get(each).negativeScore;
                   } catch (Exception e) {
                       continue;
                   }
               }
               if (positiveScore < negativeScore) {  // This word is a negative word.
                   negativeFeatures.add(eachString);
                   negativeFeatures.addAll(synset);
               } else {                                // This word is positve word.
                   positiveFeatures.add(eachString);
                   positiveFeatures.addAll(synset);
               }
           }
       }
   }


   public ArrayList<Long> getOffsetList(String eachString) throws Exception {
        IndexWord[] demoIndexWord;
        JHWNL.initialize();
        ArrayList<Long> offsetsList = new ArrayList<>();
        Synset[] synsetArray;
        IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(eachString);
        demoIndexWord = demoIWSet.getIndexWordArray();
        for (int i = 0; i < demoIndexWord.length; i++) {
            synsetArray = demoIndexWord[i].getSenses();
            for (int z = 0; z < synsetArray.length; z++)
                offsetsList.add(synsetArray[z].getOffset());
        }
        return offsetsList;
    }


    public  ArrayList<String> getThreeGramAfterWord(ArrayList<String> list, String word){
        int index = list.indexOf(word);
        ArrayList<String> tokens =  new ArrayList<>();
        while(index<list.size()){
            tokens.add(list.get(index));
            index++;
        }
      return tokens;
    }

    public ArrayList<String> getThreeGramBeforeWord(ArrayList<String> list, String word){
        int index = list.indexOf(word);
        ArrayList<String> tokens =  new ArrayList<>();
        while(index>=0){
            tokens.add(list.get(index));
            index--;
        }
        return tokens;
    }



    public ArrayList<String> getListOfTokens(String line){
        String[] tokens = line.trim().split(" ");
        tokenList = new ArrayList<>(Arrays.asList(tokens));
        return tokenList;
    }

    public void findSarcasticTweets() throws Exception{

        BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\FinalSarcasmData.txt"));

        int positiveFlag =0, negativeFlag =0;
        BufferedReader br = new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\English To Hindi Sarcasm Data.txt"));
        ArrayList<String> tokenList;
        String line = br.readLine();
        while(line!=null){
           tokenList = getListOfTokens(line);
           if(!(Collections.disjoint(tokenList,positiveFeatures)))
                 positiveFlag =1;

           if(!(Collections.disjoint(tokenList,negativeFeatures)))
               negativeFlag =1;
           if(positiveFlag == 1 && negativeFlag ==1){
                bw.write(line);
                bw.newLine();
           }

           line = br.readLine();
        }
        bw.close();
    }

    public void display(){
        System.out.println(positiveFeatures.size());
        System.out.println(negativeFeatures.size());
    }
}
