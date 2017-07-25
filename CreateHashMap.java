import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CreateHashMap {
    public static void main(String[] args) throws Exception{
        CreateHashMap c = new CreateHashMap();
        HashMap<Long, HSWNNode> hMap = c.create();
        c.display(hMap);
    }
    public HashMap<Long, HSWNNode> create() throws Exception{
        HashMap<Long, HSWNNode> hMap = new HashMap<>();
        long offset;
        int firstIndex,secondIndex,thirdIndex;
        String posString;
        ArrayList<String> synsetList ;
        HSWNNode hsNode;
        BufferedReader br = new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\Hindi Senti WordNet\\Hindi_Senti_Word_Net.txt"));
       //String line ="2 [POS: adjective] 0.125 0.875 [अशुभ, अमांगलिक, अमाङ्गलिक, मनहूस, अमंगल, अमङ्गल, अक्षेम, अरिष्ट, दग्ध]  जो शुभ न हो:\"बिल्ली के द्वारा रास्ता काटा जाना अशुभ माना जाता है ।\"";
        String line = br.readLine();
        int firstFreeIndex = 0;
        int count =0;
       while(line!=null) {
           count++;
           firstFreeIndex =  line.indexOf(" ",0);
           offset = Long.parseLong(String.valueOf(line.substring(0,firstFreeIndex)));
           firstIndex = line.indexOf("]", 2);
           posString = line.substring(3, firstIndex).split(":")[1].trim();
           secondIndex = line.indexOf("[", firstIndex);
           double positiveValue = Double.parseDouble(line.substring(firstIndex + 1, secondIndex).trim().split(" ")[0]);
           double negativeValue = Double.parseDouble(line.substring(firstIndex + 1, secondIndex).trim().split(" ")[1]);
           thirdIndex = line.indexOf("]", secondIndex);
           synsetList = new ArrayList<>(Arrays.asList(line.substring(secondIndex + 1, thirdIndex).split(",")));
           String gloss=" ";
           hsNode = new HSWNNode(posString, positiveValue, negativeValue, synsetList, gloss);
           hMap.put(offset,hsNode);
           line = br.readLine();
       }
       /*System.out.println("\nthe offset value is "+offset);
       System.out.println("posstring is "+posString);
       System.out.println("positive is "+positiveValue+" negative is"+negativeValue);
       /*for(String each: synsetString){
           System.out.print(each+"\t");
       }*/
       System.out.println("count is "+count);
       return hMap;
    }

    public void display(HashMap<Long,HSWNNode> hMap){
        for(Long l : hMap.keySet()){
            System.out.println(hMap.get(l));
        }
    }

}
