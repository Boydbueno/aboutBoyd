package bueno.boyd.aboutboyd;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import bueno.boyd.aboutboyd.Fragments.QuoteDescriptionFragment;
import bueno.boyd.aboutboyd.Fragments.QuoteTitlesFragment;


public class QuoteActivity extends FragmentActivity implements QuoteTitlesFragment.OnQuoteSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_quote:
                Intent intent = new Intent(this, AddQuoteActivity.class);
                startActivityForResult(intent, 0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onQuoteSelected(ArrayList<Quote> quotes, long id) {

        QuoteDescriptionFragment quoteDescriptionFragment = (QuoteDescriptionFragment) getFragmentManager().findFragmentById(R.id.description_fragment);

        Quote quote = quotes.get((int) id);

        if (quoteDescriptionFragment != null && quoteDescriptionFragment.isVisible()) {

            quoteDescriptionFragment.updateDescription(quote);

        } else {

            Intent intent = new Intent(this, QuoteMessageActivity.class);

            intent.putExtra(MainActivity.EXTRA_QUOTE_AUTHOR, quote.author);
            intent.putExtra(MainActivity.EXTRA_QUOTE_MESSAGE, quote.message);

            startActivity(intent);

        }

    }
}
