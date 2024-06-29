import java.util.*; //for RNG

public class Reservation {
    private final String reservationID;
    private String guestName;
    private int checkInDay;
    private int checkOutDay;
    private Room roomBooked;
    private double totalPrice;

    public Reservation(String guestName, int checkInDay, int checkOutDay, Room roomBooked) {
        this.reservationID = generateReservationID().trim();
        this.guestName = guestName.trim();
        this.checkInDay = checkInDay;
        this.checkOutDay = checkOutDay;
        this.roomBooked = roomBooked;
        this.totalPrice = this.calculateTotalPrice();
    }

    private String generateReservationID() {
        Random rand = new Random();
        int number = rand.nextInt(100000);
        return String.format("%06d", number);
    }

    public String getReservationID() {
        return this.reservationID;
    }

    public String getGuestName() {
        return this.guestName;
    }

    public int getCheckInDay() {
        return this.checkInDay;
    }

    public int getCheckOutDay() {
        return this.checkOutDay;
    }

    public Room getRoomBooked() {
        return this.roomBooked;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public double calculateTotalPrice() {
        int dayDiff = checkOutDay - checkInDay;
        return dayDiff * roomBooked.getRoomPrice();
    }
}
