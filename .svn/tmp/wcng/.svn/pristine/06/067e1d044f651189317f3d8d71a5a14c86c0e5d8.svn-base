
package com.miclir.lockmypass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingsActivity extends Activity
{
	
	private static final String	TAG	= SettingsActivity.class.getSimpleName();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		TextView tvPasswordGenerator = (TextView) findViewById(R.settings.tvPasswordGenerator);
		tvPasswordGenerator.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(SettingsActivity.this, PasswordGeneratorActivity.class);
				startActivity(intent);
			}
		});
	}
}
