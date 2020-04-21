import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

/**
 * 
 * @author Kevin
 * Class for a Room which allows user to create rooms and stuff.
 * Contains all the room dates for the room as well as name and size
 */
public class Room implements Comparable <Room> {
	private String RoomName;
	private String RoomSize;
	private LinkedList<RoomDates> roomDates;
	private int DeclarationNumber;
	
	/**
	 * Constructor for Room
	 * @param name Room name 
	 * @param size size of room
	 * @param declarationnumber this is for sorting purposes for printing in the correct declared order
	 */
	public Room (String name, String size, int declarationnumber){
		this.RoomName = name;
		this.RoomSize = size;
		this.roomDates = new LinkedList<RoomDates>();
		this.DeclarationNumber = declarationnumber;
	}
	
	/**
	 * Checks if the room is available. This is called by venue
	 * @param startdate startdate of booking
	 * @param enddate enddate of booking
	 * @return boolean true or false based on if it's available
	 */
	public boolean IsRoomAvailable(Date startdate, Date enddate){
		Date datetemp = null;
        Calendar cal = Calendar.getInstance();
        
        //Works by checking the each roomdate node in the linked list of room dates of the room and checking each start date and incrementing it
        //by one each time until the days field in RoomDates
        for(RoomDates rd: this.roomDates){
        	//System.out.println(rd.getRoomDatesStartDate());
        	for(int i=0;i<rd.getRoomDatesDays();i++){
        		datetemp = rd.getRoomDatesStartDate();
        		cal.setTime(datetemp);
        		cal.add(Calendar.DAY_OF_MONTH, i);
        		datetemp = cal.getTime();
        		//System.out.println(rd.getRoomDatesDays());
        		if((datetemp.after(startdate) && datetemp.before(enddate)) || datetemp.equals(startdate) || datetemp.equals(enddate)){
        			return false;
        		}
        	}
        }
		return true;
	}
	
	/**
	 * set room dates for id days and date and sorts it
	 * @param id bookingid 
	 * @param days number of days of booking
	 * @param startdate startdate of booking
	 */
	public void SetRoomDates(int id, int days, Date startdate){
		RoomDates newroomdate = new RoomDates(id, days, startdate);
		this.roomDates.add(newroomdate);
		Collections.sort(this.roomDates);
	}
	
	
	/**
	 * gets room name
	 * @return String room name
	 */
	public String getRoomName(){
		return this.RoomName;
	}
	
	
	/**
	 * gets Room size
	 * @return String room size
	 */
	public String getRoomSize(){
		return this.RoomSize;
	}
	
	
	/**
	 * gets room dates as a linked list
	 * @return LinkedList<RoomDates>
	 */
	public LinkedList<RoomDates> getRoomDates(){
		return this.roomDates;
	}
	
	
	/**
	 * get declarationnumber. higher the declaration number the later it was declared
	 * @return  int declaration number
	 */
	public int getDeclarationNumber(){
		return this.DeclarationNumber;
	}
	
	
	/**
	 * cancels room dates for a specific object Room
	 * @param id booking id
	 */
	public void CancelRoomDates(int id) {
		for(RoomDates rd: this.roomDates){
			//System.out.println(rd.getRoomDatesID());
			if(rd.getRoomDatesID() == id){
				this.roomDates.remove(rd);
				return;
			}
		}
	}
	
	/**
	 * Used for declarationnumber to sort out the printed reservation in declaration order
	 * @param r takes in a room
	 * @return int to compare declaration numbers
	 */
	@Override
	public int compareTo(Room r) {
		if(this.DeclarationNumber < r.getDeclarationNumber()){
			return -1;
		} else {
			return 1;
		}
	}

}
