/**
 * Abstract class for representing basic framework of getting and handling the json from the json
 * requests
 */
package nightcrysis.project_walk.Backend.urlthreads;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class UrlThread extends Thread{

    protected String url;
    protected String json;
    protected JsonArray jsonArray;

    /**
     *
     * @param url The api request that returns a json element
     */
    public UrlThread(String url){
        this.url = url;
    }

    protected UrlThread() {
    }

    /**
     * Connects to the url and parses the json to a string and stores it to the class variable json
     */
    public void run(){
        try {
            URL url = new URL(this.url);

            //connects to the url, if the response code isn't 200 then something isn't ok
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            if(urlConn.getResponseCode() != 200){
                throw new IOException(urlConn.getResponseMessage());
            }

            //Build the json string from the url
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            this.json = sb.toString();

            JsonParser jsonParser = new JsonParser();
            JsonElement je = jsonParser.parse(json);
            JsonArray jsonArr1 = je.getAsJsonArray();
            jsonArray = jsonArr1.get(1).getAsJsonArray();

            //handling of the json string via overridden method
            processJson();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to overide to implement specific handling the json string, will be called in run
     * once the json is retrieved
     */
    protected abstract void processJson();
}
