package bueno.boyd.aboutboyd.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

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

        QuoteAdapter adapter = new QuoteAdapter(getActivity().getBaseContext(), quotes);
        quotes.add(new Quote("Boyd", "Roses are awesome"));
        quotes.add(new Quote("Kimberley", "Roses are kawaii"));
        quotes.add(new Quote("Tim", "Roses are stupid"));

        setListAdapter(adapter);
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

        callback.onQuoteSelected(quotes, (int)id);
    }

}
