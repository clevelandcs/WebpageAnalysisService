package main.java.webpage;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Path("/webpageservice")
public class WebpageService {
    @Path("{f}")
    @GET
    @Produces("application/json")
    public Response getWebpageInfoFromInput(@PathParam("f") String s) throws JSONException {
        String jsonString = null;
        String result = null;
        try {
            URL letsValidate = new URL("https://api.letsvalidate.com/v1/technologies/?url=" + s);
            jsonString = readUrl(letsValidate);
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            jsonObject.put("ThumbnailURL", "https://api.letsvalidate.com/v1/thumbs/?url=" + s);
            result = "@Produces(\"application/json\") Output: \n\nWebpage Analysis Output: \n\n" + jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(200).entity(result).build();
    }

    @GET
    @Produces("application/json")
    public Response getWebpageInfo() {
        String jsonString = null;
        String result = null;
        try {
            URL letsValidate = new URL("https://api.letsvalidate.com/v1/technologies/?url=clevelandcoding.com");
            jsonString = readUrl(letsValidate);
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            jsonObject.put("ThumbnailURL", "https://api.letsvalidate.com/v1/thumbs/?url=clevelandcoding.com");
            result = "@Produces(\"application/json\") Output: \n\nWebpage Analysis Output: \n\n" + jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(200).entity(result).build();
    }

    private static String readUrl(URL url) throws Exception {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}