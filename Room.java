import java.util.*;
import java.lang.*;

public class Room {
    private String roomName;
    private double roomPrice;
    private Hotel hotel;
    private ArrayList<Reservation> reservationList;

    public Room (double roomPrice, Hotel hotel) {
        this.roomName = generateRoomName(hotel);
        this.roomPrice = roomPrice;
        this.hotel = hotel;
    }

    private String generateRoomName(Hotel hotel) {
        String firstLetter = hotel.getHotelName().substring(0, 1);
        int roomNumber = hotel.getNumRooms() + 1;
        return firstLetter.toUpperCase() + roomNumber;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public double getRoomPrice() {
        return this.roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public ArrayList<Reservation> getReservationList() {
        return this.reservationList;
    }
}
