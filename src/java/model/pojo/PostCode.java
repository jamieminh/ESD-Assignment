/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.pojo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Jamie
 */
public class PostCode {

    /* api keys 
            - uN55lsL-j0GWUb300dl3yg29647   saxifim781@ailiking.com
            - N9yAJZTKY0qecn8K1YFdJQ29758

        
        
     */
    public String[] getAddrParams(String postcode) {
        try {
            postcode = postcode.replaceAll("\\s+", "");
            String requestUrl = String.format("https://api.getaddress.io/find/%s?api-key=uN55lsL-j0GWUb300dl3yg29647", postcode);

            // connect and get result
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // if status code is not 200 -> failed
            int status = conn.getResponseCode();
            
            
            if (status == 400 || status == 404) {
                return new String[] {"error", "Invalid Postcode"};
            }
            if (status != 200) {
                return new String[] {"error", "Server Error"};
            }


            // if success, continue
            Scanner sc = new Scanner(url.openStream()); // scan the response and store it in a string
            String response = "";
            while (sc.hasNext()) {
                response += sc.nextLine();
            }
            sc.close();

            // parse the response string to json object
            JSONParser parser = new JSONParser();    // convert string to key-value pair
            JSONObject jsonObj = (JSONObject) parser.parse(response);   // transform the parsed json data to json obj
            JSONArray addresses = (JSONArray) jsonObj.get("addresses");    // store as obj array

            String[] results = new String[addresses.size()];

            for (int i = 0; i < addresses.size(); i++) {
                String address = ((String) addresses.get(i)).replaceAll("( ,)", "");
                address += address.charAt(address.length() - 1) == ',' ? "United Kingdom, " : ", United Kingdom, " + postcode.toUpperCase();
                results[i] = address;
            }

            conn.disconnect();  // disconnect HttpURLConnection stream

            return results;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}
