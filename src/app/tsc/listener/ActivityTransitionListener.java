package app.tsc.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityTransitionListener implements OnClickListener {
	
	private Class<?> activityToOpen;
	private boolean isTransitionPermanent;
	
	public ActivityTransitionListener(final Class<?> activityToOpen, boolean isTransitionPermanent) {
		this.activityToOpen = activityToOpen;
		this.isTransitionPermanent = isTransitionPermanent;
	}

	@Override
	public void onClick(View v) {
		final Activity invoker = (Activity) v.getContext();
		final Intent i = new Intent(invoker, activityToOpen);
		invoker.startActivity(i);
		if(isTransitionPermanent){
			invoker.finish();
		}
	}

}
