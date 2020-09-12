package app.tsc.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import app.tsc.EndGameActivity;
import app.tsc.InGameActivity;
import app.tsc.R;
import app.tsc.ui.UICore;
import app.tsc.util.AndroidXMLFileTranslator;
import core.tsc.AppCore;
import core.tsc.lang.Language;
import core.tsc.lang.vocab.OtherMessage;
import core.tsc.player.impl.DummyPlayer;
import core.tsc.rule.ruleSet.IRuleSet;
import core.tsc.rule.ruleSet.impl.ClassicRuleSet;
import core.tsc.rule.ruleSet.impl.SheldonRuleSet;

public final class PlayerMoveListener implements OnClickListener {

	private IRuleSet correspondingMove;
	
	public PlayerMoveListener(int resID) {
		switch(resID){
		case R.id.clRock:
			this.correspondingMove = ClassicRuleSet.ROCK;
			break;
		case R.id.clPaper:
			this.correspondingMove = ClassicRuleSet.PAPER;
			break;
		case R.id.clScissors:
			this.correspondingMove = ClassicRuleSet.SCISSORS;
			break;
		case R.id.shRock:
			this.correspondingMove = SheldonRuleSet.ROCK;
			break;
		case R.id.shPaper:
			this.correspondingMove = SheldonRuleSet.PAPER;
			break;
		case R.id.shScissors:
			this.correspondingMove = SheldonRuleSet.SCISSORS;
			break;
		case R.id.shLizard:
			this.correspondingMove = SheldonRuleSet.LIZARD;
			break;
		case R.id.shSpock:
			this.correspondingMove = SheldonRuleSet.SPOCK;
			break;
		default:
			this.correspondingMove = null;
			break;
		}
	}
	
	/**
	 * TODO: works only with one player
	 */
	@Override
	public synchronized final void onClick(View v) {

		final InGameActivity iga = UICore.getInstance().getIga();
		IRuleSet move = null;
		if(correspondingMove == null){
			// random button here... 
			if(v.getId() == R.id.button_random){
				move = DummyPlayer.chooseRandomMove(AppCore.getInstance().getSessionRule());
			}
			else if(v.getId() == R.id.button_surrender){
				// the surrender mark
				move = null;
				
				// set strings for printing outcome
				final String sP1 = ((TextView)iga.findViewById(R.id.nameText_player1)).getText() + ": "
						+ ((TextView)iga.findViewById(R.id.ingame_1_score)).getText() + "\n";
				final String sP2 = ((TextView)iga.findViewById(R.id.nameText_player2)).getText() + ": "
				+ ((TextView)iga.findViewById(R.id.ingame_2_score)).getText()  + "\n";
	
				// TODO: to change to determine who surrendered
				final String sWin = ((TextView)iga.findViewById(R.id.nameText_player2)).getText().toString();
				
				final Activity invoker = (Activity) v.getContext();
				final Intent i = new Intent(invoker, EndGameActivity.class);
				Language currentLang = AppCore.getInstance().getSessionLanguage();
				i.putExtra("app.tsc.surrender", sP1 + sP2 + sWin + " " + new AndroidXMLFileTranslator(iga).translate(OtherMessage.FORFEIT, currentLang))
						.putExtra("app.tsc.surr_image", R.drawable.disapp);
				invoker.startActivity(i);
				invoker.finish();
			}
		}
		else{
			move = this.correspondingMove;
		}
		iga.setMoveToReturn(move);
		iga.getPlayerSynchronizer().resume();
	}

}