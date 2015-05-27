package bueno.boyd.aboutboyd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuoteAdapter extends ArrayAdapter<Quote> {
    public QuoteAdapter(Context context, ArrayList<Quote> quotes) {
        super(context, 0, quotes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Quote quote = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_quote, parent, false);
        }

        TextView quoteAuthor = (TextView) convertView.findViewById(R.id.author);

        quoteAuthor.setText(quote.author);

        return convertView;
    }
}
