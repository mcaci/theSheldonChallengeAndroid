package app.tsc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.tsc.listener.GoButtonListener;
import app.tsc.util.BaseTSCActivity;
import core.tsc.AppCore;
import core.tsc.lang.vocab.GameRelatedButtonLabel;
import core.tsc.lang.vocab.OtherLabel;

public final class InputPlayerActivity extends BaseTSCActivity {
	
	@Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_inputplayer);
        
        Button b = (Button) findViewById(R.id.goButton);
        b.setOnClickListener(new GoButtonListener(this, InGameActivity.class, true));
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected final void onResume() {
		super.onResume();
        setLabel(this, (Button) findViewById(R.id.goButton), GameRelatedButtonLabel.START);
        setLabel(this, (TextView) findViewById(R.id.inputplayer_textview_prompt1), OtherLabel.ENTER_NAME);
        ((EditText) findViewById(R.id.nameP1Text)).setText(AppCore.getInstance().getNameP1());
        setLabel(this, (TextView) findViewById(R.id.inputplayer_textview_prompt2), OtherLabel.ENTER_NAME);
        // TODO: player 2 name
        ((EditText) findViewById(R.id.nameP2Text)).setText(AppCore.getInstance().getNameP2());
	}
    
}