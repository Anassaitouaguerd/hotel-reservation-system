import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Service service = new Service();
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("=== Welcome to Hotel Reservation System ===");
        System.out.println("1. Create 3 rooms: Room1(Standard,1000), Room2(Junior,2000), Room3(Suite,3000)");
        System.out.println("2. Create 2 users: User1(5000), User2(10000)");
        System.out.println("3. Test various bookings");
        System.out.println("4. Update room and see the results");
        System.out.println("==============================================\n");

        showMenu();
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n--- Hotel Management Menu ---");
            System.out.println("1. Create/Update Room");
            System.out.println("2. Create/Update User");
            System.out.println("3. Book Room");
            System.out.println("4. Print All Rooms & Bookings");
            System.out.println("5. Print All Users");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        handleCreateRoom();
                        break;
                    case 2:
                        handleCreateUser();
                        break;
                    case 3:
                        handleBookRoom();
                        break;
                    case 4:
                        service.printAll();
                        break;
                    case 5:
                        service.printAllUsers();
                        break;
                    case 6:
                        System.out.println("Thank you for using Hotel Reservation System!");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1-6.");
            }
        }
    }

    private static void handleCreateRoom() {
        try {
            System.out.print("Enter room number: ");
            int roomNumber = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Available room types:");
            System.out.println("1. STANDARD");
            System.out.println("2. JUNIOR");
            System.out.println("3. SUITE");
            System.out.print("Choose room type (1-3): ");
            int typeChoice = Integer.parseInt(scanner.nextLine().trim());

            RoomType roomType;
            switch (typeChoice) {
                case 1:
                    roomType = RoomType.STANDARD;
                    break;
                case 2:
                    roomType = RoomType.JUNIOR;
                    break;
                case 3:
                    roomType = RoomType.SUITE;
                    break;
                default:
                    System.out.println("Invalid room type. Using STANDARD as default.");
                    roomType = RoomType.STANDARD;
            }

            System.out.print("Enter price per night: ");
            int pricePerNight = Integer.parseInt(scanner.nextLine().trim());

            service.setRoom(roomNumber, roomType, pricePerNight);

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println("Error creating room: " + e.getMessage());
        }
    }

    private static void handleCreateUser() {
        try {
            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter user balance: ");
            int balance = Integer.parseInt(scanner.nextLine().trim());

            service.setUser(userId, balance);

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private static void handleBookRoom() {
        try {
            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter room number: ");
            int roomNumber = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter check-in date (dd/MM/yyyy): ");
            String checkInStr = scanner.nextLine().trim();
            Date checkIn = sdf.parse(checkInStr);

            System.out.print("Enter check-out date (dd/MM/yyyy): ");
            String checkOutStr = scanner.nextLine().trim();
            Date checkOut = sdf.parse(checkOutStr);

            service.bookRoom(userId, roomNumber, checkIn, checkOut);

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println("Error booking room: " + e.getMessage());
        }
    }
}