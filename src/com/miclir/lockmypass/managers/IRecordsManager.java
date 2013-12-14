
package com.miclir.lockmypass.managers;

import java.util.List;
import android.content.Context;
import com.miclir.lockmypass.entities.Record;

/**
 * @author Michael
 * 
 */
public interface IRecordsManager
{
	
	// Login, create, Authentication
	
	/**
	 * 
	 * @param username
	 *        new user's username
	 * @param password
	 *        new user's password
	 * @return indication if the user was added
	 */
	public boolean createNewUser(Context context, String username, String password);
	
	
	/**
	 * @param username
	 *        user name for the login session
	 * @param password
	 *        password for the login session
	 * @return returns true if the user name corresponds with the password
	 */
	public boolean AuthenticateUser(Context context, String username, String password);
	
	
	public String getLoggedInUser();
	
	
	/**
	 * 
	 */
	public void logoutUser();
	
	
	// Database queries
	/**
	 * @param category
	 *        the category of the new record
	 * @return List of records from retrieved from the database
	 */
	public List < Record > getRecordsByCategory(Context context, String category);
	
	
	/**
	 * @param context
	 * @param recordToAdd
	 *        The record that should be added to the database
	 * @return Value indicating the success of the query
	 */
	public boolean addRecord(Context context, Record recordToAdd);
	
	
	/**
	 * @param id
	 *        The id the system should delete
	 * @return Value indicating the success of the query
	 */
	public boolean deleteRecordByRecordID(Context context, Record recordToDelete);
	
	
	/**
	 * @param recordToUpdate
	 *        The record that should be updated
	 * @return Value indicating the success of the query
	 */
	public boolean updateRecord(Context context, Record recordToUpdate);
	
}
