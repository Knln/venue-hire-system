import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

/**
* Class to store Room dates that are booked for a particular room
* Contains start date, days and the booking id
*/

public class RoomDates implements Comparable<RoomDates> {
	private int ID;
	private int Days;
	private Date StartDate;
	
	/**
	 * Constructor for RoomDates
	 * @param id BookingID number
	 * @param days number of days of booking
	 * @param startdate date of start of booking
	 */
	public RoomDates(int id, int days, Date startdate){
		this.ID = id;
		this.Days = days;
		this.StartDate = startdate;
	}
	
	/**
	 * get room dates for days
	 * @return days of booking
	 */
	public int getRoomDatesDays(){
		return this.Days;
	}
	
	/**
	 * get room dates id
	 * @return id of booking
	 */
	public int getRoomDatesID(){
		return this.ID;
	}
	
	/**
	 * get room dates
	 * @return start date of booking
	 */
	public Date getRoomDatesStartDate(){
		return this.StartDate;
	}
	
	/**
	 * set room dates for days
	 * @param days sets the days
	 */
	public void setRoomDatesDays(int days){
		this.Days = days;
	}
	
	/**
	 * set id for room dates
	 * @return id sets id of booking
	 */
	public void setRoomDatesID(int id){
		this.ID = id;
	}
	
	/**
	 * set room dates
	 * @return startdate set start date of booking
	 */
	public void setRoomDatesStartDate(Date startdate){
		this.StartDate = startdate;
	}

	/**
	* private method for comparing dates for sorting
	* @param rd date to compare to
	* @return a number for comparison
	*/
	@Override
	public int compareTo(RoomDates rd) {
		if(this.StartDate.before(rd.StartDate)){
			return -1;
		} else if (this.StartDate.equals(rd.StartDate)){
			return 0;
		} else {
			return 1;
		}
	}
	
}
