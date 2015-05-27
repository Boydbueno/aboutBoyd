package bueno.boyd.aboutboyd.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bueno.boyd.aboutboyd.Quote;
import bueno.boyd.aboutboyd.R;

public class QuoteDescriptionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quote_description, container, false);
    }

    public void updateDescription(Quote quote) {
        TextView author = (TextView) getView().findViewById(R.id.author);
        TextView message = (TextView) getView().findViewById(R.id.message);

        author.setText(quote.author);
        message.setText(quote.message);
    }
}
