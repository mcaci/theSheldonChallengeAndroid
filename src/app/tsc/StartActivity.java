package app.tsc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import app.tsc.persistency.PreferencesReader;
import core.tsc.AppCore;

public class StartActivity extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Data management: I need it to be done just once at the very beginning
        AppCore.getInstance().initializeGameData(new PreferencesReader(this));
        
        // Then go to the main menu window
        Intent i = new Intent(this, MainWinActivity.class);
        this.startActivity(i);
        this.finish();
    }

}
