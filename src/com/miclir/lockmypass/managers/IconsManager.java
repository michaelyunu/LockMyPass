
package com.miclir.lockmypass.managers;

import android.util.Log;
import com.miclir.lockmypass.R;

public class IconsManager
{
	
	private static final String	TAG		= "IconsManager";
	/**
	 * array holding all resources of the icons
     *
     *
     *
	 */
	private static Integer []	icons	= {
										
										R.drawable.lock_details_default_icon,
										R.drawable.lock_icon_android,
										R.drawable.lock_icon_apple,
										R.drawable.lock_icon_dropbox,
										R.drawable.lock_icon_ebay,
										R.drawable.lock_icon_evernote,
										R.drawable.lock_icon_facebook,
										R.drawable.lock_icon_foursquare,
										R.drawable.lock_icon_google,
										R.drawable.lock_icon_google_plus,
										R.drawable.lock_icon_meetup,
										R.drawable.lock_icon_rss,
										R.drawable.lock_icon_skype,
										R.drawable.lock_icon_soundcloud,
										R.drawable.lock_icon_steam,
										R.drawable.lock_icon_viemo,
										R.drawable.lock_icon_wordpress,
										R.drawable.lock_icon_yahoo,
										R.drawable.lock_icon_youtube,
										R.drawable.lock_icons_twitter
										};
	
	
	/**
	 * @param iconIndex
	 *            icon's indes in array
	 * @return the icons resource id
	 */
	public static int getIcon(int iconIndex)
	{
		return icons[iconIndex];
	}
	
	
	/**
	 * @return number of icons in the array
	 */
	public static int getAmount()
	{
		return icons.length;
	}
	
	
	public static int getIconIdByResourceId(int resourceID)
	{
		Log.d(TAG, "getIconIdByResourceId() initiated with" +
				" resourceID = " + resourceID);
		int i;
		boolean isFound = false;
		for(i = 0 ; i < getAmount() ; i++)
		{
			Log.i(TAG, "Checking resource " + getIcon(i));
			if ( getIcon(i) == resourceID )
			{
				Log.d(TAG, "Icon was found in index = " + i);
				isFound = true;
				break;
			}
		}
		if ( isFound == true )
		{
			return i;
		}
		else
		{
			Log.e(TAG, "Icon was NOT found");
			return -1;
		}
	}
}
