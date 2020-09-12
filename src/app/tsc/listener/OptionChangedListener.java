package app.tsc.listener;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import app.tsc.R;
import app.tsc.util.BaseTSCActivity;
import core.tsc.AppCore;
import core.tsc.lang.Language;
import core.tsc.lang.vocab.OptionMenuLabel;
import core.tsc.rule.Rule;

public final class OptionChangedListener implements OnPreferenceChangeListener {

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		final PreferenceActivity pa = (PreferenceActivity) preference.getContext();
		if(preference.getKey().equals(pa.getString(R.string.lang_value))) {

			AppCore.getInstance().getData().setSessionLanguage(Language.valueOf((String) newValue));
			
			final Preference lang = pa.findPreference(pa.getString(R.string.lang_value));
			final Preference maxScore = pa.findPreference(pa.getString(R.string.maxScore_value));
			final Preference rule = pa.findPreference(pa.getString(R.string.rule_value));
			
			BaseTSCActivity.setPrefTitle(lang, OptionMenuLabel.LANGUAGE);
			BaseTSCActivity.setPrefTitle(maxScore, OptionMenuLabel.SCORE_TO_WIN);
			BaseTSCActivity.setPrefTitle(rule, OptionMenuLabel.RULE);
		}
		else if(preference.getKey().equals(pa.getString(R.string.maxScore_value))){
			AppCore.getInstance().getData().setScoreToWin(Integer.parseInt((String)newValue));
		}
		else if(preference.getKey().equals(pa.getString(R.string.rule_value))){
			AppCore.getInstance().getData().setSessionRule(Rule.valueOf((String) newValue));
		}
		else{
			// TODO: throw exception
		}
		
		BaseTSCActivity.setPrefSummary(preference, (String) newValue);
		return true;
	}

}
