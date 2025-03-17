import java.io.*;
import java.net.*;
import org.json.*;

public class Bitcoin {
    public static void main(String[] args){
        try{
            if (args.length != 1){
                System.out.println("Too few Arguments!");
                System.exit(1);
            }

            int BitCoins = Integer.parseInt(args[0]);
            
            double price = get_price();
            if (price == -1){
                System.out.println("Price for bitoins is unavailable for now, Please try again later!");
            }

            double total_price = price * BitCoins;

            System.out.println("The price of " + BitCoins + " Bitcoins is $" + String.format("%,.4f", total_price));
            
        }
        catch(NumberFormatException e){
            System.out.println("Enter the proper number for Bitcoins.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    static double get_price(){
        try{
            String urlString = "https://api.coincap.io/v2/assets/bitcoin";

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

            JSONObject data = jsonRes.getJSONObject("data");

            double rate = data.getDouble("priceUsd");

            ///System.out.println(jsonRes.toString(2));

            return rate;
 
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }
}
