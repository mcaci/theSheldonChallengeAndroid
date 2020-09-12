package app.tsc.listener;

import android.view.View;
import android.widget.Button;
import app.tsc.InputPlayerActivity;
import app.tsc.R;
import app.tsc.ipc.com.AndroidLabelHook;
import core.tsc.AppCore;
import core.tsc.ipc.IntercomCore;
import core.tsc.mechanics.GameType;
import core.tsc.player.exception.NoPlayerNameException;
import core.tsc.player.exception.UnknownRuleException;

public final class GameTypeActionListener extends ActivityTransitionListener {

	public GameTypeActionListener() {
		super(InputPlayerActivity.class, false);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		
		try {
			setHooks((Button) v);
		} catch (NoPlayerNameException e) {
			e.printStackTrace();
		} catch (UnknownRuleException e) {
			e.printStackTrace();
		}
	}
	
	private final void setHooks(Button source) throws NoPlayerNameException, UnknownRuleException {
		
		IntercomCore.getInstance().setHook(new AndroidLabelHook());
		if(source.getId() == R.id.button_twoPlayerGame) {
			AppCore.getInstance().getData().setTypeOfGame(GameType.PLAYER_VS_PLAYER);
		}
		else if(source.getId() == R.id.button_onePlayerGame) {
			AppCore.getInstance().getData().setTypeOfGame(GameType.PLAYER_VS_AI);
			AppCore.getInstance().getData().setNameP2(AppCore.DEFAULT_NAME_P2);
		}
		else { //TODO: else if(source instanceof ZeroPlayerButton) {
			AppCore.getInstance().getData().setTypeOfGame(GameType.AI_VS_AI);
		}
		
	}

}
