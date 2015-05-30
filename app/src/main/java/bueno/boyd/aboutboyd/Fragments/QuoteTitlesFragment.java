package bueno.boyd.aboutboyd.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bueno.boyd.aboutboyd.InternetConnectionDetector;
import bueno.boyd.aboutboyd.MainActivity;
import bueno.boyd.aboutboyd.Quote;
import bueno.boyd.aboutboyd.QuoteAdapter;
import bueno.boyd.aboutboyd.R;

public class QuoteTitlesFragment extends ListFragment {

    private ArrayList<Quote> quotes = new ArrayList<Quote>();

    OnQuoteSelectedListener callback;

    // Container Activity must implement this interface
    public interface OnQuoteSelectedListener {
        public void onQuoteSelected(ArrayList<Quote> quotes, long position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_quote_titles, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final QuoteAdapter adapter = new QuoteAdapter(getActivity().getBaseContext(), quotes);

        InternetConnectionDetector connectionDetector = new InternetConnectionDetector(getActivity());

        if (!connectionDetector.isNetworkAvailable()) {
            Toast toast = Toast.makeText(getActivity(), "You'll need an internet connection to view all quotes", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        getAndAddQuotes(adapter);
    }

    private void getAndAddQuotes(final QuoteAdapter adapter) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://178.62.135.117/quotes";

        JsonRequest stringRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject quote = response.getJSONObject(i);
                                quotes.add(new Quote(quote));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setListAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Context context = getActivity().getBaseContext();
                        String text = new String(error.networkResponse.data);
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

        queue.add(stringRequest);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnQuoteSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        callback.onQuoteSelected(quotes, (int) id);
    }

}
