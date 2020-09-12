package app.tsc.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.widget.TextView;
import core.tsc.AppCore;
import core.tsc.lang.Translatable;
import core.tsc.lang.translator.TranslateMethod;
import core.tsc.lang.vocab.OtherMessage;
import core.tsc.util.Support;

public abstract class BaseTSCActivity extends Activity {

    private static ArrayList<Activity> activities = new ArrayList<Activity>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }

    public static final void finishAll() {
        for(Activity activity:activities)
           activity.finish();
    }
	
	public final static void setLabel(TextView tv, Translatable text, TranslateMethod method){
		String label = Support.executeTranslation(
        		method, text,
        		AppCore.getInstance().getSessionLanguage());
		if(text.equals(OtherMessage.CREDITS)){
			label += ("\n" + AppCore.MAIL_ADDRESS);
		}
        tv.setText(label);
	}
	
	public final static void setLabel(Context c, TextView tv, Translatable text){
        setLabel(tv, text, new AndroidXMLFileTranslator(c));
	}
	
	public final static void setPrefTitle(Preference res, Translatable text, TranslateMethod method){
		String label = Support.executeTranslation(
        		method, text,
        		AppCore.getInstance().getSessionLanguage());
        res.setTitle(label);
	}
	
	public final static void setPrefTitle(Preference res, Translatable text){
        setPrefTitle(res, text, new AndroidXMLFileTranslator(res.getContext()));
	}
	
	public final static void setPrefSummary(Preference res, String label){
        res.setSummary(label);
	}
	
    public final static void killApp(boolean killSafely) {
        if (killSafely) {
        	finishAll();
            System.runFinalizersOnExit(true);
            System.exit(0);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
