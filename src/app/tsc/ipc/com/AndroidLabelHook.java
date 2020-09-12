package app.tsc.ipc.com;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import app.tsc.InGameActivity;
import app.tsc.util.AndroidXMLFileTranslator;
import core.tsc.AppCore;
import core.tsc.ipc.communication.DefaultHook;
import core.tsc.ipc.communication.InGameUpdateContext;
import core.tsc.lang.Language;
import core.tsc.mechanics.gameSession.classic.IClassicGS;
import core.tsc.util.Support;

public final class AndroidLabelHook extends DefaultHook {
	
	private Handler handler;
	
	// GameSession data
	private IClassicGS gs;
	
	// UI data
	private InGameActivity iga;
	
	@Override
	public final void updateUI(InGameUpdateContext whatToUpdate) {
		switch (whatToUpdate) {
		case SCORE:
			final String score1 = Integer.toString(this.gs.getP1().getScore());
			final String score2 = Integer.toString(this.gs.getP2().getScore());

			notifyMessage(whatToUpdate, score1, score2);
			break;
		case LABEL:
			final Language currentLang = AppCore.getInstance().getSessionLanguage();
			final String move1 = new AndroidXMLFileTranslator(iga).translate(this.gs.getP1()
					.getMove(), currentLang);
			final String move2 = new AndroidXMLFileTranslator(iga).translate(this.gs.getP2()
					.getMove(), currentLang);

			notifyMessage(whatToUpdate, move1, move2);
			break;
		case END:
			notifyMessage(whatToUpdate, null, null);
//			this.gol.actionPerformed(null);
			break;
		default:
			// TODO: change to UnexpectedContextException
			Support.notImplYet();
			break;
		}
	}
	
	private void notifyMessage(InGameUpdateContext whatToUpdate, String value1, String value2) {
	    Message msg = handler.obtainMessage();
	    Bundle b = new Bundle();
    	b.putString("whatToUpdate", whatToUpdate.name());
	    if(!whatToUpdate.equals(InGameUpdateContext.END)){
		    b.putString("1", value1);
		    b.putString("2", value2);
	    }
	    msg.setData(b);
	    handler.sendMessage(msg);
	}
	
	public final IClassicGS getGs() {
		return gs;
	}

	public final void setGs(IClassicGS gs) {
		this.gs = gs;
	}

	public final InGameActivity getIga() {
		return iga;
	}

	public final void setIga(InGameActivity iga) {
		this.iga = iga;
	}

	public final Handler getHandler() {
		return handler;
	}

	public final void setHandler(Handler handler) {
		this.handler = handler;
	}

}
