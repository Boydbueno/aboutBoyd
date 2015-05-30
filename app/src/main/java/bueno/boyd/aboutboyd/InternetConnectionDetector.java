package bueno.boyd.aboutboyd;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @see: <a href="http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android">Source</a>
 */
public class InternetConnectionDetector {

    private Context context;

    public InternetConnectionDetector(Context context){
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
