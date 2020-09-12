package app.tsc;

import android.os.Bundle;
import android.widget.Button;
import app.tsc.listener.ActivityTransitionListener;
import app.tsc.util.BaseTSCActivity;
import core.tsc.lang.vocab.PlayerButtonLabel;

public final class NewGameActivity extends BaseTSCActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_newgame);
        this.setButtonListeners();
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    protected final void onResume() {
    	super.onResume();

    	setLabel(this, (Button)findViewById(R.id.button_onePlayerGame), PlayerButtonLabel.ONE_PLAYER);
    	setLabel(this, (Button)findViewById(R.id.button_twoPlayerGame), PlayerButtonLabel.TWO_PLAYERS);
    }
	
	private void setButtonListeners() {
		Button b = (Button) findViewById(R.id.button_onePlayerGame);
        b.setOnClickListener(new ActivityTransitionListener(InputPlayerActivity.class, true));
	}

}
