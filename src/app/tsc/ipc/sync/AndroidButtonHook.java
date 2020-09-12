package app.tsc.ipc.sync;

import core.tsc.ipc.IGameSynchro;

/**
 * This class is used to block computation while waiting that a player decides its move
 * This happens especially with swing where the main thread doesn't wait for the player to decide
 * It involves the Thread to be blocked and the IPlayer that need the blocking 
 * @author nikiforos
 *
 */
public class AndroidButtonHook implements IGameSynchro {

	public AndroidButtonHook() {}
	
	@Override
	public synchronized void holdIt() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	synchronized public final void resume(){
		this.notify();
	}

}