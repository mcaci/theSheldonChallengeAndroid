package app.tsc.util;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.content.Context;
import android.content.res.Resources;
import app.tsc.R;
import core.tsc.lang.Language;
import core.tsc.lang.translator.XMLTranslator;
import core.tsc.util.Support;

public final class AndroidXMLFileTranslator extends XMLTranslator {
	
	private Context c;
	
	public AndroidXMLFileTranslator(Context c) {
		this.c = c;
	}
	
	public final Document loadVocabularyDocument(Language lang) {
		final Resources resources = c.getResources();
		final InputStream stream = resources.openRawResource(getRawID(lang));
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static final int getRawID(Language l){
		switch(l){
		case ALBANIAN:
			return R.raw.albanian;
		case DUTCH:
			return R.raw.dutch;
		case ENGLISH:
			return R.raw.english;
		case FRENCH:
			return R.raw.french;
		case GERMAN:
			return R.raw.german;
		case ITALIAN:
			return R.raw.italian;
		case PERSIAN:
			return R.raw.persian;
		case PORTUGUESE:
			return R.raw.portuguese;
		case SPANISH:
			return R.raw.spanish;
		default:
//			TODO: change with nicer exception
			Support.notImplYet();
			break;
		}
		return 0;
	}
}
