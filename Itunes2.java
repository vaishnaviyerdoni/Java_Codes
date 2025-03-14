import java.io.*;
import java.net.*;
import org.json.*;

public class Itunes2{
    public static void main(String[]args){
        try{
            if (args.length != 1){
                System.out.println("Too few Arguments!");
                System.exit(1);
            }

            String term = args[0];
            String urlString = "https://itunes.apple.com/search?entity=song&limit=5&term=" + term;

            @SuppressWarnings("deprecation")
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder res = new StringBuilder();

            String line;

            while((line = in.readLine()) != null){
                res.append(line);
            }

            in.close();

            JSONObject jsonRes = new JSONObject(res.toString());
            //System.out.println(jsonRes.toString(4));
            JSONArray results = jsonRes.getJSONArray("results");
            for (int i = 0; i < results.length(); i++){
                JSONObject song = results.getJSONObject(i);
                System.out.println("Track: " + song.getString("trackName"));
            } 
        }
        catch(Exception e){
            System.out.println("Exception Occured!");
        }
    }
}