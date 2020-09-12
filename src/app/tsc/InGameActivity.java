package app.tsc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.tsc.ipc.com.AndroidLabelHook;
import app.tsc.ipc.sync.AndroidButtonHook;
import app.tsc.listener.PlayerMoveListener;
import app.tsc.mechanics.gameSession.AutoAndroidGame;
import app.tsc.mechanics.gameSession.RB_Android_GS;
import app.tsc.player.AndroidPlayer;
import app.tsc.ui.UICore;
import app.tsc.util.AndroidXMLFileTranslator;
import app.tsc.util.BaseTSCActivity;
import core.tsc.AppCore;
import core.tsc.ipc.IntercomCore;
import core.tsc.ipc.communication.InGameUpdateContext;
import core.tsc.lang.Language;
import core.tsc.lang.vocab.GameRelatedButtonLabel;
import core.tsc.lang.vocab.OtherMessage;
import core.tsc.mechanics.gameSession.IGameSession;
import core.tsc.player.exception.NoPlayerNameException;
import core.tsc.player.exception.UnknownRuleException;
import core.tsc.player.impl.afc.RandomPlayer;
import core.tsc.rule.Rule;
import core.tsc.rule.ruleSet.IRuleSet;
import core.tsc.rule.ruleSet.impl.ClassicRuleSet;
import core.tsc.rule.ruleSet.impl.SheldonRuleSet;

public final class InGameActivity extends BaseTSCActivity {
	
	private IRuleSet moveToReturn;
	
