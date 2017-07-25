//Required.
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
public class CreateSentiHashMap {
    HashMap<Long,SentiWordNetNode> sentiMap = new HashMap<>();
    public static void main(String[] args) throws Exception{
        CreateSentiHashMap c = new CreateSentiHashMap();
        c.sentiMap = c.create();
        System.out.println("DONE");
        c.display();
    }
    public HashMap<Long,SentiWordNetNode> create() throws Exception{
        int count =0;
        BufferedReader br =new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\English Senti WordNet.txt"));
        String line = br.readLine();
        String stripLine1;
        String[] strip;
        String[] lineDivide;
        long offset = (long) 0;
        double positiveScore=0.0, negativeScore=0.0;
        ArrayList<String> combinedSynsets;
        while(line!=null){
               combinedSynsets = new ArrayList<>();
               count++;
               stripLine1 = line.substring(1, line.length());
               strip = stripLine1.split(" ");
               for (int i = 0; i < strip.length; i++) {
                   strip[i] = strip[i].trim();
                   if(strip[i].contains("#")){
                       if(i==0) {
                           lineDivide = strip[i].split("\t");
                           for (int p = 0; p < lineDivide.length; p++) {
                               lineDivide[p] = lineDivide[p].trim();
                           }

                           offset = Long.parseLong(lineDivide[0]);
                           positiveScore = Float.parseFloat(lineDivide[1]);
                           negativeScore = Float.parseFloat(lineDivide[2]);
                           for (int q = 3; q < lineDivide.length; q++) {
                               if (lineDivide[q].contains("#")) {
                                   lineDivide[q]=lineDivide[q].trim();
                                   combinedSynsets.add(lineDivide[q]);
                               }
                               else
                                   break;
                           }
                       }
                       else
                           {
                           lineDivide = strip[i].split("\t");
                           for(int p=0;p<lineDivide.length;p++)
                               if(lineDivide[p].contains("#")) {
                                   lineDivide[p] = lineDivide[p].trim();
                                   combinedSynsets.add(lineDivide[p]);
                               }
                               else
                                   break;
                       }

                   }
                   else
                       break;
               }
           SentiWordNetNode s =new SentiWordNetNode(positiveScore, negativeScore,combinedSynsets);
           sentiMap.put(offset,s);
           line = br.readLine();
        }
        //System.out.println("count is "+count);
        return sentiMap;
    }

    public void display(){
       System.out.println("IN DISPLAY FUNCTION the keyset is "+sentiMap.keySet().size());
       long l =(long) 14126660;
       System.out.println(sentiMap.get(l));
       /*for(Long l:sentiMap.keySet()){
           System.out.println(sentiMap.get(l));
           System.out.println(" ");
       }*/
       /*Long l =(long) 1418179;
       System.out.println(sentiMap.get(l));*/
    }
}

