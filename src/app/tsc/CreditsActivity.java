package app.tsc;

import android.os.Bundle;
import android.widget.TextView;
import app.tsc.util.BaseTSCActivity;
import core.tsc.lang.vocab.OtherMessage;

public final class CreditsActivity extends BaseTSCActivity {
	
	@Override
	public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_credits);
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    protected final void onResume() {
    	super.onResume();
    	TextView tv = (TextView) findViewById(R.id.credits_textview_note);
        setLabel(this, tv, OtherMessage.CREDITS);
    }

}
