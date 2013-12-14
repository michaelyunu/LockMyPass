
package com.miclir.lockmypass.adapters;

import com.miclir.lockmypass.managers.IconsManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * @author Michael & Liron
 *         adapter for the icons
 */
public class IconsAdapter extends BaseAdapter implements ListAdapter
{
	
	private final Context	context;
	
	
	/**
	 * @param context
	 *            context of the calling activity
	 */
	public IconsAdapter(Context context)
	{
		this.context = context;
		
	}
	
	
	@Override
	public int getCount()
	{
		/*
		 * returns the number of the Icons in the stored in the IconsManager class
		 */
		return IconsManager.getAmount();
	}
	
	
	@Override
	public Object getItem(int position)
	{
		/*
		 * returns an icon by its position
		 */
		return IconsManager.getIcon(position);
	}
	
	
	@Override
	public long getItemId(int position)
	{
		
		/*
		 * returns the position of the selected icon.
		 */
		return position;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		/*
		 * sets the view of each cell in the table.
		 */
		ImageView imageView;
		if ( convertView == null )
		{
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		}
		else
		{
			imageView = (ImageView) convertView;
		}
		
		imageView.setImageResource(IconsManager.getIcon(position));
		return imageView;
	}
	
}
