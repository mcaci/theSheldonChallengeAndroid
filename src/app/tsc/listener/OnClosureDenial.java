package app.tsc.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public final class OnClosureDenial implements OnClickListener {

	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.cancel();
	}

}
