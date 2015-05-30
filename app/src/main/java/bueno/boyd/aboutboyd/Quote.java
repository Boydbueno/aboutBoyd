package bueno.boyd.aboutboyd;

import org.json.JSONException;
import org.json.JSONObject;

public class Quote {
    public String author;
    public String message;

    public Quote(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public Quote(JSONObject quote) throws JSONException {
        this.author = quote.getString("author");
        this.message = quote.getString("message");
    }
}
