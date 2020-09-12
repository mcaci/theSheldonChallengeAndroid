/**
 * 
 */
package app.tsc.ui;

import app.tsc.InGameActivity;

/**
 * @author nikiforos
 * 
 */
public final class UICore {

	/**
	 * class holder for the Singleton pattern
	 */
	private static class UICoreHolder {
		public static final UICore core = new UICore();
	}

	// managing gameplay changes
	private InGameActivity iga;

	/**
	 * private constructor following the Singleton pattern
	 */
	private UICore() {
	}

	public final static UICore getInstance() {
		return UICoreHolder.core;
	}

	public final void initializeGUIData() {
		this.setIga(null);
	}


	@Override
	public final String toString() {
		// TODO Auto-generated method stub
		// return super.toString();
		return "Core instance";
	}

	public final InGameActivity getIga() {
		return iga;
	}

	public final void setIga(InGameActivity iga) {
		this.iga = iga;
	}
}
