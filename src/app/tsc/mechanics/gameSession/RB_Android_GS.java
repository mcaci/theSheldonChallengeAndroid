package app.tsc.mechanics.gameSession;

import app.tsc.ipc.com.AndroidLabelHook;
import app.tsc.player.AndroidPlayer;
import core.tsc.ipc.IGameHook;
import core.tsc.mechanics.gameSession.classic.RoundBasedGS;
import core.tsc.player.impl.afc.RandomPlayer;

public final class RB_Android_GS extends RoundBasedGS {

	public RB_Android_GS(AndroidPlayer p1, RandomPlayer p2) {
		super(p1, p2);
	}

	public RB_Android_GS(AndroidPlayer p1, RandomPlayer p2, int winScore) {
		super(p1, p2, winScore);
	}
	
	@Override
	protected void registerToHook(IGameHook hook) {
		AndroidLabelHook tmp = (AndroidLabelHook) hook;
		tmp.setGs(this);
		this.setLabelUpdater(tmp);
		tmp = null;
	}

}
