package app.tsc.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import app.tsc.util.BaseTSCActivity;

public final class OnClosureConfirmation implements OnClickListener {

	
	public OnClosureConfirmation() {}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		BaseTSCActivity.killApp(true);
	}

}
