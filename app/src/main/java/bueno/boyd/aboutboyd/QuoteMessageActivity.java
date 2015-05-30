package bueno.boyd.aboutboyd;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class QuoteMessageActivity extends FragmentActivity {

    @InjectView(R.id.author) TextView author;
    @InjectView(R.id.message) TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_message);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.inject(this);

        Intent intent = getIntent();
        String author = intent.getStringExtra(MainActivity.EXTRA_QUOTE_AUTHOR);
        String message = intent.getStringExtra(MainActivity.EXTRA_QUOTE_MESSAGE);

        this.author.setText(author);
        this.message.setText(message);
    }

}
