/**
 * 
 */
package app.tsc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import app.tsc.listener.OptionChangedListener;
import app.tsc.util.BaseTSCActivity;
import core.tsc.AppCore;
import core.tsc.lang.Language;
import core.tsc.lang.vocab.OptionMenuLabel;
import core.tsc.mechanics.gameSession.IGameSession;
import core.tsc.rule.Rule;

/**
 * @author nikiforos
 *
 */
public final class OptionsActivity extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// showing displayed options
		addPreferencesFromResource(R.xml.prefs_option);
//		displayOptionSaveNote();
		
		Preference lang = findPreference(getString(R.string.lang_value));
		lang.setOnPreferenceChangeListener(new OptionChangedListener());
		Preference maxScore = findPreference(getString(R.string.maxScore_value));
		maxScore.setOnPreferenceChangeListener(new OptionChangedListener());
		Preference rule = findPreference(getString(R.string.rule_value));
		rule.setOnPreferenceChangeListener(new OptionChangedListener());
	}
	
	@Override
    protected final void onResume() {
    	super.onResume();

    	// SHOW ALL THE LABELS IN THE OPTION WINDOW
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String langValue = prefs.getString(getString(R.string.lang_value), Language.defaultElement().name());
		String maxScoreValue = prefs.getString(getString(R.string.maxScore_value), new Integer(IGameSession.DEFAULT_WIN_SCORE).toString());
		String ruleValue = prefs.getString(getString(R.string.rule_value), Rule.defaultElement().toString());
		
		Preference lang = findPreference(getString(R.string.lang_value));
		Preference maxScore = findPreference(getString(R.string.maxScore_value));
		Preference rule = findPreference(getString(R.string.rule_value));

    	BaseTSCActivity.setPrefTitle(lang, OptionMenuLabel.LANGUAGE);
    	BaseTSCActivity.setPrefTitle(maxScore, OptionMenuLabel.SCORE_TO_WIN);
    	BaseTSCActivity.setPrefTitle(rule, OptionMenuLabel.RULE);

    	BaseTSCActivity.setPrefSummary(lang, langValue);
    	BaseTSCActivity.setPrefSummary(maxScore, maxScoreValue);
    	BaseTSCActivity.setPrefSummary(rule, ruleValue);
    }
	
	@Override
	public void onBackPressed() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String lang = prefs.getString(getString(R.string.lang_value), Language.defaultElement().name());
		String maxScore = prefs.getString(getString(R.string.maxScore_value), new Integer(IGameSession.DEFAULT_WIN_SCORE).toString());
		String rule = prefs.getString(getString(R.string.rule_value), Rule.defaultElement().toString());
		
		AppCore.getInstance().getData().setOptionData(lang, maxScore, rule);
		
		super.onBackPressed();
	}
	
//	private final void displayOptionSaveNote() {
//    	Toast.makeText(this, R.string.opt_noteOnSave, Toast.LENGTH_LONG).show();
//	}
	
}
