package bueno.boyd.aboutboyd.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import bueno.boyd.aboutboyd.R;

public class Prefs1Fragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
