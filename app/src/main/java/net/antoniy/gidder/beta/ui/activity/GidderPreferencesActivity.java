package net.antoniy.gidder.beta.ui.activity;

import net.antoniy.gidder.beta.R;
import net.antoniy.gidder.beta.ui.util.C;
import net.antoniy.gidder.beta.ui.util.GidderCommons;
import net.antoniy.gidder.beta.ui.util.PrefsConstants;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class GidderPreferencesActivity extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener {

	private EditTextPreference sshPortPreferences;
	private EditTextPreference gitRepositoriesDirPreferences;

	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		
		sshPortPreferences = (EditTextPreference) getPreferenceScreen().findPreference(PrefsConstants.SSH_PORT.getKey());
		gitRepositoriesDirPreferences = (EditTextPreference) getPreferenceScreen().findPreference(PrefsConstants.GIT_REPOSITORIES_DIR.getKey());

		Preference helpButton = (Preference)findPreference(PrefsConstants.HELP.getKey());
		helpButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) { 
					GidderCommons.showTutorialDialog(GidderPreferencesActivity.this);
				    return true;
				}
			});

		mPrefs = getPreferenceScreen().getSharedPreferences();
		Preference importButton = (Preference)findPreference(PrefsConstants.IMPORT_REPOSITORIES.getKey());
		importButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				String directory = mPrefs.getString(PrefsConstants.GIT_REPOSITORIES_DIR.getKey(),
						PrefsConstants.GIT_REPOSITORIES_DIR.getDefaultValue());
				GidderCommons.importRepositoriesFromDirectory(GidderPreferencesActivity.this, directory);
				return true;
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(C.action.START_HOME_ACTIVITY);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			finish();
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
		
		sshPortPreferences.setSummary("SSH server port: " + prefs.getString(PrefsConstants.SSH_PORT.getKey(), PrefsConstants.SSH_PORT.getDefaultValue()));
		gitRepositoriesDirPreferences.setSummary("Repositories: " + prefs.getString(PrefsConstants.GIT_REPOSITORIES_DIR.getKey(), PrefsConstants.GIT_REPOSITORIES_DIR.getDefaultValue()));

		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(PrefsConstants.SSH_PORT.getKey())) {
			sshPortPreferences.setSummary("SSH server port: " + sharedPreferences.getString(
					PrefsConstants.SSH_PORT.getKey(), PrefsConstants.SSH_PORT.getDefaultValue()));
		} else if (key.equals(PrefsConstants.GIT_REPOSITORIES_DIR.getKey())) {
			gitRepositoriesDirPreferences.setSummary("Repositories: " + sharedPreferences.getString(
					PrefsConstants.GIT_REPOSITORIES_DIR.getKey(), PrefsConstants.GIT_REPOSITORIES_DIR.getDefaultValue()));
		}
	}

}
