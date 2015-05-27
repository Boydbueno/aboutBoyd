package bueno.boyd.aboutboyd;

import android.app.ActionBar;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;

public class CreditsActivity extends BaseActivity implements AudioManager.OnAudioFocusChangeListener {
    private static final String TAG = "bueno.boyd.aboutme";

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            initMediaPlayer();
        }
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        mediaPlayer.release();;
        mediaPlayer = null;
        super.onBackPressed();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                mediaPlayer.stop();
                mediaPlayer.release();;
                mediaPlayer = null;
                break;

        }
        super.onMenuItemSelected(featureId, item);

        return true;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch(focusChange) {

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (mediaPlayer.isPlaying()) mediaPlayer.pause();
                break;

            case  AudioManager.AUDIOFOCUS_GAIN:
                Log.d(TAG, "Gain");
                if (mediaPlayer == null) initMediaPlayer();
                else if (!mediaPlayer.isPlaying()) mediaPlayer.start();
                mediaPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                if (mediaPlayer != null) {
                    Log.d(TAG, "Stopping");
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mediaPlayer.isPlaying()) mediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }

    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.nascence);
        mediaPlayer.start();
    }

}
