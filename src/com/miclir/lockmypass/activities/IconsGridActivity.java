
package com.miclir.lockmypass.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.miclir.lockmypass.R;
import com.miclir.lockmypass.adapters.IconsAdapter;

public class IconsGridActivity extends Activity
{
	
	public static final String	TAG								= IconsGridActivity.class.getSimpleName();
	public static final String	INTENT_EXTRA_CHOSEN_ICON_INDEX	= "ChosenIconIndex";
	protected static final int	REQUEST_CODE_OK					= 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.icons_grid);
		Log.d(TAG, "OnCreate() initiated");
		final GridView grid = (GridView) findViewById(R.icons_grid.grid);
		grid.setAdapter(new IconsAdapter(this));
		grid.setOnItemClickListener(new OnItemClickListener()
		{
			
			@Override
			public void onItemClick(AdapterView < ? > arg0, View arg1, int indexOfChosenIcon, long arg3)
			{
				Log.d(TAG, "Item in index " + indexOfChosenIcon + " was chosen");
				Intent resultIntent = getIntent();
				resultIntent.putExtra(INTENT_EXTRA_CHOSEN_ICON_INDEX, indexOfChosenIcon);
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});
	}
}
