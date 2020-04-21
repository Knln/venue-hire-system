import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;

/**
 * Simple Venue Hire System
 * featuring Cancellation, Reservation, Changing reservation.
 * @author Kevin
 *	
 */
public class VenueHireSystem {
	
	//Some C/C++ type of #defines right here.
	private LinkedList<Venue> allVenues;
	private LinkedList<Requests> request;
	private static final int argument1 = 1;
	private static final int argument2 = 2;
	private static final int argument3 = 3;
	private static final int argument4 = 4;
	private static final int argument5 = 5;
	private static final int argument6 = 6;
	private static int declarationnumber = 0;
	
	/**
	 * Main function where everything is done
	 * @param args Args are taking in from std in.
	 */
	public static void main (String[] args) {
		
		//Reads input from file and makes all the initial things happen
		VenueHireSystem VenueSystem = new VenueHireSystem();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
		Scanner input;
		File fileRead = new File(args[0]);
		try {
			input = new Scanner(fileRead);
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}
		input.useDelimiter("\n");
		while (input.hasNext()) {
			
			//Make venue!
			if(input.hasNext("[Vv]enue.*")){
				String[] words = input.nextLine().split(" ");
				VenueSystem.MakeVenue(VenueSystem, words);
				
				//Usage: Request RequestID Month Day Month Day No1 Size No2 Size No3 Size
			} else if(input.hasNext("[Rr]equest.*")){
				String requestinput = input.nextLine(); 
				String[] words = requestinput.split(" ");
				try {
					String string, trimmedstring;
					string = VenueSystem.ReserveRequest(VenueSystem, words, sdf);
					trimmedstring = "Reservation " + words[argument1] + " " + string;
					trimmedstring = trimmedstring.trim();
					System.out.println(trimmedstring);
					Requests RequestID = new Requests(Integer.parseInt(words[argument1]), requestinput); 
					VenueSystem.request.add(RequestID);
				} catch (Exception e) {
					System.out.println("Request rejected");
				}
				
				//Usage: Print Venue
			} else if(input.hasNext("[Pp]rint.*")){
				String[] words = input.nextLine().split(" ");
				Venue venueToPrint = null;
				for(Venue v: VenueSystem.allVenues){
					if(v.getVenueName().equals(words[argument1])){
						venueToPrint = v;
						break;
					}
				}
				venueToPrint.PrintRoom(sdf);
				
				//Usage: Cancel BookingID
			} else if(input.hasNext("[Cc]ancel.*")){
				String[] words = input.nextLine().split(" ");
				try {
					VenueSystem.CancelRequest(VenueSystem, words[argument1]);
					System.out.println("Cancel " + Integer.parseInt(words[argument1]));
				} catch (Exception e) {
					System.out.println("Cancel rejected");
				}
				
				//Usage: Change BookingID Date DateNo Date DateNo No1 Size No2 Size No3 Size
			} else if(input.hasNext("[Cc]hange.*")){
				String changeinput = input.nextLine();
				String[] words = changeinput.split(" ");
				String previousinput;
				
				//This block logic first attempts to cancel the request. If successful attempts to make a new request, if successful then
				//prints it out. Otherwise it goes to reserve the previous request as stored in Requests list.
				try {
					previousinput = VenueSystem.CancelRequest(VenueSystem, words[argument1]);
					try {
						String trimmedstring;
						String newstring = VenueSystem.ReserveRequest(VenueSystem, words, sdf);
						changeinput = changeinput.replaceAll("Change", "Request");
						Requests RequestID = new Requests(Integer.parseInt(words[argument1]), changeinput); 
						VenueSystem.request.add(RequestID);
						trimmedstring = "Change " + words[argument1] + " " + newstring;
						trimmedstring = trimmedstring.trim();
						System.out.println(trimmedstring);
					} catch (Exception e) {
						try {
							words = previousinput.split(" ");
							VenueSystem.ReserveRequest(VenueSystem, words, sdf);
							Requests RequestID = new Requests(Integer.parseInt(words[argument1]), previousinput); 
							VenueSystem.request.add(RequestID);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						System.out.println("Change rejected");
					}
				} catch (Exception e2) {
					System.out.println("Change rejected");
				}
				
			} else {
				input.next();
			}
		}
		input.close();
	}
	
	/**
	 * Constructor for the Venue Hire System
	 */
	public VenueHireSystem(){
		this.allVenues = new LinkedList<Venue>();
		this.request = new LinkedList<Requests>();
	}
	
	/**
	 * Checks if it already has the venue declared or not
	 * @param allVenues List of venues
	 * @param VenueName Venue name to make
	 * @return boolean true or false
	 */
	public boolean HasVenue(LinkedList<Venue> allVenues, String VenueName){
		for (Venue v: allVenues){
			if(v.getVenueName().equals(VenueName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Makes the venue with the room.
	 * @param VenueSystem The object VenueSystem
	 * @param words The input string
	 */
	public void MakeVenue (VenueHireSystem VenueSystem, String[] words){
		Venue NewVenue = null;
		
		//If doesn't have the venue already it makes a new venue otherwise it searches for the menu in the list of venues
		if(!(VenueSystem.HasVenue(VenueSystem.allVenues, words[argument1]))){
			NewVenue = new Venue(words[argument1]);
			VenueSystem.allVenues.add(NewVenue);
		} else {
			for(Venue v: VenueSystem.allVenues){
				if(v.getVenueName().equals(words[argument1])) {
					NewVenue = v;
					break;
				}
			}
		}
		NewVenue.NewRoom(words[argument2], words[argument3], declarationnumber++);
	}
	
	/**
	 * Reserves the request. Separated from the main function due to static types
	 * @param VenueSystem VenueHireSystem
	 * @param words string input from std in
	 * @param sdf sdf for MMM dd style of dates
	 * @return String for printing purposes
	 * @throws Exception If cannot be fulfilled it throws an exception
	 */
	public String ReserveRequest(VenueHireSystem VenueSystem, String[] words, SimpleDateFormat sdf) throws Exception{
		
		Date date1 = null;
		Date date2 = null;
		
		//Passes the date to Date format
		try {
			date1 = sdf.parse("2015" + " " + words[argument2] + " " + words[argument3]);
			date2 = sdf.parse("2015" + " " + words[argument4] + " " + words[argument5]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Stack<Integer> numberandsize = new Stack<Integer>();
		String string = "";
		
		//Loops through all venues to find a place to book
		for(Venue v: VenueSystem.allVenues){
			
			while(!numberandsize.isEmpty()){
				numberandsize.pop();
			}
			
			//Pushes stuff onto the stack to pass into the RequestRoom function
			for (int i=words.length-1; i>=argument6; i--){
				if (i%2 != 0){
					numberandsize.push(SizeToInt(words[i]));
				} else {
					numberandsize.push(Integer.parseInt(words[i]));
				}
			}
			
			//If request room outputs a non blank string it will break. This indicates it has booked rooms
			string = v.RequestRoom(Integer.parseInt(words[argument1]), date1, date2, numberandsize);
			if((string != "")){
				break;
			}
		}
		
		//Request is rejected after cycling through all venues
		if((string == "")){
			throw new Exception("Request rejected");
		} 
		return string;
	}
	
	/**
	 * Cancel a reservation. It's separated from main function due to static typing of variables.
	 * @param VenueSystem The object VenueHireSystem
	 * @param id booking id
	 * @return String For the change request function to work properly as this cancels the previous reservation which we need if the request isn't
	 * fulfilled
	 * @throws Exception If cancellation cannot be fulfilled due to wrong id or something
	 */
	public String CancelRequest(VenueHireSystem VenueSystem, String id) throws Exception{
		Requests exceptionr = null;
		for(Requests r: VenueSystem.request){
			if(r.getRequestsID() == Integer.parseInt(id)){
				exceptionr = r;
				break;
			}
		}
		
		//Throws exception if the request isn't found in the list of requests
		if (exceptionr == null){
			throw new Exception("Cancel rejected");
		}
		
		for(Venue v: VenueSystem.allVenues){
			v.CancelRoom(exceptionr);
		}

		String requestinput = exceptionr.getRequestsRequestInput();
		VenueSystem.request.remove(exceptionr);
		return requestinput;
	}
	
	/**
	 * Room Size to integer converter
	 * @param size Room size
	 * @return int int of the room size
	 */
	private static int SizeToInt(String size) {
		 switch (size) {
		 case "small": return 1;
		 case "medium": return 2;
		 case "large": return 3;
		 default: return -1;
		 }
	}
}
