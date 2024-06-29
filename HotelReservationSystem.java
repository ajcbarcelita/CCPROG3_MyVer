import java.util.*; //Scanner and Arraylist

public class HotelReservationSystem {
    private ArrayList<Hotel> hotelList;
    private Scanner sc = new Scanner(System.in);

    //  Constructor for HRS class
    public HotelReservationSystem() {
        this.hotelList = new ArrayList<Hotel>();
    }

    //  This displays all names of the hotels under the hotelList arraylist.
    public void displayHotels() {
        System.out.println("List of Hotels Currently Under Hotel Reservation System: ");
        if (this.hotelList.size() == 0) {
            System.out.println("No hotels found.");
        } else {
            for (Hotel hotel : this.hotelList) {
                System.out.printf("%s\n", hotel.getHotelName());
            }
        }
    }

    public boolean displayAvailableRooms(Hotel hotel, int checkInDay) {
        boolean isAvailable, hasAvailableRooms = false;
        System.out.println("Available rooms for hotel " + hotel.getHotelName() + " on day " + checkInDay + ":");
    
        for (Room room : hotel.getRoomList()) {
            isAvailable = true;
            for (Reservation reservation : room.getReservationList()) {
                if (checkInDay >= reservation.getCheckInDay() && checkInDay < reservation.getCheckOutDay()){
                    isAvailable = false;
                    break;
                }
            }
            if(isAvailable) {
                System.out.println(room.getRoomName());
                hasAvailableRooms = true;
            }
        }
        return hasAvailableRooms;
    }

    public Hotel findHotelByName(String hotelName) {
        for (Hotel hotel : this.hotelList) {
            if (hotel.getHotelName().equals(hotelName.trim())) {
                return hotel;
            }
        }
        return null;
    }

    public void displayHotelReservationList(String hotelName) {
        Hotel hotel = findHotelByName(hotelName);
        if (hotel == null) {
            System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
        } else {
            System.out.println("Reservations for hotel " + hotelName + ":\n");
            System.out.println("Reservation ID\tGuest Name\tCheck-in Day\tCheck-out Day\tRoom Name\tTotal Price");
            for (Reservation reservation : hotel.getReservationList()) {
                System.out.printf("\t%s\t%s\t%d\t%d\t%s\t%.2f\n", reservation.getReservationID(), reservation.getGuestName(), reservation.getCheckInDay(), reservation.getCheckOutDay(), reservation.getRoomBooked().getRoomName(), reservation.getTotalPrice());
            }
        }
    }

    /*  
     *  This method creates a new hotel object and adds it to the hotelList arraylist.
     *  Assigns a default price of 1299.00 to the hotel. 
     *  Return true if action is successful, returns false if hotel name conflicts with existing hotel names.
     */
    public boolean createAndAddHotel(String hotelName) {
        hotelName = hotelName.trim();
        for (Hotel hotel : this.hotelList) {
            if (hotel.getHotelName().equals(hotelName)) {
                return false;
            }
        }
        this.hotelList.add(new Hotel(hotelName));
        return true;
    }

    /*  
     *  This method creates a new hotel object and adds it to the hotelList arraylist.
     *  Assigns both desired hotelname and hotel base price @ creation. 
     *  Return true if action is successful, returns false if hotel name conflicts with existing hotel names.
     */
    public boolean createAndAddHotel(String hotelName, double basePrice) {
        hotelName = hotelName.trim();
        for (Hotel hotel : this.hotelList) {
            if (hotel.getHotelName().equals(hotelName)) {
                return false;
            }
        }
        this.hotelList.add(new Hotel(hotelName, basePrice));
        return true;
    }

    /*
     *  Removes a hotel from the hotelList arraylist.
     *  If the hotel does not exist, return false, else return true.
     */
    public int removeHotel(String hotelName) {
        Hotel hotel = findHotelByName(hotelName);
        if (hotel == null) {
            return 0;
        }
        boolean hasReservations = !hotel.getReservationList().isEmpty();
        if (hasReservations) {
            return 1;
        } else {
            return 2;
        }

    }

    private boolean confirmAction() { 
        int input = 0;

        do 
        {
            System.out.println("Are you sure you want to proceed? (1. Yes, 2. No)");
            System.out.print("Enter choice: ");
            try {
                input = Integer.parseInt(sc.nextLine());
                switch(input) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input (expected int input). Please try again.");
            }
        } while (true);
    }

