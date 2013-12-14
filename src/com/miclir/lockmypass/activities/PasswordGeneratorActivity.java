
package com.miclir.lockmypass.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.miclir.lockmypass.R;
import com.miclir.lockmypass.utils.PasswordGenerator;

/**
 * @author Michael & Liron
 * 
 */
public class PasswordGeneratorActivity extends Activity
{
	
	private static final String	TAG						= PasswordGeneratorActivity.class.getSimpleName();
	
	private static final String	SETTING_USE_LETTERS		= "UseLetters";
	private static final String	SETTING_USE_UPPER_CASE	= "UseUpperCase";
	private static final String	SETTING_USE_NUMBERS		= "UseNumbers";
	private static final String	SETTING_USE_CHARS		= "UseChars";
	private static final String	SETTING_PASSWORD_LEGNTH	= "PasswordLength";
	
	private EditText			etLegnth;
	private ToggleButton		tbLetters;
	private ToggleButton		tbNumbers;
	private ToggleButton		tbUpperCase;
	private ToggleButton		tbSpecialChar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_generator);
		
		etLegnth = (EditText) findViewById(R.pass_gen.etLegnth);
		tbLetters = (ToggleButton) findViewById(R.pass_gen.tbLetters);
		tbNumbers = (ToggleButton) findViewById(R.pass_gen.tbNumbers);
		tbUpperCase = (ToggleButton) findViewById(R.pass_gen.tbUperCase);
		tbSpecialChar = (ToggleButton) findViewById(R.pass_gen.tbSpecialChars);
		
		final Button btnGenerate = (Button) findViewById(R.pass_gen.btnGenerate);
		final EditText etGeneratedPassword = (EditText) findViewById(R.pass_gen.etGeneratedPassword);
		final Button btnCopy = (Button) findViewById(R.pass_gen.btnCopy);
		loadPreferances();
		
		OnCheckedChangeListener checkListener = new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				SharedPreferences preferences = getPreferences(MODE_PRIVATE);
				Editor editor = preferences.edit();
				int checkedButtonId = buttonView.getId();
				switch( checkedButtonId )
				{
					case R.pass_gen.tbLetters :
						editor.putBoolean(SETTING_USE_LETTERS, isChecked);
						break;
					case R.pass_gen.tbNumbers :
						editor.putBoolean(SETTING_USE_NUMBERS, isChecked);
						break;
					case R.pass_gen.tbUperCase :
						editor.putBoolean(SETTING_USE_UPPER_CASE, isChecked);
						break;
					case R.pass_gen.tbSpecialChars :
						editor.putBoolean(SETTING_USE_CHARS, isChecked);
						break;
				}
				editor.commit();
			}
			
		};
		etLegnth.setOnEditorActionListener(new OnEditorActionListener()
		{
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				SharedPreferences preferances = getPreferences(MODE_PRIVATE);
				Editor editor = preferances.edit();
				editor.putInt(SETTING_PASSWORD_LEGNTH, Integer.valueOf(v.getText().toString()));
				editor.commit();
				return false;
			}
		});
		
		tbLetters.setOnCheckedChangeListener(checkListener);
		tbNumbers.setOnCheckedChangeListener(checkListener);
		tbUpperCase.setOnCheckedChangeListener(checkListener);
		tbSpecialChar.setOnCheckedChangeListener(checkListener);
		
		btnCopy.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				String stringToCopy = etGeneratedPassword.getText().toString();
				if ( stringToCopy.length() != 0 )
				{
					ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
					clipboard.setText(stringToCopy);
					Toast.makeText(PasswordGeneratorActivity.this, "'" + stringToCopy + "' was copied to the clipboard.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(PasswordGeneratorActivity.this, "There is nothing to copy.Please generate a password first.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		btnGenerate.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				int legnth = Integer.parseInt(etLegnth.getText().toString().trim());
				boolean useLetters = tbLetters.isChecked();
				boolean useNumbers = tbNumbers.isChecked();
				boolean useUpperCase = tbUpperCase.isChecked();
				boolean useSpecialCharacters = tbSpecialChar.isChecked();
				
				if ( legnth <= 0 )
				{
					Toast.makeText(PasswordGeneratorActivity.this, "Legnth must be more then 0.", Toast.LENGTH_SHORT).show();
				}
				else if ( !(useLetters || useNumbers || useUpperCase || useSpecialCharacters) )
				{
					Toast.makeText(PasswordGeneratorActivity.this, "At least one of the toggle buttons must be checked.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String generatedPasswrod = PasswordGenerator.getGeneratedPassword(legnth, useLetters, useNumbers, useUpperCase, useSpecialCharacters);
					Log.d(TAG, "Generated password  " + generatedPasswrod);
					etGeneratedPassword.setText(generatedPasswrod);
					
				}
			}
		});
		
	}
	
	
	/**
	 * 
	 */
	private void loadPreferances()
	{
		
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);//PreferenceManager.getDefaultSharedPreferences(this);
		tbLetters.setChecked(preferences.getBoolean(SETTING_USE_LETTERS, true));
		tbNumbers.setChecked(preferences.getBoolean(SETTING_USE_NUMBERS, true));
		tbUpperCase.setChecked(preferences.getBoolean(SETTING_USE_UPPER_CASE, false));
		tbSpecialChar.setChecked(preferences.getBoolean(SETTING_USE_CHARS, false));
		etLegnth.setText(String.valueOf(preferences.getInt(SETTING_PASSWORD_LEGNTH, 8)));
	}
}
