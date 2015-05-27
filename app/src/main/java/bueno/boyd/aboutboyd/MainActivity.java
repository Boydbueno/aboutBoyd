package bueno.boyd.aboutboyd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "bueno.boyd.aboutme";

    public final static String EXTRA_LATITUDE = "bueno.boyd.aboutme.LATITUDE";
    public final static String EXTRA_LONGITUDE = "bueno.boyd.aboutme.LONGITUDE";

    private GoogleApiClient mGoogleApiClient;

    private double lat;
    private double lon;


    @InjectView(R.id.latitude) TextView latitudeView;
    @InjectView(R.id.longitude) TextView longitudeView;
    @InjectView(R.id.show_on_map) Button showOnMapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean showLocation = prefs.getBoolean("checkbox_location_preference", true);

        if (showLocation) {
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    @OnClick(R.id.show_on_map)
    public void goToLocationActivity() {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lon);
        startActivity(intent);
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
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
            latitudeView.setText("Latitude: " + String.valueOf(lat));
            longitudeView.setText("Longitude: " + String.valueOf(lon));
            showOnMapBtn.setVisibility(View.VISIBLE);
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


}
