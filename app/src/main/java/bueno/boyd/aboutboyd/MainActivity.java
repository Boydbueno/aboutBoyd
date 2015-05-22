package bueno.boyd.aboutboyd;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnAudioFocusChangeListener {
    private static final String TAG = "bueno.boyd.aboutme";

    private GoogleApiClient mGoogleApiClient;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    @InjectView(R.id.latitude) TextView latitudeView;
    @InjectView(R.id.longitude) TextView longitudeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus for playback
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            initMediaPlayer();
            if (audioManager.isBluetoothA2dpOn()) {
                // Adjust output for Bluetooth.
            } else if (audioManager.isSpeakerphoneOn()) {
                // Adjust output for Speakerphone.
            } else if (audioManager.isWiredHeadsetOn()) {
                // Adjust output for headsets
            } else {
                // If audio plays and noone can hear it, is it still playing?

            }
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean showLocation = prefs.getBoolean("checkbox_location_preference", true);

        if(showLocation) {
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        final Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitudeView.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
            longitudeView.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Failed");
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