    private boolean areDaysValid(int checkInDay, int checkOutDay) {
        boolean isCheckInDayValid = (checkInDay >= 1 && checkInDay <= 30);
        boolean isCheckOutDayValid = (checkOutDay >= 2 && checkOutDay <= 31);
        boolean isCheckOutDayAfterCheckInDay = (checkOutDay > checkInDay); //cannot have same day checkin and checkout
        return isCheckInDayValid && isCheckOutDayValid && isCheckOutDayAfterCheckInDay;
    }

    /*
     *  This method serves as the submenu for the createHotel functionality.
     */
    private void createHotelMenu() { //FINISHED
        String hotelName;
        double basePrice = 0.0;
        int choice = 0;
        boolean status, exit = false;

        while (exit == false) {
            System.out.println();
            System.out.println("\t == CREATE HOTEL MENU ==");
            System.out.println("1. Create hotel with default base price (1299.00).");
            System.out.println("2. Create hotel with custom base price.");
            System.out.println("3. Back to main menu.");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input (expected int input). Please try again.");
                continue;
            }

            switch(choice) {
                case 1:
                case 2:
                    System.out.println();
                    System.out.print("Enter hotel name: ");
                    hotelName = sc.nextLine();
                    if (choice == 2) {
                        System.out.print("Enter base price: ");
                        try {
                            basePrice = Double.parseDouble(sc.nextLine());
                            if(basePrice < 100) {
                                System.out.println("Base price should be at least 100.0. Please try again.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input (expected double input). Please try again.");
                            continue;
                        }
                    }
                    status = (choice == 1) ? createAndAddHotel(hotelName) : createAndAddHotel(hotelName, basePrice);
                    String message = status ? "Hotel created successfully." : "Hotel name already exists. Try again.";
                    System.out.printf("%s\n", message);
                break;

                case 3:
                    System.out.println("Returning to main menu...");
                    exit = true;
                break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                break;
            }
        }
    }

    public boolean removeReservation(String hotelName, String reservationID) {
        for (Hotel hotel : this.hotelList) {
            for (Reservation reservation : hotel.getReservationList()) {
                if (reservation.getReservationID().equals(reservationID)) {
                    hotel.getReservationList().remove(reservation);
                    
                    return true;
                }
            }
        }
        return false;
    }

    private void countAvailableandBookedRooms(Hotel hotel, int day) {
        int availableRooms = 0;
        int bookedRooms = 0;
        boolean isAvailable;

        for (Room room : hotel.getRoomList()) {
            isAvailable = true;
            for (Reservation reservation : room.getReservationList()) {
                if (day >= reservation.getCheckInDay() && day < reservation.getCheckOutDay()) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableRooms++;
            } else {
                bookedRooms++;
            }
        }
        System.out.printf("Available rooms on day %d: %d\n", day, availableRooms);
        System.out.printf("Booked rooms on day %d: %d\n", day, bookedRooms);
    }

    private void viewRoomInfo(Hotel hotel) {
        int day;
        String roomName;
        boolean isAvailable;
        Room room;
        
        hotel.displayAllRooms();
        System.out.println("Enter the name of the room you want to view: ");
        roomName = sc.nextLine();
        room = hotel.findRoomByName(roomName);
        if (room == null) {
            System.out.printf("Room %s does not exist in hotel %s.\n", roomName, hotel.getHotelName());
        } else {
            System.out.println("Room Name: " + room.getRoomName());
            System.out.println("Room Rate (Per Night): " + room.getRoomPrice());
            System.out.println("Hotel Name: " + room.getHotel().getHotelName());
            System.out.print("Available on Days: ");

            for (day = 1; day <= 31 ; day++) {
                isAvailable = true;
                for (Reservation reservation : room.getReservationList()) {
                    if (day >= reservation.getCheckInDay() && day < reservation.getCheckOutDay()) {
                        isAvailable = false;
                        break;
                    }
                }
                if (isAvailable) {
                    System.out.print(day + " ,");
                }
            }
        }
    }

    private void viewReservationInfo (Hotel hotel) {
        String reservationID;
        Reservation reservation;

        hotel.displayReservationIDs();
        System.out.println("Enter the reservation ID you want to view: ");
        reservationID = sc.nextLine();
        for (Reservation res : hotel.getReservationList()) {
            if (res.getReservationID().equals(reservationID)) {
                reservation = res;
                System.out.println("Reservation ID: " + reservation.getReservationID());
                System.out.println("Guest Name: " + reservation.getGuestName());
                System.out.println("Check-in Day: " + reservation.getCheckInDay());
                System.out.println("Check-out Day: " + reservation.getCheckOutDay());
                System.out.println("Room Name: " + reservation.getRoomBooked().getRoomName());
                System.out.println("Total Price: " + reservation.getTotalPrice());
                return;
            }
        }
        System.out.printf("Reservation ID %s does not exist in hotel %s.\n", reservationID, hotel.getHotelName());
    }

