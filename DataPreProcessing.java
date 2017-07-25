//required
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class DataPreProcessing {

    public static void main(String [] args) throws Exception {
        DataPreProcessing dp = new DataPreProcessing();
        BufferedReader br = new BufferedReader(new FileReader("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\Sarcastic Data\\#Sarcasm-search.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\TwiiterSarcasmProject\\Sarcasm detection\\DATA COLLECTION\\English Data\\Sarcastic Data\\Pre_Processed_Sarcasm_Tweets.txt"));
        dp.processTweet(br,bw);

    }

    public void processTweet(BufferedReader br, BufferedWriter bw) throws  Exception{


        String tweet = br.readLine();

        String firstString;
        tweet = org.apache.commons.lang.StringEscapeUtils.unescapeJava(tweet);
        System.out.println("tweet is "+tweet.length());
        tweet = tweet.replace("\\n","");
        //String tweet = "#lol #funny #comedy #sarcasm \\\"The world is gonna end!\\\"\\n\\nMe 2012: omfg please no\\n\\nMe now: https://t.co/tFtYZ8aeca";
        int count =1;
        while (tweet != null) {
            if(tweet.length()>0) {
                //System.out.println("The tweet which came in is "+tweet.charAt(0)+" "+tweet.charAt(1));

                if (tweet.charAt(1) == 'R') { //Remove RT in front of every tweet.
                    //System.out.println("came inside with tweet is "+tweet);
                    if (tweet.charAt(2) == 'T') {

                        firstString = tweet.substring(1, 3);
                        tweet = tweet.replace(firstString, "");

                    }
                }
                tweet = org.apache.commons.lang.StringEscapeUtils.unescapeJava(tweet); //decode the unicode values.
                tweet = removeHashtags(tweet);
                tweet = tweet.trim();

                tweet = removeUrls(tweet);
                tweet = tweet.trim();

                tweet = removeGarbage(tweet);
                tweet = tweet.trim();
                System.out.println("tweet is "+tweet);
                //bw.write(count+" "+tweet);
                bw.write(tweet);
                bw.newLine();
                count++;
            }


            tweet = br.readLine();
        }
    }
    public String removeHashtags(String line){
        //System.out.println("Line is "+line);
        int index1, index2;
        String str;
        index1 = line.indexOf('#');
        while(index1!=-1){
            index2 = line.indexOf(' ',index1);
            if (index2 ==-1){
                index2 = line.length();
            }
            str = line.substring(index1,index2);
            line = line.replace(str,"");
            index1 = line.indexOf('#');
        }
        return line;
    }

    public String removeUrls(String tweet){
        String tweetWithoutHashtagAndUrl;
        /*Pattern urlPattern = Pattern.compile("<\\\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>");

       //Create a matcher with our 'urlPattern'
        Matcher matcher = urlPattern.matcher(line);

       //Check if matcher finds url
        if(matcher.find()) {
            //Matcher found urls
            //Removing them now..
            tweetWithoutHashtagAndUrl = matcher.replaceAll("");
            //Use new tweet here
        } else {
            //Matcher did not find any urls, which means the 'tweetWithoutHashtag' already is ready for further usage
            tweetWithoutHashtagAndUrl = line;
        }*/
        tweet = tweet.replaceAll("\n"," ");
        tweet = tweet.replaceAll("S?@\\S+\\s?", "");
        tweet = tweet.replaceAll("www?.\\S+\\s?", "");
        tweet = tweet.replaceAll("https?(://\\S+\\s?)?", "");
        return tweet;
    }

   public String removeGarbage(String tweet){
       tweet = tweet.replaceAll("_", "");
       tweet = tweet.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
       tweet = tweet.replaceAll("\\*", "");
       tweet = tweet.replaceAll("\\!|\\?|\\;|\\,|\\:|\\.", "");
       tweet = tweet.replaceAll("|", "");
       tweet = tweet.replaceAll("\\\\", "");
       tweet = tweet.replaceAll("\"", "");
       return tweet;
   }

}
