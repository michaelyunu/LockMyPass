
package com.miclir.lockmypass.managers;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;
import com.miclir.lockmypass.datalayer.PasswordsProvider;
import com.miclir.lockmypass.datalayer.PasswordsProvider.Passwords;
import com.miclir.lockmypass.datalayer.PasswordsProvider.Passwords.AppUsers;
import com.miclir.lockmypass.datalayer.PasswordsProvider.Passwords.ContentURIs;
import com.miclir.lockmypass.datalayer.PasswordsProvider.Passwords.Records;
import com.miclir.lockmypass.encryption.Encryptor;
import com.miclir.lockmypass.encryption.MD5Utility;
import com.miclir.lockmypass.entities.Record;

/**
 * @author Michael & Liron
 * 
 */
public class RecordsManager implements IRecordsManager
{
	
	/**
	 * logging tag
	 */
	private static final String		TAG			= "RecordManager";
	/**
	 * current user that signed in to the system right now
	 */
	public String					currentSignedInUser;
	private Encryptor				encryptor;
	/**
	 * insatnce of the singeltone RecordManager class
	 */
	private static RecordsManager	instance	= null;
	
	
	/**
	 * private constructor for singleton implementation
	 */
	private RecordsManager()
	{
		
	}
	
	
	/**
	 * @return instance of the RecordsManager class
	 */
	public static RecordsManager getInstance()
	{
		Log.d(TAG, "getIntance() initiated");
		if ( instance == null )
		{
			instance = new RecordsManager();
			
		}
		return instance;
		
	}
	
	
	@Override
	public boolean createNewUser(Context context, String username, String password)
	{
		boolean isSucceded;
		String stringForEncription = password.concat(password);
		try
		{
			
			String [] vec = new String[ ]
			{
					BaseColumns._ID,
					PasswordsProvider.Passwords.AppUsers.APP_USERNAME,
					PasswordsProvider.Passwords.AppUsers.APP_PASSWORD,
			};
			Cursor cursor = context.getContentResolver().query(
					PasswordsProvider.Passwords.ContentURIs.APP_USERS_CONTENT_URI,
					vec, // Select clause
					PasswordsProvider.Passwords.AppUsers.APP_USERNAME + "= '" + username + "' ",// Where
					// clause
					null,// selection arguments... in this case none
					PasswordsProvider.Passwords.DEFAULT_SORT_ORDER); // order by
			
			if ( cursor.getCount() == 0 )
			{
				Log.d(TAG, "Username " + username + " not exists in the database.");
				
				String passwordMD5 = MD5Utility.getMD5(stringForEncription);
				ContentValues values = new ContentValues();
				values.put(PasswordsProvider.Passwords.AppUsers.APP_USERNAME, username);
				values.put(PasswordsProvider.Passwords.AppUsers.APP_PASSWORD, passwordMD5);
				Log.d(TAG, "MD5 of:" + stringForEncription + ",is: " + passwordMD5);
				try
				{
					context.getContentResolver().insert(PasswordsProvider.Passwords.ContentURIs.APP_USERS_CONTENT_URI, values);
					encryptor = Encryptor.getInstance(stringForEncription);
					currentSignedInUser = username;
					isSucceded = true;
				}
				catch (Exception e)
				{
					Log.e(TAG, e.getMessage());
					isSucceded = false;
				}
				
			}
			else
			{
				isSucceded = false;
			}
			cursor.close();
		}
		catch (Exception exception)
		{
			Log.e(TAG, exception.getMessage());
			exception.printStackTrace();
			isSucceded = false;
		}
		
		return isSucceded;
	}
	
	
	/**
	 * @return Boolean value indicating if the application contains a logged in user.
	 */
	public boolean isUserLoggedIn()
	{
		Log.d(TAG, "isUserLoggedIn() currentSignedInUser = " + currentSignedInUser);
		return currentSignedInUser != null;
	}
	
	
	@Override
	public boolean AuthenticateUser(Context context, String username, String password)
	{
		Log.d(TAG, "isUserAuthenticated() with " +
				" username = " + username +
				" password = " + password);
		boolean isSucceeded;
		String stringForEncription = password.concat(password);
		Cursor cursor = null;
		String [] vec = new String[ ]
		{
				PasswordsProvider.Passwords.AppUsers.APP_USERNAME,
		};
		
		try
		{
			cursor = context.getContentResolver().query(
					PasswordsProvider.Passwords.ContentURIs.APP_USERS_CONTENT_URI,
					vec, 																// Select clause
					PasswordsProvider.Passwords.AppUsers.APP_USERNAME + "=?" +
							"  AND " +
							PasswordsProvider.Passwords.AppUsers.APP_PASSWORD + " =?",	//Where clause
					new String[ ] { username, MD5Utility.getMD5(stringForEncription) },			// selection arguments
					null);
			if ( cursor.getCount() == 1 )
			{
				try
				{
					encryptor = Encryptor.getInstance(stringForEncription);
					currentSignedInUser = username;
					isSucceeded = true;
				}
				catch (Exception exception)
				{
					isSucceeded = false;
					Log.e(TAG, "Can't get instace of the encryptor.");
					exception.printStackTrace();
				}
				
			}
			else
			{
				isSucceeded = false;
			}
		}
		catch (Exception exception)
		{
			Log.e(TAG, exception.getMessage());
			exception.printStackTrace();
			isSucceeded = false;
		}
		
		finally
		{
			cursor.close();
		}
		if ( isSucceeded == false )
		{
			Log.e(TAG, "Can't authonticate user");
		}
		else
		{
			Log.e(TAG, "User authonticated successfully");
		}
		return isSucceeded;
	}
	
	
	@Override
	public void logoutUser()
	{
		Log.d(TAG, "logoutUser() initiated. currentSignedInUser  = " + currentSignedInUser);
		currentSignedInUser = null;
	}
	
	
	@Override
	public List < Record > getRecordsByCategory(Context context, String category)
	{
		Log.d(TAG, "getRecordsByCategory() with " +
				" Category = " + category +
				" Username = " + currentSignedInUser);
		
		List < Record > records = new ArrayList < Record >();
		String [] vec = new String[ ]
		{
				BaseColumns._ID,
				PasswordsProvider.Passwords.Records.RECORD_USERNAME,
				PasswordsProvider.Passwords.Records.RECORD_PASSWORD,
				PasswordsProvider.Passwords.Records.ALIAS,
				PasswordsProvider.Passwords.Records.CATEGORY,
				PasswordsProvider.Passwords.Records.IMAGE_ID,
		};
		
		Cursor cursor = context.getContentResolver().query(
				PasswordsProvider.Passwords.ContentURIs.USERNAMES_PASSWORDS_CONTENT_URI,
				vec,
				PasswordsProvider.Passwords.AppUsers.APP_USERNAME + " =?" + " AND " + PasswordsProvider.Passwords.Records.CATEGORY + " =?",
				new String[ ] { currentSignedInUser, category }, null);
		
		Log.d(TAG, "getRecordsByCategory() found " + cursor.getCount() + " records matching " +
				" category = '" + category +
				"' and user = '" + currentSignedInUser + "'");
		
		for(cursor.moveToFirst() ; !cursor.isAfterLast() ; cursor.moveToNext())
		{
			try
			{
				records.add(new Record(
						cursor.getString(cursor.getColumnIndex(BaseColumns._ID)),
						cursor.getString(cursor.getColumnIndex(PasswordsProvider.Passwords.Records.ALIAS)),
						cursor.getString(cursor.getColumnIndex(PasswordsProvider.Passwords.Records.CATEGORY)),
						encryptor.decrypt(cursor.getString(cursor.getColumnIndex(PasswordsProvider.Passwords.Records.RECORD_USERNAME))),
						encryptor.decrypt(cursor.getString(cursor.getColumnIndex(PasswordsProvider.Passwords.Records.RECORD_PASSWORD))),
						cursor.getInt(cursor.getColumnIndex(PasswordsProvider.Passwords.Records.IMAGE_ID))));
			}
			catch (Exception exception)
			{
				Log.e(TAG, "Encryption error : " + exception.getMessage());
				exception.printStackTrace();
			}
		}
		
		cursor.close();
		return records;
	}
	
	
	@Override
	public boolean addRecord(Context context, Record recordToAdd)
	{
		Log.d(TAG, "addRecord() with " +
				" recordToAdd = " + recordToAdd +
				" category = " + recordToAdd.getCategory() +
				" user = " + currentSignedInUser);
		
		boolean isSucceded;
		
		String [] vec = new String[ ]
		{
				BaseColumns._ID,
				PasswordsProvider.Passwords.Records.ALIAS,
				PasswordsProvider.Passwords.Records.RECORD_USERNAME,
				PasswordsProvider.Passwords.Records.RECORD_PASSWORD,
				PasswordsProvider.Passwords.Records.CATEGORY,
				PasswordsProvider.Passwords.Records.IMAGE_ID,
		};
		try
		{
			String encUsername = encryptor.encrypt(recordToAdd.getUsername());
			String encPassword = encryptor.encrypt(recordToAdd.getPassword());
			
			ContentValues values = new ContentValues();
			values.put(PasswordsProvider.Passwords.AppUsers.APP_USERNAME, currentSignedInUser);
			values.put(PasswordsProvider.Passwords.Records.ALIAS, recordToAdd.getAlias());
			values.put(PasswordsProvider.Passwords.Records.RECORD_USERNAME, encUsername);
			values.put(PasswordsProvider.Passwords.Records.RECORD_PASSWORD, encPassword);
			values.put(PasswordsProvider.Passwords.Records.CATEGORY, recordToAdd.getCategory());
			values.put(PasswordsProvider.Passwords.Records.IMAGE_ID, recordToAdd.getImageID());
			
			context.getContentResolver().insert(PasswordsProvider.Passwords.ContentURIs.USERNAMES_PASSWORDS_CONTENT_URI, values);
			isSucceded = true;
			Log.d(TAG, "Record added successfully.");
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error while adding record.");
			Log.e(TAG, e.getMessage());
			isSucceded = false;
		}
		return isSucceded;
	}
	
	
	@Override
	public boolean deleteRecordByRecordID(Context context, Record recordToDelete)
	{
		Log.d(TAG, "deleteRecordByRecordID() with " +
				" recordToDelete = " + recordToDelete.getIdInDatabase() +
				" user = " + currentSignedInUser);
		boolean isQuerySucceeded = true;
		
		try
		{
			int numberOfDeletedRows = context.getContentResolver().delete(
					PasswordsProvider.Passwords.ContentURIs.USERNAMES_PASSWORDS_CONTENT_URI,
					BaseColumns._ID + "=?",
					new String[ ] { recordToDelete.getIdInDatabase().toString() });
			if ( numberOfDeletedRows == 1 )
			{
				isQuerySucceeded = true;
				Log.d(TAG, "deleted 1 row");
			}
			else
			{
				isQuerySucceeded = false;
				Log.d(TAG, "Deleted more then 1 row matching id = " + recordToDelete.getIdInDatabase());
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage());
			isQuerySucceeded = false;
		}
		
		return isQuerySucceeded;
	}
	
	
	@Override
	public boolean updateRecord(Context context, Record recordToUpdate)
	{
		
		Log.d(TAG, "updateRecord() with " +
				" recordToUpdate = " + recordToUpdate);
		boolean isQuerySucceeded = true;
		try
		{
			
			String encUsername = encryptor.encrypt(recordToUpdate.getUsername());
			String encPassword = encryptor.encrypt(recordToUpdate.getPassword());
			
			ContentValues values = new ContentValues();
			values.put(PasswordsProvider.Passwords.AppUsers.APP_USERNAME, currentSignedInUser);
			values.put(PasswordsProvider.Passwords.Records.ALIAS, recordToUpdate.getAlias());
			values.put(PasswordsProvider.Passwords.Records.RECORD_USERNAME, encUsername);
			values.put(PasswordsProvider.Passwords.Records.RECORD_PASSWORD, encPassword);
			values.put(PasswordsProvider.Passwords.Records.CATEGORY, recordToUpdate.getCategory());
			values.put(PasswordsProvider.Passwords.Records.IMAGE_ID, recordToUpdate.getImageID());
			
			int numberOfUpdatedRows = context.getContentResolver().update(
					PasswordsProvider.Passwords.ContentURIs.USERNAMES_PASSWORDS_CONTENT_URI,
					values,
					BaseColumns._ID + "=?",
					new String[ ] { recordToUpdate.getIdInDatabase() });
			
			if ( numberOfUpdatedRows == 1 )
			{
				Log.d(TAG, "Updated one row for id = " + recordToUpdate.getIdInDatabase());
				isQuerySucceeded = true;
			}
			else
			{
				Log.e(TAG, "Error while updating rows. found " + numberOfUpdatedRows + " matching recordId = " + recordToUpdate.getIdInDatabase());
				isQuerySucceeded = false;
			}
			
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage());
			isQuerySucceeded = false;
		}
		return isQuerySucceeded;
	}
	
	
	@Override
	public String getLoggedInUser()
	{
		return currentSignedInUser;
	}
	
}
