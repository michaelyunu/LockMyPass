
package com.miclir.lockmypass.adapters;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.miclir.lockmypass.R;
import com.miclir.lockmypass.entities.Record;
import com.miclir.lockmypass.managers.IconsManager;

public class RecordAdapter extends BaseAdapter
{
	
	private static final String		TAG	= RecordAdapter.class.getSimpleName();
	private final List < Record >	records;
	private final Context			context;
	
	
	public RecordAdapter(Context context, List < Record > records)
	{
		super();
		Log.d(TAG, "CTOR initiated");
		this.context = context;
		this.records = records;
	}
	
	
	@Override
	public int getCount()
	{
		int retVal = records.size();
		Log.d(TAG, "getCount() initiated. returned " + retVal);
		return retVal;
	}
	
	
	@Override
	public Object getItem(int index)
	{
		Log.d(TAG, "getItem() initiated.");
		return records.get(index);
	}
	
	
	@Override
	public long getItemId(int index)
	{
		Log.d(TAG, "getItemId() initiated. with index = " + index + "returned index = " + index);
		return index;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Log.v(TAG, "getView() called with position " + position);
		
		View row = convertView;
		if ( row == null )
		{
			final LayoutInflater viewInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = viewInflater.inflate(R.layout.record_row, null);
		}
		Record rec = (Record) getItem(position);
		
		final TextView tvAlias = (TextView) row.findViewById(R.record_row.tvAlias);
		final TextView tvUsername = (TextView) row.findViewById(R.record_row.tvUsername);
		final TextView tvPassword = (TextView) row.findViewById(R.record_row.tvPassword);
		final ImageView ivIcon = (ImageView) row.findViewById(R.record_row.ivRecordIcon);
		
		tvAlias.setText(rec.getAlias());
		tvUsername.setText(rec.getUsername());
		tvPassword.setText(rec.getPassword());
		ivIcon.setImageDrawable(context.getResources().getDrawable(IconsManager.getIcon(rec.getImageID())));
		
		return row;
	}
}
