
package com.miclir.lockmypass.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.miclir.lockmypass.R;
import com.miclir.lockmypass.managers.RecordsManager;

/**
 * @author Michael & Liron
 *         activity asking the user for his username and password and commits a login prrocedure
 */
public class LoginActivity extends Activity
{
	
	private static final String		TAG				= LoginActivity.class.getSimpleName();
	private final RecordsManager	recordManager	= RecordsManager.getInstance();
	EditText						etUsername;
	EditText						etPassword;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "OnCreate() initiated.");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		etUsername = (EditText) findViewById(R.login.etUsername);
		etPassword = (EditText) findViewById(R.login.etPassword);
		
		final Button btnLogin = (Button) findViewById(R.login.btnLogin);
		final Button btnCreateNewUser = (Button) findViewById(R.login.btnCreateNewUser);
		final ImageView ivTitle = (ImageView) findViewById(R.login.ivTitle);
		
		// TODO sholud remove text data below
		ivTitle.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				etUsername.setText("Michael");
				etPassword.setText("1234");
				
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Button LOGIN clicked.");
				
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if ( username != null && password != null )
				{
					if ( password.length() == 4 )
					{
						if ( recordManager.AuthenticateUser(LoginActivity.this, username, password) )
						{
							Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
							startActivity(intent);
						}
						else
						{
							Toast.makeText(LoginActivity.this, "Username and/or password are wrong.", Toast.LENGTH_LONG).show();
						}
					}
					else
					{
						Toast.makeText(LoginActivity.this, "Password should be 4 digits long.", Toast.LENGTH_LONG).show();
					}
					
				}
				
			}
		});
		btnCreateNewUser.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Create new user button clicked.");
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if ( username != null && password != null )
				{
					if ( password.length() == 4 )
					{
						if ( recordManager.createNewUser(LoginActivity.this, username, password) )
						{
							Toast.makeText(LoginActivity.this, "User " + username + " was created successfully", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
							startActivity(intent);
						}
						else
						{
							Toast.makeText(LoginActivity.this, "Username already exists.", Toast.LENGTH_LONG).show();
						}
					}
					else
					{
						Toast.makeText(LoginActivity.this, "Password should be 4 digits legnth.", Toast.LENGTH_LONG).show();
					}
				}
				
			}
		});
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart()
	{
		etUsername.setText("");
		etPassword.setText("");
		super.onRestart();
	}
}
