/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchscoresjson;

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
public class MatchScoresJson {


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
        String sURL = "https://api.vexdb.io/v1/get_matches?season=turning%20point&sku=RE-VRC-19-5411&team="; //just a string
        String ccwm;
        String size;
        int actualSize;
        int numTeams = 72;
        
        int[] scoredPoints = new int[numTeams];
        int[] opponentScore = new int[numTeams];
        String[] team = new String[numTeams];
        
        
        String teams = "40A40B109A134A134B134C134E134F134G134J134K134M134X169A169B169X169Y375X404A404S404Z603A603B603D785A785B790X855A1124A1320C1353A1353C1353P2019F2442A2442B2442C2442E2616J3142A3773U3773W4180A4180B4478B4478D4478S4478V4478X4886T6277B6277D6916D9605A9909A9909C9932A9932E9932F9932G11442X11442Z16099A16099B25461Z28088A41364A58566A75735A75735B81118P94802A";
        
        System.out.println("Team\t\tScored  - Opponents\t   NetScore");
        
        for(int x = 0; x < numTeams; x++) {
            if (x < 2)
            {
                team[x] = teams.substring(teams.indexOf(teams), teams.indexOf(teams)+3);
                teams = teams.substring(3);
                
            }
            else if(x < 28)
            {
                team[x] = teams.substring(teams.indexOf(teams), teams.indexOf(teams)+4);
                teams = teams.substring(4);
            }
            else if(x < 60)
            {
                team[x] = teams.substring(teams.indexOf(teams), teams.indexOf(teams) + 5);
                teams = teams.substring(5);
            }
            else
            {
                team[x] = teams.substring(teams.indexOf(teams), teams.indexOf(teams) + 6);
                teams = teams.substring(6);
            }
            
            //System.out.println(team[x]);
        }
        

            
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

                String[] substringMatchScore = new String[numTeams];
                String substringTeamColor;
                String[] substringOpponent = new String[numTeams];
                boolean redSide;
            
                for(int x = 0; x < actualSize; x++)
                {
                    int indexTeam = ccwm.indexOf(team[y]);
                    //System.out.println("Index of the team is: " + indexTeam + " and the team is " + team[y]);
                    substringTeamColor = ccwm.substring(indexTeam - 7, indexTeam - 6);

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
                //System.out.println("Team: " + team[y] + "       " + Integer.toString(scoredPoints[y]) + "  -  " + Integer.toString(opponentScore[y]) + "\t Net Score: " + Integer.toString(scoredPoints[y] - opponentScore[y]));
                //System.out.println(team[y]);
                System.out.println(Integer.toString(scoredPoints[y]));
                //System.out.println(Integer.toString(opponentScore[y]));
            }
        }
    }
    
}
