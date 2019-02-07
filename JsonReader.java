/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ryan Nicholas
 */
public class JsonReader {

    /**
     * read all
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Reads json url
     *
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, JSONException {
        // TODO code application logic here
        String sURL = "https://api.vexdb.io/v1/get_rankings?season=turning%20point&sku=RE-VRC-18-5715&team="; //just a string
        String ccwm;
        String size;
        int actualSize;
        
        String[] team = new String[60];
        
        
        String teams = "162A375X877H877Z1188A1188B1618A1695A1695B1695D1697A4478A4478B4478C4478D4478E4478S4478V4478X4478Y5150D5150E5150F5150G5150H5150J7263A8079A8079C8079M8079R8079S8878A8878B8878C8878D8878E8878F8878G9605A9909A9909B9909C13085A13085B17814A17814B17814C17814D17814E17814V17814W17814X17814Y17814Z41364A50005X68110G73238S1695C";
        
        for(int x = 0; x < 59; x++) {
            if(x < 4)
            {
                team[x] = teams.substring(teams.indexOf(teams), teams.indexOf(teams)+4);
                teams = teams.substring(4);
            }
            else if(x < 43)
            {
                team[x] = teams.substring(teams.indexOf(teams), teams.indexOf(teams) + 5);
                teams = teams.substring(5);
            }
            else
            {
                team[x] = teams.substring(teams.indexOf(teams), teams.indexOf(teams) + 6);
                teams = teams.substring(6);
            }
        }
        team[59] = teams;
        
        int length = team.length;
        
        

        for (int y = 0; y < length; y++) //Change the team number being looked at
        {


            JSONObject json = readJsonFromUrl(sURL + team[y]); //Get data for the team
            ccwm = json.toString(); //Convert to string
            size = ccwm.substring(ccwm.indexOf("size") + 6, ccwm.indexOf("size") + 7); //Find size of data
            actualSize = Integer.parseInt(size);

            if (actualSize > 0) //Determine if the team exists
            {

                String[] substringOPR = new String[60];
                String[] substringWins  = new String[60];
                String[] subAP = new String[60];
                
                substringWins[y] = ccwm.substring(ccwm.indexOf("wins") + 6, ccwm.indexOf("dpr") - 2);
                ccwm = ccwm.substring(ccwm.indexOf("dpr") + 3);
                subAP[y] = ccwm.substring(ccwm.indexOf("ap") + 4, ccwm.indexOf("division") - 2);
                ccwm = ccwm.substring(ccwm.indexOf("division") + 8);
                substringOPR[y] = ccwm.substring(ccwm.indexOf("opr") + 5, ccwm.indexOf("ties") - 2); //Find the average ccwm of the team
                ccwm = ccwm.substring(ccwm.indexOf("ties") + 4);
                
                System.out.println("Team: " + team[y] + "       " + subAP[y] + "     " + substringOPR[y] + "       " + substringWins[y]);
                
            }
        }
        
    }

}
