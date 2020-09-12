package app.tsc.mechanics.gameSession;

import app.tsc.ipc.com.AndroidLabelHook;
import core.tsc.ipc.IGameHook;
import core.tsc.mechanics.gameSession.classic.AutomaticGameGS;
import core.tsc.player.impl.afc.RandomPlayer;

public final class AutoAndroidGame extends AutomaticGameGS {

	public AutoAndroidGame(RandomPlayer p1, RandomPlayer p2) {
		super(p1, p2);
		// TODO Auto-generated constructor stub
	}

	public AutoAndroidGame(RandomPlayer p1, RandomPlayer p2, int winScore) {
		super(p1, p2, winScore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void registerToHook(IGameHook hook) {
		AndroidLabelHook tmp = (AndroidLabelHook) hook;
		tmp.setGs(this);
		this.setLabelUpdater(tmp);
		tmp = null;
	}

}
