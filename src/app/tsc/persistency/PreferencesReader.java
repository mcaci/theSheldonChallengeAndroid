package app.tsc.persistency;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import app.tsc.R;
import core.tsc.AppCoreData;
import core.tsc.lang.Language;
import core.tsc.mechanics.gameSession.IGameSession;
import core.tsc.persistency.IOptionManager;
import core.tsc.persistency.exception.ReadNotSupportedException;
import core.tsc.persistency.exception.WriteNotSupportedException;
import core.tsc.rule.Rule;

public final class PreferencesReader implements IOptionManager {

	private Context context;
	
	public PreferencesReader(Context c) {
		this.context = c;
	}
	
	@Override
	public AppCoreData readOptions() throws ReadNotSupportedException {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String lang = prefs.getString(this.context.getString(R.string.lang_value), Language.defaultElement().name());
		String maxScore = prefs.getString(this.context.getString(R.string.maxScore_value), new Integer(IGameSession.DEFAULT_WIN_SCORE).toString());
		String rule = prefs.getString(this.context.getString(R.string.rule_value), Rule.defaultElement().toString());
		
		return new AppCoreData(DefaultSetting.PLAYER_1_NAME.getValue(),
				DefaultSetting.PLAYER_2_NAME.getValue(),
				lang,
				maxScore,
				rule);
	}

	@Override
	public boolean writeOptions(AppCoreData data) throws WriteNotSupportedException {
		throw new WriteNotSupportedException(this);
	}

}
