
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
import com.miclir.lockmypass.entities.Record;
import com.miclir.lockmypass.managers.IconsManager;
import com.miclir.lockmypass.managers.RecordsManager;

public class RecordDetailsActivity extends Activity
{
	
	private static final String		TAG								= RecordDetailsActivity.class.getSimpleName();
	
	private static final int		REQUEST_CODE_CHANGE_ICON		= 1;
	
	public static final String		INTENT_EXTRAS_LAUNCH_TYPE		= "LaunchType";
	public static final String		INTENT_EXTRAS_SELECTED_RECORD	= "SelectedRecord";
	public static final String		INTENT_EXTRAS_SELECTED_CATEGORY	= "SelectedCategory";
	
	private final RecordsManager	manager							= RecordsManager.getInstance();
	public static final int			NEW_RECORD_ID					= -1;
	private int						selectedIconIndex;
	private int						selectedIconResourceId;
	ImageView						ivTitle;
	ImageView						ivRecordIcon;
	Button							btnChangeIcon;
	EditText						etRecordAlias;
	EditText						etRecordUsername;
	EditText						etRecordPassword;
	Button							btnAction;
	
	Record							currentRecord;
	LaunchType						launchType;
	
	enum LaunchType
	{
		AddRecord,
		EditRecord,
		ViewRecord,
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_details);
		Intent intent = getIntent();
		
		launchType = (LaunchType) intent.getExtras().get(INTENT_EXTRAS_LAUNCH_TYPE);
		currentRecord = (Record) intent.getExtras().get(INTENT_EXTRAS_SELECTED_RECORD);
		
		ivTitle = (ImageView) findViewById(R.record.ivTitle);
		ivRecordIcon = (ImageView) findViewById(R.record.ivRecordIcon);
		btnChangeIcon = (Button) findViewById(R.record.btnChangeIcon);
		etRecordAlias = (EditText) findViewById(R.record.etRecordAlias);
		etRecordUsername = (EditText) findViewById(R.record.etRecordUsername);
		etRecordPassword = (EditText) findViewById(R.record.etRecordPassword);
		btnAction = (Button) findViewById(R.record.btnAction);
		
		ivRecordIcon.setImageDrawable(getResources().getDrawable(IconsManager.getIcon(0)));
		btnAction.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				switch( launchType )
				{
					case AddRecord :
						String category = String.valueOf(getIntent().getExtras().get(INTENT_EXTRAS_SELECTED_CATEGORY));
						Record recordToAdd = new Record(
								String.valueOf(NEW_RECORD_ID),
								etRecordAlias.getText().toString(),
								category,
								etRecordUsername.getText().toString(),
								etRecordPassword.getText().toString(),
								IconsManager.getIconIdByResourceId(selectedIconResourceId)
								);
						if ( manager.addRecord(RecordDetailsActivity.this, recordToAdd) == true )
						{
							Toast.makeText(RecordDetailsActivity.this, "Record was added successfuly", Toast.LENGTH_LONG).show();
						}
						finish();
						
						break;
					case EditRecord :
						currentRecord.setAlias(etRecordAlias.getText().toString());
						currentRecord.setUsername(etRecordUsername.getText().toString());
						currentRecord.setPassword(etRecordPassword.getText().toString());
						currentRecord.setImageID(selectedIconIndex);
						manager.updateRecord(RecordDetailsActivity.this, currentRecord);
						finish();
						break;
					case ViewRecord :
						finish();
						break;
				}
				
			}
		});
		
		btnChangeIcon.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(RecordDetailsActivity.this, IconsGridActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CHANGE_ICON);
			}
		});
		setViewsAccourdingToLaunchType();
	}
	
	
	private void setViewsAccourdingToLaunchType()
	{
		Log.d(TAG, "setViewsAccourdingToLaunchType() initiated");
		switch( launchType )
		{
			case AddRecord :
				selectedIconResourceId = R.drawable.lock_details_default_icon;
				ivTitle.setImageDrawable(getResources().getDrawable(R.drawable.lock_record_add));
				ivRecordIcon.setImageDrawable(getResources().getDrawable(selectedIconResourceId));
				btnChangeIcon.setVisibility(View.VISIBLE);
				btnChangeIcon.setText("Set icon");
				etRecordAlias.setText("");
				etRecordUsername.setText("");
				etRecordPassword.setText("");
				btnAction.setText("Add record");
				break;
			
			case EditRecord :
				ivTitle.setImageDrawable(getResources().getDrawable(R.drawable.lock_record_edit));
				btnChangeIcon.setVisibility(View.VISIBLE);
				btnChangeIcon.setText("Change icon");
				
				setDataFromRecord();
				
				btnAction.setText("Done");
				break;
			
			case ViewRecord :
				ivTitle.setImageDrawable(getResources().getDrawable(R.drawable.lock_record_view));
				btnChangeIcon.setVisibility(View.GONE);
				
				setDataFromRecord();
				
				btnAction.setText("Return");
				
				break;
		
		}
	}
	
	
	private void setDataFromRecord()
	{
		ivRecordIcon.setImageDrawable(getResources().getDrawable(IconsManager.getIcon(currentRecord.getImageID())));
		etRecordAlias.setText(currentRecord.getAlias());
		etRecordUsername.setText(currentRecord.getUsername());
		etRecordPassword.setText(currentRecord.getPassword());
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.d("RecordDetailsActivity", "onActivityResult() initiated");
		if ( requestCode == REQUEST_CODE_CHANGE_ICON )
		{
			if ( resultCode == RESULT_OK )
			{
				Log.d("RecordDetailsActivity", "resultCode = OK");
				selectedIconIndex = data.getExtras().getInt(IconsGridActivity.INTENT_EXTRA_CHOSEN_ICON_INDEX);
				selectedIconResourceId = IconsManager.getIcon(selectedIconIndex);
				ivRecordIcon.setImageDrawable(getResources().getDrawable(IconsManager.getIcon(selectedIconIndex)));
				
			}
		}
	}
	
}
