package bueno.boyd.aboutboyd;

import android.app.ActionBar;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AboutActivity extends BaseActivity {

    @InjectView(R.id.about_video)
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.inject(this);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.about));
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });

    }

}
