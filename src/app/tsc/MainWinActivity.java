package app.tsc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import app.tsc.listener.ActivityTransitionListener;
import app.tsc.listener.OnClosureConfirmation;
import app.tsc.listener.OnClosureDenial;
import app.tsc.util.AndroidXMLFileTranslator;
import app.tsc.util.BaseTSCActivity;
import core.tsc.AppCore;
import core.tsc.lang.Language;
import core.tsc.lang.translator.TranslateMethod;
import core.tsc.lang.vocab.BackButtonLabel;
import core.tsc.lang.vocab.BooleanAnswer;
import core.tsc.lang.vocab.MainMenuButtonLabel;
import core.tsc.lang.vocab.OtherMessage;
import core.tsc.util.Support;

public final class MainWinActivity extends BaseTSCActivity {
	
    /** Called when the activity is first created. */
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GUI management
		setContentView(R.layout.window_mainwin);
        this.setButtonListeners();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
    
    @Override
    protected final void onResume() {
    	super.onResume();

    	setLabel(this, (Button)findViewById(R.id.button_newgame), MainMenuButtonLabel.NEW_GAME);
    	setLabel(this, (Button)findViewById(R.id.button_options), MainMenuButtonLabel.OPTIONS);
    	setLabel(this, (Button)findViewById(R.id.button_credits), MainMenuButtonLabel.CREDITS);
    }
    
	@Override
    public final void onBackPressed() {
    	quitGameWithAlert();
    }
    
    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	MenuInflater mi = getMenuInflater();
    	mi.inflate(R.menu.menu_main, menu);
    	
    	return true;
    }
    
    @Override
    public final boolean onPrepareOptionsMenu(Menu menu) {
    	String title = Support.executeTranslation(
        		new AndroidXMLFileTranslator(this),
        		BackButtonLabel.QUIT,
        		AppCore.getInstance().getSessionLanguage());
    	MenuItem item = menu.getItem(0);
    	item.setTitle(title);
    	item.setTitleCondensed(title);
    	
    	return true;
    }
    
    @Override
    public final boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menuItem_quit:
			quitGameWithAlert();
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
    }
    
    // --------------- AUX METHODS --------------- //
    
    private final void setButtonListeners() {

        Button b = null;
        b = (Button) findViewById(R.id.button_newgame);
        b.setOnClickListener(new ActivityTransitionListener(NewGameActivity.class, false));
        b = (Button) findViewById(R.id.button_options);
        b.setOnClickListener(new ActivityTransitionListener(OptionsActivity.class, false));
        b = (Button) findViewById(R.id.button_credits);
        b.setOnClickListener(new ActivityTransitionListener(CreditsActivity.class, false));
	}

	private final void quitGameWithAlert(){
    	// do something on back.
    	final TranslateMethod tm = new AndroidXMLFileTranslator(this);
    	final Language lang = AppCore.getInstance().getSessionLanguage();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(MainWinActivity.this);
        builder.setMessage(Support.executeTranslation(tm, OtherMessage.EXIT_CONFIRMATION, lang))
               .setCancelable(false)
               .setPositiveButton(Support.executeTranslation(tm, BooleanAnswer.YES, lang), new OnClosureConfirmation())
               .setNegativeButton(Support.executeTranslation(tm, BooleanAnswer.NO, lang), new OnClosureDenial());
        AlertDialog alert = builder.create();
        alert.show();
           
   return;
    }
    
}