    private void displayHighLevelInfo (Hotel hotel) {
        System.out.println("Hotel Name: " + hotel.getHotelName());
        System.out.println("Base Price: " + hotel.getRoomRate());
        System.out.println("Number of Rooms: " + hotel.getNumRooms());
        System.out.println("Estimated Earnings This Month: PHP " + hotel.calculateMonthlyEarnings());
    }

    private void displayLowLevelInfo(Hotel hotel) {
        int day, choice = 0;

        System.out.println("Select type of low level information to view: ");
        System.out.println("1. Total number of available and booked rooms for a selected date");
        System.out.println("2. Information about a selected room");
        System.out.println("3. Information about a selected reservation");
        System.out.print("Enter choice: ");
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input (expected int input). Please try again.");
            return;
        }

        switch(choice) {
            case 1: //display number of available and booked rooms
                System.out.print("Enter the day you want to view: ");
                try {
                    day = Integer.parseInt(sc.nextLine());
                    if (day < 1 || day > 31) {
                        System.out.println("Invalid day. Please try again.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input (expected int input). Please try again.");
                    return;
                }
                countAvailableandBookedRooms(hotel, day);
            break;

            case 2: 
                viewRoomInfo(hotel);
            break;

            case 3:
                viewReservationInfo(hotel);
            break;
        }

    }

    private void displayHotelInfo(Hotel hotel) {
        int choice;

        System.out.println("Would you like to view %s's high level or low level information?");
        System.out.println("1. High Level Information");
        System.out.println("2. Low Level Information");
        System.out.print("Enter choice: ");
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input (expected int input). Please try again.");
            return;
        }

