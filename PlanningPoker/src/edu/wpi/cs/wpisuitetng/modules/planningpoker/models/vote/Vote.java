package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote;

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
	
	
	
}
