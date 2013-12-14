
package com.miclir.lockmypass.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.miclir.lockmypass.R;
import com.miclir.lockmypass.managers.RecordsManager;

/**
 * @author Michael & Liron
 * 
 */
public class CategoriesActivity extends Activity
{
	
	private static String			TAG		= CategoriesActivity.class.getSimpleName();
	private static RecordsManager	manager	= RecordsManager.getInstance();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		Log.d(TAG, "onCreate() initiated.");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories);
		
		// getting the refferacnes of each of the UI widgets
		final Button btnEmail = (Button) findViewById(R.categories.btnEmail);
		final Button btnWeb = (Button) findViewById(R.categories.btnWeb);
		final Button btnBank = (Button) findViewById(R.categories.btnBank);
		final Button btnCredit = (Button) findViewById(R.categories.btnCredit);
		final Button btnWallet = (Button) findViewById(R.categories.btnWallet);
		final Button btnIDs = (Button) findViewById(R.categories.btnIDs);
		final Button btnNotes = (Button) findViewById(R.categories.btnNotes);
		final Button btnContats = (Button) findViewById(R.categories.btnContacts);
		final Button btnOther = (Button) findViewById(R.categories.btnOther);
		
		final Button btnPasswordGenerator = (Button) findViewById(R.categories.btnPasswordGenerator);
		final Button btnLogout = (Button) findViewById(R.categories.btnLogout);
		
		OnClickListener categoriedClickListener = new OnClickListener()
		{
			
			@Override
			public void onClick(View clickedButton)
			{
				Log.d(TAG, "Category button clicked");
				String selectedTag = getString(R.string.email);
				
				if ( clickedButton == btnEmail )
				{
					selectedTag = getString(R.string.email);
				}
				else if ( clickedButton == btnWeb )
				{
					selectedTag = getString(R.string.web);
				}
				else if ( clickedButton == btnBank )
				{
					selectedTag = getString(R.string.bank);
				}
				else if ( clickedButton == btnCredit )
				{
					selectedTag = getString(R.string.credit);
				}
				else if ( clickedButton == btnWallet )
				{
					selectedTag = getString(R.string.wallet);
				}
				else if ( clickedButton == btnIDs )
				{
					selectedTag = getString(R.string.ids);
				}
				else if ( clickedButton == btnNotes )
				{
					selectedTag = getString(R.string.notes);
				}
				else if ( clickedButton == btnContats )
				{
					selectedTag = getString(R.string.contacts);
				}
				else if ( clickedButton == btnOther )
				{
					selectedTag = getString(R.string.other);
				}
				
				Log.d(TAG, selectedTag + " button was clicked");
				
				/*
				 * Setting the intent with the extra data which should be passed to
				 * RecordsListActivity
				 */
				Intent intent = new Intent(CategoriesActivity.this, RecordsListActivity.class);
				intent.putExtra("ListType", selectedTag);
				startActivity(intent);
			}
		};
		
		// setting the categoty on click listener for each of the categories buttons
		btnEmail.setOnClickListener(categoriedClickListener);
		btnWeb.setOnClickListener(categoriedClickListener);
		btnBank.setOnClickListener(categoriedClickListener);
		btnCredit.setOnClickListener(categoriedClickListener);
		btnWallet.setOnClickListener(categoriedClickListener);
		btnIDs.setOnClickListener(categoriedClickListener);
		btnNotes.setOnClickListener(categoriedClickListener);
		btnContats.setOnClickListener(categoriedClickListener);
		btnOther.setOnClickListener(categoriedClickListener);
		
		btnPasswordGenerator.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Settings button was clicked");
				Intent intent = new Intent(CategoriesActivity.this, PasswordGeneratorActivity.class);
				startActivity(intent);
			}
		});
		
		btnLogout.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Logout button was clicked");
				
				showLogoutDialog();
				
			}
			
		});
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ( keyCode == KeyEvent.KEYCODE_BACK )
		{
			Log.d(TAG, "Back button was clicked");
			showLogoutDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private void showLogoutDialog()
	{
		AlertDialog.Builder logoutDialogBuilder = new Builder(CategoriesActivity.this);
		logoutDialogBuilder.setCancelable(true);
		logoutDialogBuilder.setIcon(android.R.drawable.ic_menu_help);
		logoutDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String username = manager.getLoggedInUser();
				Toast.makeText(CategoriesActivity.this, "Goodbye " + username + ", see you next time...", Toast.LENGTH_LONG).show();
				manager.logoutUser();
				finish();
			}
			
		});
		logoutDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		logoutDialogBuilder.setTitle("Logout");
		logoutDialogBuilder.setMessage("Are you sure you want to logout?");
		
		AlertDialog deleteDialog = logoutDialogBuilder.create();
		deleteDialog.show();
	}
}
