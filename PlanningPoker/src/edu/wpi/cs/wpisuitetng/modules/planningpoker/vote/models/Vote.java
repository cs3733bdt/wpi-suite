/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models;
 
/**
 * used to record user votes for a game
 *
 * @author Bobby Drop Tables
 */
public class Vote {

	private final String username;
	private int voteNumber;
	
	/**
	 * Constructor for a vote 
	 * @param username The username of the voter
	 * @param voteNumber The value of the vote
	 */
	public Vote(String username, int voteNumber){
		this.username = username;
		this.voteNumber = voteNumber;
	}

	/**
	 * getter method for username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * getter method for voteNumber
	 * @return voteNumber
	 */
	public int getVoteNumber() {
		return voteNumber;
	}
	
	/**
	 * set the number of votes to a given value
	 * @param newVote value to set the number of votes to
	 */
	public void setVoteNumber(int newVote) {
		voteNumber = newVote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + voteNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vote other = (Vote) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (voteNumber != other.voteNumber)
			return false;
		return true;
	}
}
