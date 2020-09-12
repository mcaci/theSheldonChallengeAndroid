package app.tsc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import app.tsc.listener.ActivityTransitionListener;
import app.tsc.util.BaseTSCActivity;
import core.tsc.lang.vocab.BackButtonLabel;
import core.tsc.lang.vocab.GameRelatedButtonLabel;

public final class EndGameActivity extends BaseTSCActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_endgame);
        
        Bundle extras = getIntent().getExtras();
        String outcome = null;
        int imageID = 0;
        if(extras != null){
        	if(extras.containsKey("app.tsc.surrender")){
        		outcome = extras.getString("app.tsc.surrender");
        		imageID = extras.getInt("app.tsc.surr_image");
        	}
        	else if(extras.containsKey("app.tsc.regular_exit")){
        		outcome = extras.getString("app.tsc.regular_exit");
        		imageID = extras.getInt("app.tsc.exit_image");
        	}
        	else {
        		// TODO: exception
        	}
        }
        
        TextView textView = (TextView) findViewById(R.id.outcome_text);
		textView.setText(outcome);
		ImageView imageView = (ImageView) findViewById(R.id.outcome_image);
		imageView.setImageDrawable(getResources().getDrawable(imageID));

        Button b = null;
        b = (Button) findViewById(R.id.button_rematch);
        b.setOnClickListener(new ActivityTransitionListener(InGameActivity.class, true));
        b = (Button) findViewById(R.id.button_mainmenu);
        b.setOnClickListener(new ActivityTransitionListener(MainWinActivity.class, true));
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		setLabel(this, (Button) findViewById(R.id.button_rematch), GameRelatedButtonLabel.REMATCH);
		setLabel(this, (Button) findViewById(R.id.button_mainmenu), BackButtonLabel.MAIN_MENU);
	}

}
