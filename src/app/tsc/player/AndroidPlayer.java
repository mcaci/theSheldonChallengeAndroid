package app.tsc.player;

import app.tsc.InGameActivity;
import app.tsc.ipc.sync.AndroidButtonHook;
import app.tsc.ui.UICore;
import core.tsc.player.exception.NoPlayerNameException;
import core.tsc.player.exception.UnknownRuleException;
import core.tsc.player.impl.afc.AliveForChoosingPlayer;
import core.tsc.rule.Rule;
import core.tsc.rule.ruleSet.IRuleSet;

public class AndroidPlayer extends AliveForChoosingPlayer {

	private AndroidButtonHook buttonWaiter = null;

	/**
	 * @param p
	 * @param ruleUsed
	 */
	public AndroidPlayer(String name, Rule ruleUsed) throws NoPlayerNameException,
			UnknownRuleException {
		super(name, ruleUsed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see player.IPlayer#chooseMove(rule.RuleList)
	 */
	@Override
	public final IRuleSet chooseMove(Rule ruleUsed) throws UnknownRuleException {

		InGameActivity iga = UICore.getInstance().getIga();

		AndroidButtonHook playerSynchronizer = new AndroidButtonHook();
		iga.setPlayerSynchronizer(playerSynchronizer);
		this.setButtonWaiter(playerSynchronizer);
		this.getButtonWaiter().holdIt();

		switch (ruleUsed) {
		case CLASSIC:
		case SHELDON:
			return iga.getMoveToReturn();
		default:
			throw new UnknownRuleException();
			// code here is unreachable... no return statement (no break even
			// since all return or throw)
		}
	}

	public void setButtonWaiter(AndroidButtonHook buttonWaiter) {
		this.buttonWaiter = buttonWaiter;
	}

	public AndroidButtonHook getButtonWaiter() {
		return buttonWaiter;
	}

}
