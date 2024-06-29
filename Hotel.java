import java.util.*; //for arraylist

public class Hotel {
    private String hotelName;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList; 
    private double basePrice;

    //constructors
    public Hotel(String hotelName) { //using default base price
        this.hotelName = hotelName.trim();
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();
        this.basePrice = 1299.00;
    }

    public Hotel(String hotelName, double basePrice) {
        this.hotelName = hotelName.trim();
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();
        this.basePrice = basePrice;
    }
    
    //getters and setters
    public String getHotelName() {
        return this.hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public ArrayList<Room> getRoomList() {
        return this.roomList;
    }

    public ArrayList<Reservation> getReservationList() {
        return this.reservationList;
    }

    public double getRoomRate() {
        return this.basePrice;
    }

    public void setRoomRate(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getNumRooms() {
        return this.roomList.size();
    }

    //room methods
    public boolean createAndAddRoom(double roomPrice) {
        if (this.roomList.size() >= 50) {
            System.out.printf("The hotel %s has already reached the maximum number of rooms allowed.\n", this.hotelName);
            return false;
        }

        Room hotelRoom = new Room(roomPrice, this);
        this.roomList.add(hotelRoom);
        return true;
    }

    public boolean createAndAddRooms(double roomPrice, int numRooms) {
        if (this.roomList.size() + numRooms > 50) {
            System.out.printf("The current number of rooms in this hotel %s is %d.\n", this.hotelName, this.roomList.size());
            System.out.printf("Adding %d rooms would exceed the maximum number of rooms allowed in a hotel.\n", numRooms);
            return false;
        }

        for (int i = 0; i < numRooms; i++) {
            Room hotelRoom = new Room(roomPrice, this);
            this.roomList.add(hotelRoom);
        }
        return true;
    }

    public boolean removeRoom(Room hotelRoom) {
        // Check if the room exists in the hotel
        if (this.roomList.contains(hotelRoom)) {
            // Check if the reservation list is not null and not empty
            if (hotelRoom.getReservationList() != null && !hotelRoom.getReservationList().isEmpty()) {
                System.out.printf("The room %s has existing reservations and cannot be removed.\n", hotelRoom.getRoomName());
                return false;
            } else {
                // Proceed to remove the room as it has no reservations or the list is null
                this.roomList.remove(hotelRoom);
                return true;
            }
        } else {
            // Room does not exist in the hotel
            System.out.printf("The room %s does not exist in this hotel %s.\n", hotelRoom.getRoomName(), this.hotelName);
            System.out.println("Please check the room name and try again.");
            return false;
        }
    }

    public Room findRoomByName(String roomName) {
        for (Room hotelRoom: this.getRoomList()) {
            if (hotelRoom.getRoomName().equalsIgnoreCase(roomName.trim())) {
                return hotelRoom;
            }
        }
        return null;
    }

    //change hotel attribute methods
    public boolean updateRoomRate(double newRate) {
        if (this.reservationList.isEmpty()) {
            if (newRate >= 100.0) {
                for (Room hotelRoom: this.roomList) {
                    hotelRoom.setRoomPrice(newRate);
                }
                System.out.printf("The room rate for all rooms in hotel %s has been updated to %.2f.\n", this.hotelName, newRate);
                return true;
            } else {
                System.out.println("The new rate must be at least above / equal to 100.00. Try again.");
                return false;
            }
        } else {
            System.out.println("There are existing reservations in this hotel. The room rate cannot be updated.");
            return false;
        }
    }

    //display methods
    public void displayAllRooms() {
        ArrayList<Room> roomList = getRoomList();

        if (roomList.isEmpty()) {
            System.out.printf("There are no rooms in hotel %s.\n", this.hotelName);
        } else {
            System.out.printf("Hotel %s has the following rooms:\n", this.hotelName);
            for (Room hotelRoom: roomList) {
                System.out.printf("%s: PHP%.2f\n", hotelRoom.getRoomName(), hotelRoom.getRoomPrice());
            }
        }
    }

    //reservation methods
    public void addReservation(Reservation newReservation) {
        this.reservationList.add(newReservation);
    }

    public double calculateMonthlyEarnings() {
        double monthlyEarnings = 0.0;
        for (Reservation hotelReservation: this.reservationList) {
            monthlyEarnings += hotelReservation.getTotalPrice();
        }
        return monthlyEarnings;
    }

    public void displayReservationIDs() {
        if (this.reservationList.isEmpty()) {
            System.out.printf("There are no reservations in hotel %s.\n", this.hotelName);
        } else {
            System.out.printf("Hotel %s has the following reservations:\n", this.hotelName);
            for (Reservation hotelReservation: this.reservationList) {
                System.out.printf("Reservation ID: %s\t Room Booked: %s\n", hotelReservation.getReservationID(), hotelReservation.getRoomBooked().getRoomName());
            }
        }
    }
}