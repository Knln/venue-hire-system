import java.util.*;

import java.text.SimpleDateFormat;
import java.io.*;

/**
 * 
 * @author Kevin
 * Venue which contains many rooms.
 * Has list of all rooms as well as a list of all requests for a particular venue and also the name
 * Contains Print all rooms of the venue, requests, cancellations and making of new rooms for a venue
 */
public class Venue {
	private String VenueName;
	private LinkedList<Requests> requestlist;
	private LinkedList<Room> VenueRoom;
	
	/**
	 * Constructor for Venue
	 * @param name Venue name to construct 
	 */
	public Venue (String name){
		this.VenueName = name;
		this.VenueRoom = new LinkedList<Room>();
		this.requestlist = new LinkedList<Requests>();
	}
	
	/**
	 * Constructor for Venue
	 * @param RoomName makes a room with room name in the venue
	 * @param RoomSize makes a room with room size
	 * @param DeclarationNumber Uses a declaration number to order the rooms in declared order
	 */
	public void NewRoom(String RoomName, String RoomSize, int DeclarationNumber){
		Room newRoom = new Room(RoomName, RoomSize, DeclarationNumber);
		this.VenueRoom.add(newRoom);
	}
	
	/**
	 * Prints the entire occupancy of each room with dates booked for the entire venue
	 * @param sdf takes in a simple date format so that is prints month and day only
	 */
	public void PrintRoom(SimpleDateFormat sdf){
		for (Room r: this.VenueRoom){
			if(!requestlist.isEmpty()){
				System.out.print(this.VenueName + " ");
				System.out.print(r.getRoomName() + "");
				for(RoomDates rd: r.getRoomDates()){
					System.out.print(sdf.format(rd.getRoomDatesStartDate()).replaceAll("2015","") + " " + rd.getRoomDatesDays() + "");
				}
				System.out.println("");
			}
		}
	}
	
	/**
	 * Request room function. This is a massive function that is also used by change room.
	 * @param id takes in booking id
	 * @param startdate takes in start date
	 * @param enddate takes in end date
	 * @param numberandsize takes in all numbers and sizes of all rooms requested
	 * @return String used for printing purposes in the Main function
	 */
	public String RequestRoom(int id, Date startdate, Date enddate, Stack<Integer> numberandsize){
		int size;
		int number;
		int days = 0;
		LinkedList<Room> room = new LinkedList<Room>();
		
		//System.out.println(this.getVenueName());
		//System.out.println(numberandsize.toString());
		
		//Pops each element off the stack until all elements are popped or all rooms are checked and cannot be filled. If all rooms are checked
		//and nothing can be filled then it returns back into main and will call the next venue
		while(!numberandsize.isEmpty()){
			number = numberandsize.pop();
			size = numberandsize.pop();
			for (Room r: this.VenueRoom){
				if(r.getRoomSize().equals(IntToSize(size))){
					if(r.IsRoomAvailable(startdate, enddate)){
						room.add(r);
						number--;
						if(number != 0){
						} else {
							break;
						}
					}
				}
			}
			if(number != 0){
				return "";
			}
	        //ListIterator<Room> testing = room.listIterator();
			//while(testing.hasNext()){
			//	System.out.println(testing.next().RoomName);
			//}
			//System.out.println(numberandsize.toString());
			//System.out.println(number);
		}
		
		//Adds to list of requests for the venue
		Requests RequestID = new Requests(id, null); 
		this.requestlist.add(RequestID);
		
		//Uses a calendar to minus the start date from the end date to get number of days of stay
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(startdate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(enddate);
		while(!beginCalendar.equals(endCalendar)){
			beginCalendar.add(Calendar.DATE, 1);
			days++;
		}
		
		//Sorts rooms by declaration and sets dates for rooms
		Collections.sort(room);
		for(Room r: room){
			r.SetRoomDates(id,days+1,startdate);
		}
		
		//Used to return a string for printing
        ListIterator<Room> listIterator = room.listIterator();
        String ReturnString = this.getVenueName() + " ";
        while(listIterator.hasNext()){
        	ReturnString += (listIterator.next().getRoomName()) + " ";
        }
        //System.out.println(ReturnString);
		return ReturnString;
	}
	
	/**
	 * Cancels the room request
	 * @param request
	 */
	public void CancelRoom(Requests request){
		for(Requests rq:this.requestlist){
			if (rq.similar(request)){
				for(Room r: this.VenueRoom){
					r.CancelRoomDates(rq.getRequestsID());
				}
				requestlist.remove(rq);
				return;
			}
		}
	}
	
	/**
	 * getter for venue name
	 * @return String venue name
	 */
	public String getVenueName(){
		return this.VenueName;
	}
	
	/**
	 * Int to Room size converter
	 * @param size takes in a size in int
	 * @return String size of the room
	 */
	private static String IntToSize(int size) {
		 switch (size) {
		 case 1: return "small";
		 case 2: return "medium";
		 case 3: return "large";
		 default: return null;
		 }
	}
	
}
