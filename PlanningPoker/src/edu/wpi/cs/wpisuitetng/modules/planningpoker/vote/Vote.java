package edu.wpi.cs.wpisuitetng.modules.planningpoker.vote;

public class Vote {

	private String username;
	private int voteNumber;
	
	public Vote(String username, int voteNumber){
		this.username = username;
		this.voteNumber = voteNumber;
	}
	
	/**
	 * getter method for username
	 * @retur username
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
