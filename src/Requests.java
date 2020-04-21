import java.util.*;

/**
 * 
 * @author Kevin
 *  Class for storing requests
 *	Used in storing request ids and request input for change purposes and ease of use for venue	
 */

public class Requests {
	private int ID;
	private String RequestInput;
	
	/**
	 * Constructor for requests
	 * @param id BookingID number
	 * @param requestinput the original input of the request
	 */
	public Requests(int no, String requestinput){
		this.ID = no;
		this.RequestInput = requestinput;
	}
	
	/**
	 * get requests booking id
	 * @return requests id
	 */
	public int getRequestsID(){
		return this.ID;
	}
	
	/**
	 * get requests input ie Reserve 33 Mar 12 Mar 19 etc will be stored
	 * @return request input 
	 */
	public String getRequestsRequestInput(){
		return this.RequestInput;
	}

	/**
	 * Matches requests id used in linked list
	 * @param takes in a request and see if the ids match
	 * @return boolean to match or not
	 */
	public boolean similar(Requests r) {
		return (this.ID == (int) r.getRequestsID());
	}

}
