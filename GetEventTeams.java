/*
 * This gets all teams participating in an event
 */
package geteventteams;

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
public class GetEventTeams {
    
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
        String sURL = "https://api.vexdb.io/v1/get_teams?sku=RE-VRC-19-5411"; //just a string
        String getTeams;
        String team;
        
        JSONObject json = readJsonFromUrl(sURL); //Get data for the team
        
        getTeams = json.toString();
        
        //System.out.println(getTeams);
        
        while (getTeams.contains("number"))
        {
            team = getTeams.substring(getTeams.indexOf("number") + 9, getTeams.indexOf("country") - 3);
            
            System.out.print(team);
            
            getTeams = getTeams.substring(getTeams.indexOf("country") + 7);
        }
        System.out.println("");
        
    }
    
}