        switch(choice) {
            case 1:
                displayHighLevelInfo(hotel);
            break;

            case 2:
                displayLowLevelInfo(hotel);
            break;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /*
     *  This method serves as the submenu for the viewHotel functionality.
     */
    private void viewHotelMenu() { //INCOMPLETE - need to add more functionality
        int choice = 0;
        boolean exit = false;
        Hotel hotel;

        while (exit == false) {
            System.out.println();
            System.out.println("\t== VIEW HOTEL MENU ==\n");
            displayHotels();

            System.out.println();
            System.out.println("1. View a specfic hotel's information.");
            System.out.println("2. Go back to main menu.");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input (expected int input). Please try again.");
                continue;
            }

            switch(choice) { //use findhotelby name to get the hotel object
                case 1:
                    System.out.println("Enter the name of the hotel you want to view: ");
                    String hotelName = sc.nextLine();
                    hotel = findHotelByName(hotelName);
                    if (hotel == null) {
                        System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
                    } else {
                        displayHotelInfo(hotel);
                    }
                break;

                case 2:
                    System.out.println();
                    System.out.println("Returning to main menu...");
                    exit = true;
                break;

                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageHotelMenu() { //TO BE TESTED
        int status, numRooms, choice = 0;
        boolean success, exit = false;
        double roomPrice;
        String hotelName, newHotelName, roomName, reservationID;
        Hotel hotel;
        Room roomToRemove;

        while (exit == false) {
            System.out.println();
            System.out.println("\t== MANAGE HOTEL MENU ==\n");
            displayHotels();
            System.out.println();
            System.out.println("1. Change a hotel's name."); //done
            System.out.println("2. Add a room to a hotel."); //done
            System.out.println("3. Add rooms to a hotel."); //done - have not checked yet
            System.out.println("4. Remove a room from a hotel."); //done - cannot check yet since reservation is not done
            System.out.println("5. Update base price of a hotel's rooms");
            System.out.println("6. Remove a reservation from a hotel.");
            System.out.println("7. Remove a hotel."); //done
            System.out.println("8. Go back to main menu.");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input (expected int input). Please try again.");
                continue;
            }

            switch(choice) {
                case 1: //change the name of an existing hotel
                    System.out.println();
                    System.out.println("Enter the name of the hotel you want to change: ");
                    hotelName = sc.nextLine();
                    System.out.println("Enter the new name of the hotel: ");
                    newHotelName = sc.nextLine();
                    hotel = findHotelByName(hotelName);
                    if (hotel == null) {
                        System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
                    } else if (findHotelByName(newHotelName) != null) {
                        System.out.printf("The name %s already exists in the list; it cannot be used.\n", newHotelName);
                    } else if (!confirmAction()) {
                        System.out.println("Action cancelled.");
                    } else {
                        hotel.setHotelName(newHotelName);
                        System.out.printf("Hotel %s has been successfully renamed to %s.\n", hotelName, newHotelName);
                    }
                break;

                case 2: //add 1 room to a specific hotel
                    System.out.println();
                    System.out.println("Enter the name of the hotel you want to add a room to: ");
                    hotelName = sc.nextLine();
                    hotel = findHotelByName(hotelName);
                    if (hotel == null) {
                        System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
                    } else {
                        roomPrice = hotel.getRoomRate();

                        if (!confirmAction()) {
                            System.out.println("Action cancelled.");
                        } else {
                            success = hotel.createAndAddRoom(roomPrice);
                            if (success) {
                                System.out.printf("Room added to hotel %s.\n", hotelName);
                            } else {
                                System.out.printf("Room could not be added to hotel %s.\n", hotelName);
                            }
                        }
                    }
                    
                break;

                case 3: //add a specified number of rooms to a specific hotel
                    System.out.println();
                    System.out.println("Enter the name of the hotel you want to add rooms to: ");
                    hotelName = sc.nextLine();
                    hotel = findHotelByName(hotelName);
                    if (hotel == null) {
                        System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
                    } else {
                        System.out.print("Enter the number of rooms you want to add: ");
                        try {
                            numRooms = Integer.parseInt(sc.nextLine());
                            roomPrice = hotel.getRoomRate();
                            if (!confirmAction()) {
                                System.out.println("Action cancelled.");
                            } else {
                                success = hotel.createAndAddRooms(roomPrice, numRooms);
                                if (success) {
                                    System.out.printf("%d rooms added to hotel %s.\n", numRooms, hotelName);
                                } else {
                                    System.out.printf("Rooms could not be added to hotel %s.\n", hotelName);
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input (expected int input). Please try again.");
                        }
                    }
                break;

                case 4: //remove a room from a specific hotel
                    System.out.println();
                    System.out.println("Enter the name of the hotel you want to remove a room from: ");
                    hotelName = sc.nextLine();
                    hotel = findHotelByName(hotelName);
                    if (hotel == null) {
                        System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
                    } else {
                        hotel.displayAllRooms();
                        System.out.println();
                        System.out.println("Enter the name of the room you want to remove: ");
                        roomName = sc.nextLine();
                        roomToRemove = hotel.findRoomByName(roomName); // error occurs here
                        if (roomToRemove == null) {
                            System.out.printf("Room %s does not exist in hotel %s.\n", roomName, hotelName);
                        } else if (!confirmAction()) {
                            System.out.println("Action cancelled.");
                        } else {
                            success = hotel.removeRoom(roomToRemove);
                            if (success) {
                                System.out.printf("Room %s has been removed from hotel %s.\n", roomName, hotelName);
                            } else {
                                System.out.printf("Room %s could not be removed from hotel %s.\n", roomName, hotelName);
                            }
                        }
                    }
                break;

                case 5: //update a hotel's base price and therefore all the rooms under it
                    System.out.println();
                    System.out.println("Enter the name of the hotel you want to update the base price of: ");
                    hotelName = sc.nextLine();
                    hotel = findHotelByName(hotelName);
                    if (hotel == null) {
                        System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
                    } else {
                        if (!hotel.getReservationList().isEmpty()) {
                            System.out.printf("There are existing reservations for hotel %s. Cannot change base price.\n", hotelName);
                        } else {
                            System.out.print("Enter the new base price: ");
                            try {
                                roomPrice = Double.parseDouble(sc.nextLine());
                                if (roomPrice < 100) {
                                    System.out.println("Base price should be at least 100.0. Please try again.");
                                    continue;
                                }
                                if (!confirmAction()) {
                                    System.out.println("Action cancelled.");
                                } else {
                                    hotel.setRoomRate(roomPrice);
                                    System.out.printf("Base price of hotel %s has been updated to %.2f.\n", hotelName, roomPrice);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input (expected double input). Please try again.");
                            }
                        }
                    }
                    
                break;

                case 6: //remove a reservation from a specific hotel
                    System.out.println();
                    System.out.println("Enter the name of the hotel you want to remove a reservation from: ");
                    hotelName = sc.nextLine();
                    displayHotelReservationList(hotelName);
                    System.out.println();
                    System.out.println("Enter the reservation ID you want to remove: ");
                    reservationID = sc.nextLine();
                    if (!removeReservation(hotelName, reservationID)) {
                        System.out.printf("Reservation ID %s does not exist in hotel %s.\n", reservationID, hotelName);
                    } else if (!confirmAction()) {
                        System.out.println("Action cancelled.");
                    } else {
                        System.out.printf("Reservation %s has been removed from hotel %s.\n", reservationID, hotelName);
                    }
                break;

                case 7: //remove a hotel from the list
                    System.out.println();
                    System.out.println("Enter the name of the hotel you want to remove: ");
                    hotelName = sc.nextLine();
                    status = removeHotel(hotelName);

                    if (status == 0) {
                        System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
                    } else {
                        if (status == 1) {
                            System.out.printf("Hotel %s has existing reservations. Confirm action to delete hotel AND its rooms and reservations.\n", hotelName);
                            if(confirmAction()) {
                                this.hotelList.remove(findHotelByName(hotelName));
                                System.out.printf("Hotel %s, and its rooms and reservations have been removed.\n", hotelName);
                            } else {
                                System.out.println("Action cancelled.");
                            }
                        } else if (status == 2) {
                            System.out.printf("Hotel %s has no existing reservations. Confirm action to delete hotel AND its rooms.\n", hotelName);
                            if(confirmAction()) {
                                this.hotelList.remove(findHotelByName(hotelName));
                                System.out.printf("Hotel %s, and its rooms have been removed.\n", hotelName);
                            } else {
                                System.out.println("Action cancelled.");
                            }
                        }
                    }
                break;

                case 8: //return to main menu
                    System.out.println();
                    System.out.println("Returning to main menu...");
                    exit = true;
                break;

                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void makeReservation() {
        int checkInDay, checkOutDay;
        String hotelName, guestName, roomName;
        Room chosenRoom;
        Hotel hotel;

        System.out.print("\nEnter the name of the hotel you want to make a RESERVATION in:");
        hotelName = sc.nextLine();
        hotel = findHotelByName(hotelName);
        if (hotel == null) {
            System.out.printf("Hotel %s does not exist in the list.\n", hotelName);
            return;
        }

        System.out.print("Enter the name of the guest: ");
        guestName = sc.nextLine();
        System.out.print("Enter the check-in day: ");
        checkInDay = Integer.parseInt(sc.nextLine());
        System.out.print("Enter the check-out day: ");
        checkOutDay = Integer.parseInt(sc.nextLine());
        if (!areDaysValid(checkInDay, checkOutDay)) {
            System.out.println("Invalid check-in and check-out days. Please try again.");
            return;
        }

        if (!displayAvailableRooms(hotel, checkInDay)) {
            System.out.print("No available rooms for the specified check-in day.");
            return;
        }

        System.out.print("Enter the name of the room you want to reserve: ");
        roomName = sc.nextLine();

        chosenRoom = hotel.findRoomByName(roomName);
        if (chosenRoom == null) {
            System.out.printf("Room %s does not exist in hotel %s.\n", roomName, hotelName);
            return;
        }

        if(!confirmAction()) {
            System.out.println("Action cancelled.");
            return;
        }

        Reservation newReservation = new Reservation(guestName, checkInDay, checkOutDay, chosenRoom);
        chosenRoom.addReservation(newReservation);
        hotel.addReservation(newReservation);
        System.out.println("Reservation successful.");
    }

    private void simulateBookingMenu() { //FULLY IMPLEMENTED - TO BE DOUBLE CHECKED
        int choice = 0;
        boolean exit = false;

        while (exit == false) {
            System.out.println("\n\t== SIMULATE BOOKING MENU ==\n");
            displayHotels();
            System.out.println("\n1. Simulate a reservation for a hotel.");
            System.out.println("2. Go back to main menu.");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input (expected int input). Please try again.");
                continue;
            }

            if (choice == 1) {
                makeReservation();
            } else if (choice == 2) {
                System.out.println("Returning to main menu...");
                exit = true;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        HotelReservationSystem HRS = new HotelReservationSystem();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        int choice;

        while (exit == false) {
            System.out.println();
            System.out.println("\t === HOTEL RESERVATION SYSTEM ===");
            System.out.println("Main Menu:");
            System.out.println("1. Create Hotel."); 
            System.out.println("2. View Hotel."); 
            System.out.println("3. Manage Hotel."); 
            System.out.println("4. Simulate Booking."); 
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input (expected int input). Please try again.");
                continue;
            }

            switch(choice) {
                case 1:
                    HRS.createHotelMenu();
                break;
                    
                case 2:
                    HRS.viewHotelMenu();
                break;

                case 3:
                    HRS.manageHotelMenu();
                break;

                case 4:
                    HRS.simulateBookingMenu();
                break;

                case 5:
                    System.out.println("Exiting program...");
                    exit = true;
                break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}