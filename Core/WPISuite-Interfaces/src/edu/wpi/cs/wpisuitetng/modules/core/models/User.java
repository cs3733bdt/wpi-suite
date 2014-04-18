/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    twack
 *    mpdelladonna
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.models;

import com.google.gson.*;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
/**
 * The Data Model representation of a User. Implements
 * 	database interaction and serializing.
 * @author mdelladonna, twack, bgaffey
 */

public class User extends AbstractModel
{
	private String name;
	private String username;
	private int idNum;
	private String email;
	private String facebookUsername;
	private Role role;
	private Carrier carrier;
	private String phoneNumber;
	
	transient private String password; // excluded from serialization, still stored.
	
	/**
	 * The primary constructor for a User
	 * @param name	User's full name
	 * @param username	User's username (nickname)
	 * @param password User's password
	 * @param email User's email address
	 * @param idNum	User's ID number
	 */
	public User(String name, String username, String password, String email, String facebookUsername, int idNum)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.idNum = idNum;
		this.email = email;
		this.facebookUsername = facebookUsername;
		this.role = Role.USER;
		this.phoneNumber = null;
		this.carrier = null;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof User)
		{
			if( ((User)other).idNum == this.idNum)
			{
				//things that can be null
				if(this.name != null && !this.name.equals(((User)other).name))
				{
					return false;
				}
				
				if(this.username != null && !this.username.equals(((User)other).username))
				{
					return false;
				}
				
				if(this.password != null && !this.password.equals(((User)other).password))
				{
					return false;
				}
				
				if(this.email != null && !this.email.equals(((User)other).email)) {
					return false;
				}
				
				if(this.facebookUsername != null && !this.facebookUsername.equals(((User)other).facebookUsername)) {
					return false;
				}
				
				if(this.phoneNumber != null && !this.phoneNumber.equals(((User)other).phoneNumber)) {
					return false;
				}
				
				if(this.carrier != null && !this.carrier.equals(((User)other).carrier)) {
					return false;
				}
				
				if(this.role != null && !this.role.equals(((User)other).role))
				{
					return false;
				}
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Performs password checking logic. Fails if password field is null, which happens
	 * 	when User is deserialized so as to protect the password.
	 * @param pass	the password String to compare
	 * @return	True if the password matches, False otherwise.
	 */
	public boolean matchPassword(String pass)
	{
		return (this.password == null) ? false : password.equals(pass);
	}
	
	/**
	 * Sets password (please encrypt before using this method)
	 * @param pass
	 */
	public void setPassword(String pass)
	{
		this.password = pass;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	/* Accessors */
	public String getName()
	{
		return name;
	}
	
	public int getIdNum()
	{
		return idNum;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getFacebookUsername(){
		return facebookUsername;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	public Carrier getCarrier(){
		return carrier;
	}
	
	/* database interaction */
	public void save()
	{
		return;
	}
	
	public void delete()
	{
		return;
	}
	
	/* Serializing */
	
	/**
	 * Serializes this User model into a JSON string.
	 * 
	 * @return	the JSON representation of this User
	 */
	public String toJSON()
	{
		String json;
		
		Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserSerializer()).create();
		
		json = gson.toJson(this, User.class);
		
		return json;	
	}
	
	/**
	 * Static method offering comma-delimited JSON
	 * 	serializing of User lists
	 * @param u	an array of Users
	 * @return	the serialized array of Users
	 */
	public static String toJSON(User[] u)
	{
		String json ="[";
		
		for(User a : u)
		{
			json += a.toJSON() + ",";
		}
		
		//remove that last comma
		json = json.substring(0, json.length()-1);
		
		json += "]";
				
		return json;
		
	}
	
	/* Built-in overrides/overloads */
	
	/**
	 * Override of toString() to return a JSON string for now.
	 * 	May override in the future.
	 */
	public String toString()
	{
		return this.toJSON();
	}

	@Override
	public Boolean identify(Object o)
	{
		Boolean b  = false;
		
		if(o instanceof User)
			if(((User) o).username.equalsIgnoreCase(this.username))
				b = true;
		
		if(o instanceof String)
			if(((String) o).equalsIgnoreCase(this.username))
				b = true;
		return b;
	}
	
	/**
	 * Determines if this is equal to another user
	 * @param anotherUser
	 * @return true if this and anotherUser are equal
	 */
	public boolean equals(User anotherUser){
		return this.name.equalsIgnoreCase(anotherUser.getName()) &&
				this.username.equalsIgnoreCase(anotherUser.getUsername()) &&
				this.idNum == anotherUser.getIdNum();
	}
	
	public User setName(String newName){
		this.name = newName;
		return this;
	}
	
	public User setUserName(String newUserName){
		this.username = newUserName;
		return this;
	}
	
	public User setIdNum(int newidNum){
		this.idNum = newidNum;
		return this;
	}
	
	public User setEmail(String newEmail) {
		this.email = newEmail;
		return this;
	}
	
	public User setFacebookUsername(String newFacebookUsername){
		this.facebookUsername = newFacebookUsername;
		return this;
	}
	
	public User setPhoneNumber(String newPhoneNumber){
		this.phoneNumber = newPhoneNumber;
		return this;
	}
	
	public User setCarrier(Carrier c){
		this.carrier = c;
		return this;
	}
	
	public Role getRole()
	{
		return this.role;
	}
	
	public void setRole(Role r)
	{
		this.role = r;
	}

	
	public static User fromJSON(String json) {
		// build the custom serializer/deserializer
		Gson gson;
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(User.class, new UserDeserializer());

		gson = builder.create();
		
		return gson.fromJson(json, User.class);
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setProject(Project aProject){
		//Users are not currently Associated with projects directly 
	}
}
