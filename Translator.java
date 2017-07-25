// required
//Use This class for translation your english text to hindi or any other language.
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;

public class Translator {

    public static void main(String[] args) throws Exception
    {
        Translator t = new Translator();
        BufferedReader br =new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\Sarcastic Data\\Pre_Processed_Sarcasm_Tweets.txt"));
        BufferedWriter bw =new BufferedWriter(new FileWriter("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\English To Hindi Sarcasm Data.txt"));
        String line = br.readLine();
        //String line = "गलत";
        String translatedLine;
        while(line!=null){
            if(line.length()>0){
                System.out.println("line is "+line);
                //translatedLine = t.translateToEnglish(line);
                //System.out.println("trabslated english line is "+translatedLine);
                translatedLine = t.translateToHindi(line);

                //System.out.println("trabslated line is "+translatedLine);
                bw.write(translatedLine);
                bw.newLine();
                bw.newLine();
            }
            line = br.readLine();
        }
        bw.close();
    }

    public String translateToHindi(String str) throws Exception{
        //Translator http = new Translator();
        String word = callUrlAndParseResult("en", "hi", str);
        //System.out.println("The returning english word is "+word);
        return word;
    }

    public String translateToEnglish(String str)throws Exception{
        String word = callUrlAndParseResult("hi", "en", str);
        //System.out.println("The returning english word is "+word);
        return word;
    }

    private String callUrlAndParseResult(String langFrom, String langTo,

                                         String word) throws Exception

    {

        String url = "https://translate.googleapis.com/translate_a/single?"+
                "client=gtx&"+
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //System.out.println("reposne.tostring is "+response.toString());
        return parseResult(response.toString());
    }

    private String parseResult(String inputJson) throws Exception
    {
  /*
   * inputJson for word 'hello' translated to language Hindi from English-
   * [[["नमस्ते","hello",,,1]],,"en"]
   * We have to get 'नमस्ते ' from this json.
   */

        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }
}

