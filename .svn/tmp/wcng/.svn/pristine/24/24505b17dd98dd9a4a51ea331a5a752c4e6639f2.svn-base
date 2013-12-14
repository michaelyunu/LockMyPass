
package com.miclir.lockmypass.datalayer;

import java.util.HashMap;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class PasswordsProvider extends ContentProvider
{
	
	private static final String					LOGGER_TAG						= "LockMyPassProvider";
	private static final String					DATABASE_NAME					= "lockMyPass.db";
	private static final int					DATABASE_VERSION				= 1;
	private static HashMap < String , String >	map;
	// tables names
	private static final String					APP_USERS_TABLE_NAME			= "appUsers";
	private static final String					USERNAMES_PASSWORDS_TABLE_NAME	= "usernamesPasswords";
	// matcher constants
	private static final UriMatcher				matcher;
	private static final int					APP_USERS						= 1;
	private static final int					SPECIFIC_APP_USER				= 2;
	private static final int					USERNAMES_PASSWORDS				= 3;
	private static final int					SPECIFIC_USERNAME_PASSWORD		= 4;
	
	static
	{
		// initializing the matcher
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(Passwords.AUTHORITY, "appUsers", APP_USERS);
		matcher.addURI(Passwords.AUTHORITY, "appUsers/#", SPECIFIC_APP_USER);
		matcher.addURI(Passwords.AUTHORITY, "usernamesPasswords", USERNAMES_PASSWORDS);
		matcher.addURI(Passwords.AUTHORITY, "usernamesPasswords/#", SPECIFIC_USERNAME_PASSWORD);
		map = new HashMap < String , String >();
		map.put(BaseColumns._ID, BaseColumns._ID);
		map.put(Passwords.Records.RECORD_USERNAME, Passwords.Records.RECORD_USERNAME);
		map.put(Passwords.Records.RECORD_PASSWORD, Passwords.Records.RECORD_PASSWORD);
		map.put(Passwords.Records.ALIAS, Passwords.Records.ALIAS);
		map.put(Passwords.Records.IMAGE_ID, Passwords.Records.IMAGE_ID);
		map.put(Passwords.Records.CATEGORY, Passwords.Records.CATEGORY);
		
		map.put(Passwords.AppUsers.APP_USERNAME, Passwords.AppUsers.APP_USERNAME);
		map.put(Passwords.AppUsers.APP_PASSWORD, Passwords.AppUsers.APP_PASSWORD);
	}
	
	public static class Passwords implements BaseColumns
	{
		
		public static final String	AUTHORITY	= "com.miclir.lockmypass.datalayer.provider.lockMyPassdb";
		
		public static class ContentURIs
		{
			
			public static final Uri	APP_USERS_CONTENT_URI			= Uri.parse("content://" + AUTHORITY + "/appUsers");
			public static final Uri	USERNAMES_PASSWORDS_CONTENT_URI	= Uri.parse("content://" + AUTHORITY + "/usernamesPasswords");
		}
		
		public static class Types
		{
			
			public static final String	APP_USERS_CONTENT_TYPE				= "vnd.android.cursor.dir/vnd.miclir.appUsers";
			public static final String	APP_USERS_CONTENT_ITEM_TYPE			= "vnd.android.cursor.item/vnd.miclir.appUsers";
			public static final String	USERNAME_PASSWORD_CONTENT_TYPE		= "vnd.android.cursor.dir/vnd.miclir.usernamesPasswords";
			public static final String	USERNAME_PASSWORD_CONTENT_ITEM_TYPE	= "vnd.android.cursor.item/vnd.miclir.usernamesPasswords";
		}
		
		// Columns names and definitions
		public static class AppUsers
		{
			
			public static final String	APP_USERNAME	= "app_username";
			public static final String	APP_PASSWORD	= "app_password";
		}
		
		public static class Records
		{
			
			public static final String	RECORD_USERNAME	= "record_username";
			public static final String	RECORD_PASSWORD	= "record_password";
			public static final String	ALIAS			= "alias";
			public static final String	CATEGORY		= "category";
			public static final String	IMAGE_ID		= "imageID";
		}
		
		public static final String	DEFAULT_SORT_ORDER	= AppUsers.APP_USERNAME + " DESC";
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			// create the Applications users table
			db.execSQL("CREATE TABLE " + APP_USERS_TABLE_NAME + " ("
					+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ Passwords.AppUsers.APP_USERNAME + " VARCHAR(20) NOT NULL,"
					+ Passwords.AppUsers.APP_PASSWORD + " VARCHAR(32) NOT NULL" + ");");
			// create the usernames & passwords database table
			db.execSQL("CREATE TABLE " + USERNAMES_PASSWORDS_TABLE_NAME + " ("
					+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ Passwords.AppUsers.APP_USERNAME + " VARCHAR(20) NOT NULL,"
					+ Passwords.Records.RECORD_USERNAME + " VARCHAR(30) NOT NULL,"
					+ Passwords.Records.RECORD_PASSWORD + " VARCHAR(30) NOT NULL,"
					+ Passwords.Records.CATEGORY + " VARCHAR(30) NOT NULL,"
					+ Passwords.Records.ALIAS + " TEXT NOT NULL,"
					+ Passwords.Records.IMAGE_ID + " INTEGER" + ");");
		}
		
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(LOGGER_TAG, "Upgrading the database from version "
					+ oldVersion + " to " + newVersion
					+ ", old data is deleted");
			db.execSQL("DROP TABLE IF EXISTS " + APP_USERS_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + USERNAMES_PASSWORDS_TABLE_NAME);
			onCreate(db);
		}
	}
	
	private DatabaseHelper	dbHelper;
	
	
	@Override
	public boolean onCreate()
	{
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}
	
	
	@Override
	public Cursor query(Uri uri, String [] projection, String selection,
			String [] selectionArgs, String sortOrder)
	{
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		switch( matcher.match(uri) )
		{
			case APP_USERS :
				qb.setTables(APP_USERS_TABLE_NAME);
				qb.setProjectionMap(map);
				break;
			
			case SPECIFIC_APP_USER :
				qb.setTables(APP_USERS_TABLE_NAME);
				qb.setProjectionMap(map);
				qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
				break;
			
			case USERNAMES_PASSWORDS :
				qb.setTables(USERNAMES_PASSWORDS_TABLE_NAME);
				qb.setProjectionMap(map);
				break;
			
			case SPECIFIC_USERNAME_PASSWORD :
				qb.setTables(USERNAMES_PASSWORDS_TABLE_NAME);
				qb.setProjectionMap(map);
				qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
				break;
			
			default :
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		String orderBy;
		if ( TextUtils.isEmpty(sortOrder) )
		{
			orderBy = Passwords.DEFAULT_SORT_ORDER;
		}
		else
		{
			orderBy = sortOrder;
		}
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, projection, selection, selectionArgs,
				null, null, orderBy);
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}
	
	
	// getType Method is Done!
	@Override
	public String getType(Uri uri)
	{
		switch( matcher.match(uri) )
		{
			case APP_USERS :
				return Passwords.Types.APP_USERS_CONTENT_TYPE;
			case SPECIFIC_APP_USER :
				return Passwords.Types.APP_USERS_CONTENT_ITEM_TYPE;
			case USERNAMES_PASSWORDS :
				return Passwords.Types.USERNAME_PASSWORD_CONTENT_TYPE;
				
			case SPECIFIC_USERNAME_PASSWORD :
				return Passwords.Types.USERNAME_PASSWORD_CONTENT_ITEM_TYPE;
				
			default :
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}
	
	
	@Override
	public Uri insert(Uri uri, ContentValues initialValues)
	{
		/*
		 * if ((matcher.match(uri) != APP_USERS) && (matcher.match(uri) != USERNAMES_PASSWORDS))
		 * {
		 * throw new IllegalArgumentException("Unknown URI " + uri);
		 * }
		 */
		
		ContentValues values;
		if ( initialValues != null )
		{
			values = new ContentValues(initialValues);
		}
		else
		{
			values = new ContentValues();
		}
		
		switch( matcher.match(uri) )
		{
			case APP_USERS :
				if ( (values.containsKey(Passwords.AppUsers.APP_USERNAME) == false) &&
						(values.containsKey(Passwords.AppUsers.APP_PASSWORD) == false) )
				{
					throw new SQLException("Failed to insert row into " + uri + ".username or password are missing");
				}
				else
				{
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					long rowId = db.insert(APP_USERS_TABLE_NAME, null, values);
					if ( rowId > 0 )
					{
						Uri uriNotify = ContentUris.withAppendedId(Passwords.ContentURIs.APP_USERS_CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(uriNotify, null);
						return uriNotify;
					}
					
					throw new SQLException("Failed to insert row into " + uri);
				}
				
			case USERNAMES_PASSWORDS :
				if ( (values.containsKey(Passwords.AppUsers.APP_USERNAME) == false) ||
						(values.containsKey(Passwords.Records.RECORD_USERNAME) == false) ||
						(values.containsKey(Passwords.Records.RECORD_PASSWORD) == false) ||
						(values.containsKey(Passwords.Records.CATEGORY) == false) ||
						(values.containsKey(Passwords.Records.ALIAS) == false) )
				{
					throw new SQLException("Failed to insert row into " + uri + ".one or more data fields are missing");
				}
				else
				{
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					long rowId = db.insert(USERNAMES_PASSWORDS_TABLE_NAME, null, values);
					if ( rowId > 0 )
					{
						Uri uriNotify = ContentUris.withAppendedId(Passwords.ContentURIs.USERNAMES_PASSWORDS_CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(uriNotify, null);
						return uriNotify;
					}
					
					throw new SQLException("Failed to insert row into " + uri);
				}
			default :
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		/*
		 * Long now = Long.valueOf(System.currentTimeMillis());
		 * 
		 * if (values.containsKey(Memos.CREATION_DATE) == false)
		 * {
		 * values.put(Memos.CREATION_DATE, now);
		 * }
		 * 
		 * if (values.containsKey(Memos.MODIFIED_DATE) == false)
		 * {
		 * values.put(Memos.MODIFIED_DATE, now);
		 * }
		 * 
		 * if (values.containsKey(Memos.TITLE) == false)
		 * {
		 * values.put(Memos.TITLE, "no title");
		 * }
		 * 
		 * if (values.containsKey(Memos.MEMO) == false)
		 * {
		 * values.put(Memos.MEMO, "empty memo");
		 * }
		 * 
		 * SQLiteDatabase db = dbHelper.getWritableDatabase();
		 * long rowId = db.insert(MEMOS_TABLE_NAME, Memos.MEMO, values);
		 * if (rowId > 0)
		 * {
		 * Uri uriNotify = ContentUris.withAppendedId(Memos.CONTENT_URI,rowId);
		 * getContext().getContentResolver().notifyChange(uriNotify, null);
		 * return uriNotify;
		 * }
		 * 
		 * throw new SQLException("Failed to insert row into " + uri);
		 */
	}
	
	
	@Override
	public int delete(Uri uri, String where, String [] whereArgs)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		String id;
		switch( matcher.match(uri) )
		{
			case APP_USERS :
				count = db.delete(APP_USERS_TABLE_NAME, where, whereArgs);
				break;
			case SPECIFIC_APP_USER :
				id = uri.getPathSegments().get(1);
				count = db.delete(APP_USERS_TABLE_NAME,
						BaseColumns._ID + "=" + id
								+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
				break;
			case USERNAMES_PASSWORDS :
				count = db.delete(USERNAMES_PASSWORDS_TABLE_NAME, where, whereArgs);
				break;
			case SPECIFIC_USERNAME_PASSWORD :
				id = uri.getPathSegments().get(1);
				count = db.delete(USERNAMES_PASSWORDS_TABLE_NAME,
						BaseColumns._ID + "=" + id
								+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
				break;
			
			default :
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
	
	
	@Override
	public int update(Uri uri, ContentValues values, String where, String [] whereArgs)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		String id;
		switch( matcher.match(uri) )
		{
			case APP_USERS :
				count = db.update(APP_USERS_TABLE_NAME, values, where, whereArgs);
				break;
			case SPECIFIC_APP_USER :
				id = uri.getPathSegments().get(1);
				count = db.update(APP_USERS_TABLE_NAME, values,
						BaseColumns._ID + "=" + id
								+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
				break;
			case USERNAMES_PASSWORDS :
				count = db.update(USERNAMES_PASSWORDS_TABLE_NAME, values, where, whereArgs);
				break;
			case SPECIFIC_USERNAME_PASSWORD :
				id = uri.getPathSegments().get(1);
				count = db.update(USERNAMES_PASSWORDS_TABLE_NAME, values,
						BaseColumns._ID + "=" + id
								+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
				break;
			default :
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
