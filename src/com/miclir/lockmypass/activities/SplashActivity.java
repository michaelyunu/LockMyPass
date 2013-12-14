
package com.miclir.lockmypass.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.miclir.lockmypass.R;

public class SplashActivity extends Activity
{
	
	private static final String	TAG						= SplashActivity.class.getSimpleName();
	private static int			SPLASH_TIME_IN_SECONDS	= 1;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(SPLASH_TIME_IN_SECONDS * 1000);
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				catch (InterruptedException exception)
				{
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
			}
		}).start();
	}
	
	
	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		finish(); // terminate the activity if the user hits the back button.
	}
}
