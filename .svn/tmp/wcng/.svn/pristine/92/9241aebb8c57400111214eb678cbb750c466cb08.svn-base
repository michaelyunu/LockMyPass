
package com.miclir.lockmypass.entities;

import java.io.Serializable;
import android.util.Log;

public class Record implements Serializable
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	final static String			TAG					= "Record";
	String						idInDatabase;					/* will be implemented */
	String						alias;
	String						category;
	String						username;
	String						password;
	int							imageID;
	
	
	/**
	 * @param idInDatabase
	 *            id of the record from the database
	 * @param alias
	 *            alias of the record
	 * @param category
	 *            category of the record
	 * @param username
	 *            username of the record
	 * @param password
	 *            password of the record
	 * @param imageID
	 *            imageID as a resourceID defined in the IconManager.icons array
	 */
	public Record(String idInDatabase, String alias, String category, String username, String password, int imageID)
	{
		super();
		Log.d(TAG, "Constructor inutiated with " +
				" ID = " + idInDatabase +
				" Alias: " + alias +
				" Category: " + category +
				" Username: " + username +
				" Password: " + password +
				" imageID: " + imageID);
		
		this.idInDatabase = idInDatabase;
		this.alias = alias;
		this.category = category;
		this.username = username;
		this.password = password;
		this.imageID = imageID;
	}
	
	
	/**
	 * @return alias of the record
	 */
	public String getAlias()
	{
		Log.d(TAG, "getAlias()");
		return alias;
	}
	
	
	/**
	 * @return categotr of the record
	 */
	public String getCategory()
	{
		Log.d(TAG, "getCategory()");
		return category;
	}
	
	
	public void setCategory(String category)
	{
		Log.d(TAG, "setCategory() with category=" + category);
		this.category = category;
	}
	
	
	/**
	 * @return id of the record from the database
	 */
	public String getIdInDatabase()
	{
		Log.d(TAG, "getIdInDatabas() initated");
		return idInDatabase;
	}
	
	
	/**
	 * @param alias
	 *            the alias of the record to set
	 */
	public void setAlias(String alias)
	{
		Log.d(TAG, "setAlias() with:" + alias);
		this.alias = alias;
	}
	
	
	/**
	 * @return username of the record
	 */
	public String getUsername()
	{
		Log.d(TAG, "getUsername()");
		return username;
	}
	
	
	/**
	 * @param username
	 *            the username of the record to set
	 */
	public void setUsername(String username)
	{
		Log.d(TAG, "setUsername() with:" + username);
		this.username = username;
	}
	
	
	/**
	 * @return the password of the user
	 */
	public String getPassword()
	{
		Log.d(TAG, "getPassword()");
		return password;
	}
	
	
	public void setPassword(String password)
	{
		Log.d(TAG, "setPassword() with:" + password);
		this.password = password;
	}
	
	
	public int getImageID()
	{
		Log.d(TAG, "getImageId() returned imageID=" + imageID);
		return imageID;
	}
	
	
	public void setImageID(int imageID)
	{
		Log.d(TAG, "setImageID() with:" + imageID);
		this.imageID = imageID;
	}
	
}
