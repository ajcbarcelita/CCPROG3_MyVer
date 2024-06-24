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

    /*  
     *  This method creates a new hotel object and adds it to the hotelList arraylist.
     *  Assigns a default price of 1299.00 to the hotel. 
     *  Return true if action is successful, returns false if hotel name conflicts with existing hotel names.
     */
    public boolean createAndAddHotel(String hotelName) {
        for (Hotel hotel : this.hotelList) {
            if (hotel.getHotelName().equalsIgnoreCase(hotelName)) {
                return false;
            }
        }
        this.hotelList.add(new Hotel(hotelName));
        return true;
    }

    public boolean createAndAddHotel(String hotelName, double basePrice) {
        for (Hotel hotel : this.hotelList) {
            if (hotel.getHotelName().equalsIgnoreCase(hotelName)) {
                return false;
            }
        }
        this.hotelList.add(new Hotel(hotelName, basePrice));
        return true;
    }

    public boolean removeHotel(String hotelName) {
        for (Hotel hotel : this.hotelList) {
            if (hotel.getHotelName().equals(hotelName)) {
                this.hotelList.remove(hotel);
                return true;
            }
        }
        System.out.printf("The hotel %s does not exist.\n", hotelName);
        return false;
    }

    private void createHotelMenu() {
        String hotelName;
        double basePrice;
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
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch(choice) {
                case 1:
                    System.out.print("Enter hotel name: ");
                    hotelName = sc.nextLine();
                    status = createAndAddHotel(hotelName);
                    if (status == true) {
                        System.out.printf("Hotel %s has been created.\n", hotelName);
                    } else {
                        System.out.printf("Hotel %s already exists. Try again.\n", hotelName);
                    }
                break;

                case 2:
                    System.out.print("Enter hotel name: ");
                    hotelName = sc.nextLine();
                    System.out.print("Enter base price: ");
                    basePrice = sc.nextDouble();
                    status = createAndAddHotel(hotelName, basePrice);
                    if (status == true) {
                        System.out.printf("Hotel %s has been created.\n", hotelName);
                    } else {
                        System.out.printf("Hotel %s already exists. Try again.\n", hotelName);
                    }
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

    private void viewHotelMenu() {
        int choice = 0;
        boolean status, exit = false;

        while (exit == false) {
            System.out.println();
            System.out.println("\t== VIEW HOTEL MENU ==\n");
            displayHotels();

            System.out.println();
            System.out.println("1. View a hotel's current information.");
            System.out.println("2. Go back to main menu.");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch(choice) {
                case 1:

                break;

                case 2: 
                    System.out.println("Returning to main menu...");
                    exit = true;
                break;

                default:
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
            System.out.println("1. Create Hotel."); //WIP
            System.out.println("2. View Hotel.");
            System.out.println("3. Manage Hotel."); //WIP
            System.out.println("4. Simulate Booking.");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
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

                break;

                case 4:

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