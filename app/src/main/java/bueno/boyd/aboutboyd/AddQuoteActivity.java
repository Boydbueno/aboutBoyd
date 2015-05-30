package bueno.boyd.aboutboyd;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AddQuoteActivity extends BaseActivity {

    @InjectView(R.id.author) EditText author;
    @InjectView(R.id.message) EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_quote_button)
    public void postQuote() {
        String author = this.author.getText().toString();
        String message = this.message.getText().toString();

        if (author.isEmpty() || message.isEmpty()) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Please fill in both fields", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("author", author);
            jsonObject.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest stringRequest = new JsonObjectRequest(
                Request.Method.POST,
                "http://178.62.135.117/quotes",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context, "Quote has been added", Toast.LENGTH_LONG);
                        toast.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Context context = getApplicationContext();
//                        String text = new String(error.networkResponse.data);
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, "Something went wrong", duration);
                        toast.show();
                    }
                });

        queue.add(stringRequest);
    }
}
