package app.tsc.mechanics.gameSession;

import core.tsc.ipc.synchronization.PlayerChoiceSynchronizer;
import core.tsc.mechanics.gameRound.GSBasedRound;
import core.tsc.mechanics.gameSession.classic.RoundBasedGS;
import core.tsc.player.IPlayer;

public final class SequentialGSBasedGR extends GSBasedRound {
	
	public SequentialGSBasedGR(RoundBasedGS info) {
		super(info);
	}

	protected final boolean promptMoves(IPlayer p1, IPlayer p2) {

		PlayerChoiceSynchronizer pcs = new PlayerChoiceSynchronizer();
		p1.setNotifier(pcs);
		p1.run();
		pcs.holdIt();
		
		p2.setNotifier(pcs);
		p2.run();
		pcs.holdIt();
		
		pcs = null;
		return true;
	}

}
