package app.tsc.listener;

import android.view.View;
import android.widget.EditText;
import app.tsc.InputPlayerActivity;
import app.tsc.R;
import core.tsc.AppCore;

public class GoButtonListener extends ActivityTransitionListener {
	
	private InputPlayerActivity ipa;

	public GoButtonListener(InputPlayerActivity ipa, Class<?> activityToOpen,
			boolean isTransitionPermanent) {
		super(activityToOpen, isTransitionPermanent);
		this.ipa = ipa;
	}
	
	@Override
	public void onClick(View v) {
		final String name1 = ((EditText) this.ipa.findViewById(R.id.nameP1Text)).getText().toString();
		AppCore.getInstance().getData().setNameP1(name1);
		
		// TODO: in the future same for p2
		
		super.onClick(v);
	}

}