	private AndroidLabelHook labelUpdater;
	private AndroidButtonHook playerSynchronizer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_ingame);
        
        UICore.getInstance().setIga(this);
        IntercomCore.getInstance().setHook(new AndroidLabelHook());
		this.registerToHook((AndroidLabelHook) (IntercomCore.getInstance().getHook()));
        
    	RelativeLayout rl = (RelativeLayout) findViewById(R.id.ingame_choosing_space);
    	LayoutInflater vi = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE); 
    	
    	Rule r = AppCore.getInstance().getSessionRule();
        switch(r){
        case CLASSIC:
        default:
        	View c = vi.inflate(R.layout.buttonspace_classic, null, false); 
        	rl.addView(c, new RelativeLayout.LayoutParams(rl.getLayoutParams().width, rl.getLayoutParams().height));
        	break;
        case SHELDON:
        	View s = vi.inflate(R.layout.buttonspace_sheldon, null, false); 
        	rl.addView(s, new RelativeLayout.LayoutParams(rl.getLayoutParams().width, rl.getLayoutParams().height));
        	break;
        }
        
        this.setButtonListeners(r);
        this.startGame();
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		
		((TextView)findViewById(R.id.nameText_player1)).setText(AppCore.getInstance().getNameP1());
		((TextView)findViewById(R.id.nameText_player2)).setText(AppCore.DEFAULT_NAME_P2);
		
		setLabel(this, (Button)findViewById(R.id.button_surrender), GameRelatedButtonLabel.SURRENDER);
    	setLabel(this, (Button)findViewById(R.id.button_random), GameRelatedButtonLabel.RANDOM);
    	
    	Rule r = AppCore.getInstance().getSessionRule();
        switch(r){
        case CLASSIC:
        default:
        	setLabel(this, (Button)findViewById(R.id.button_rock), ClassicRuleSet.ROCK);
        	setLabel(this, (Button)findViewById(R.id.button_paper), ClassicRuleSet.PAPER);
        	setLabel(this, (Button)findViewById(R.id.button_scissors), ClassicRuleSet.SCISSORS);
        	break;
        case SHELDON:
        	setLabel(this, (Button)findViewById(R.id.button_rock), SheldonRuleSet.ROCK);
        	setLabel(this, (Button)findViewById(R.id.button_paper), SheldonRuleSet.PAPER);
        	setLabel(this, (Button)findViewById(R.id.button_scissors), SheldonRuleSet.SCISSORS);
        	setLabel(this, (Button)findViewById(R.id.button_lizard), SheldonRuleSet.LIZARD);
        	setLabel(this, (Button)findViewById(R.id.button_spock), SheldonRuleSet.SPOCK);
        	break;
        }
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
    private void setButtonListeners(Rule r) {
        ((Button) findViewById(R.id.button_surrender)).setOnClickListener(new PlayerMoveListener(R.id.button_surrender));
    	((Button) findViewById(R.id.button_random)).setOnClickListener(new PlayerMoveListener(R.id.button_random));
    	switch(r){
    	case CLASSIC:
    		((Button) findViewById(R.id.button_rock)).setOnClickListener(new PlayerMoveListener(R.id.clRock));
    		((Button) findViewById(R.id.button_paper)).setOnClickListener(new PlayerMoveListener(R.id.clPaper));
    		((Button) findViewById(R.id.button_scissors)).setOnClickListener(new PlayerMoveListener(R.id.clScissors));
    		break;
    	case SHELDON:
    		((Button) findViewById(R.id.button_rock)).setOnClickListener(new PlayerMoveListener(R.id.shRock));
    		((Button) findViewById(R.id.button_paper)).setOnClickListener(new PlayerMoveListener(R.id.shPaper));
    		((Button) findViewById(R.id.button_scissors)).setOnClickListener(new PlayerMoveListener(R.id.shScissors));
    		((Button) findViewById(R.id.button_lizard)).setOnClickListener(new PlayerMoveListener(R.id.shLizard));
    		((Button) findViewById(R.id.button_spock)).setOnClickListener(new PlayerMoveListener(R.id.shSpock));
    		break;
		default:
			try {
				throw new UnknownRuleException();
			} catch (UnknownRuleException e) {
				e.printStackTrace();
			}
    	}
	}
    
    private final void registerToHook(AndroidLabelHook hook){
		hook.setIga(this);
		hook.setHandler(new OutcomeHandler());
		this.setLabelUpdater(hook);
	}
    
    private final void startGame(){
      	
	  	AndroidPlayer p1 = null;
	  	RandomPlayer p2 = null;
		Rule r = AppCore.getInstance().getSessionRule();
		
		try {
			p1 = new AndroidPlayer(AppCore.getInstance().getNameP1(), r);
			p2 = new RandomPlayer(AppCore.getInstance().getNameP2(), r);
		} catch (NoPlayerNameException e) {
			e.printStackTrace();
		} catch (UnknownRuleException e) {
			e.printStackTrace();
		}
  	
		// start game
		IGameSession gs = null;
		gs = new RB_Android_GS(p1, p2, AppCore.getInstance().getScoreToWin());

		new Thread(gs, "GAME SESSION").start();
    }
    
    @SuppressWarnings("unused")
	private final void startAutoGame(){
      	
	  	RandomPlayer p1 = null;
	  	RandomPlayer p2 = null;
		Rule r = AppCore.getInstance().getSessionRule();
		
		try {
			p1 = new RandomPlayer(AppCore.getInstance().getNameP1(), r);
			p2 = new RandomPlayer(AppCore.getInstance().getNameP2(), r);
		} catch (NoPlayerNameException e) {
			e.printStackTrace();
		} catch (UnknownRuleException e) {
			e.printStackTrace();
		}
  	
		// start game
		IGameSession gs = null;
		gs = new AutoAndroidGame(p1, p2, AppCore.getInstance().getScoreToWin());

		new Thread(gs, "GAME SESSION").start();
    }
    
    public final IRuleSet getMoveToReturn() {
		return moveToReturn;
	}

	public final void setMoveToReturn(IRuleSet moveToReturn) {
		this.moveToReturn = moveToReturn;
	}

	public void setLabelUpdater(AndroidLabelHook labelUpdater) {
		this.labelUpdater = labelUpdater;
	}

	public AndroidLabelHook getLabelUpdater() {
		return labelUpdater;
	}

	public void setPlayerSynchronizer(AndroidButtonHook playerSynchronizer) {
		this.playerSynchronizer = playerSynchronizer;
	}

	public AndroidButtonHook getPlayerSynchronizer() {
		return playerSynchronizer;
	}
	
	void exitGame(){
		// set strings for printing outcome
		final String label1 = ((TextView)this.findViewById(R.id.nameText_player1)).getText().toString();
		final String label2 = ((TextView)this.findViewById(R.id.nameText_player2)).getText().toString();
		
		final int score1 = Integer.parseInt(((TextView)this.findViewById(R.id.ingame_1_score)).getText().toString());
		final int score2 = Integer.parseInt(((TextView)this.findViewById(R.id.ingame_2_score)).getText().toString());
		
		final String sP1 = label1 + ": " + score1 + "\n";
		final String sP2 = label2 + ": " + score2 + "\n";
		
		final String sWin = (score1 > score2 ? label1 : label2);
		
		final Intent i = new Intent(this, EndGameActivity.class);
		Language currentLang = AppCore.getInstance().getSessionLanguage();
		i.putExtra("app.tsc.regular_exit", sP1 + sP2 + sWin + " " + new AndroidXMLFileTranslator(this).translate(OtherMessage.END_GAME, currentLang));
		if (score1 > score2) {
			i.putExtra("app.tsc.exit_image", R.drawable.smile);
		} else {
			i.putExtra("app.tsc.exit_image", R.drawable.sad);
		}
		this.startActivity(i);
	    this.finish();
	}

	private class OutcomeHandler extends Handler {
		
	    @Override
	    public void handleMessage(Message msg) {
	      Bundle bundle = msg.getData();
	      final String s1 = bundle.getString("1");
	      final String s2 = bundle.getString("2");

	      TextView tv1 = null;
	      TextView tv2 = null;
	      
	      switch(InGameUpdateContext.valueOf(bundle.getString("whatToUpdate"))){
	      case LABEL:
	    	  tv1 = (TextView) findViewById(R.id.ingame_1_move);
	    	  tv2 = (TextView) findViewById(R.id.ingame_2_move);
	    	  break;
	      case SCORE:
	    	  tv1 = (TextView) findViewById(R.id.ingame_1_score);
	    	  tv2 = (TextView) findViewById(R.id.ingame_2_score);
	    	  break;
	      case END:
	    	  exitGame();
	    	  return;
	      }
	      tv1.setText(s1);
	      tv2.setText(s2);
	      
	    }
	  }

}
