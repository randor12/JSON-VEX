/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vexjson;

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
 * @author Ryan's Computer
 */
public class VexJson {

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
        String sURL = "https://api.vexdb.io/v1/get_matches?season=turning%20point&sku=RE-VRC-18-6698&team="; //just a string
        String ccwm;
        String size;
        int actualSize;
        int[] scoredPoints = new int[60];
        int[] opponentScore = new int[60];
        String[] team = new String[60];
        
        
        String teams = "162A375X877H877Z1188A1188B1618A1695A1695B1695D1697A4478A4478B4478C4478D4478E4478S4478V4478X4478Y5150D5150E5150F5150G5150H5150J7263A8079A8079C8079M8079R8079S8878A8878B8878C8878D8878E8878F8878G9605A9909A9909B9909C13085A13085B17814A17814B17814C17814D17814E17814V17814W17814X17814Y17814Z41364A50005X68110G73238S1695C";
        
        for(int x = 0; x < 59; x++) {
            scoredPoints[x] = 0;
            opponentScore[x] = 0;
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
        scoredPoints[59] = 0;
        opponentScore[59] = 0;
            
        int length = team.length;
        
        

        for (int y = 0; y < length; y++) //Change the team number being looked at
        {


            JSONObject json = readJsonFromUrl(sURL + team[y]); //Get data for the team
            ccwm = json.toString(); //Convert to string
            size = ccwm.substring(ccwm.indexOf("size") + 6, ccwm.indexOf("status") - 2); //Find size of data
            actualSize = Integer.parseInt(size);
            
            if (actualSize > 0) //Determine if the team exists
            {
                //System.out.println(ccwm);

                String[] substringMatchScore = new String[60];
                String substringTeamColor;
                String[] substringOpponent = new String[60];
                boolean redSide;
            
                for(int x = 0; x < actualSize; x++)
                {
                    substringTeamColor = ccwm.substring(ccwm.indexOf(team[y]) - 7, ccwm.indexOf(team[y]) - 6);

                    redSide = "r".equals(substringTeamColor);


                    substringMatchScore[y] = ccwm.substring(ccwm.indexOf("redscore") + 10, ccwm.indexOf("instance") - 2);
                    //System.out.println(substringMatchScore[y]);
                    ccwm = ccwm.substring(ccwm.indexOf("instance") + 8);


                    if("1".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "2".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "3".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "4".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "5".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "6".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "7".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "8".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "9".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)) || "0".equals(ccwm.substring(ccwm.indexOf("bluescore") + 12,ccwm.indexOf("bluescore") + 13)))
                    {
                        substringOpponent[y] = ccwm.substring(ccwm.indexOf("bluescore") + 11,ccwm.indexOf("bluescore") + 13); //Find the average ccwm of the team
                        ccwm = ccwm.substring(ccwm.indexOf("bluescore") + 14);
                    }
                    else
                    {
                        substringOpponent[y] = ccwm.substring(ccwm.indexOf("bluescore") + 11,ccwm.indexOf("bluescore") + 12); //Find the average ccwm of the team
                        ccwm = ccwm.substring(ccwm.indexOf("bluescore") + 13);
                    }

                    //System.out.println(substringOpponent[y]);


                    if(redSide) //Determine scores for teams
                    {
                        scoredPoints[y] += Integer.parseInt(substringMatchScore[y]);
                        opponentScore[y] += Integer.parseInt(substringOpponent[y]);

                    }
                    else
                    {
                        opponentScore[y] += Integer.parseInt(substringMatchScore[y]);
                        scoredPoints[y] += Integer.parseInt(substringOpponent[y]);
                    }

                }
                System.out.println("Team: " + team[y] + "       " + Integer.toString(scoredPoints[y]) + "  -  " + Integer.toString(opponentScore[y]) + "\t Net Score: " + Integer.toString(scoredPoints[y] - opponentScore[y]));
            }
        }
    }
    
}
