package bueno.boyd.aboutboyd;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;


abstract public class BaseActivity extends Activity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_quit) {
            this.finish();
            System.exit(0);
            return true;
        } else if(id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if(id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if(id == R.id.action_credits) {
            Intent intent = new Intent(this, CreditsActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
