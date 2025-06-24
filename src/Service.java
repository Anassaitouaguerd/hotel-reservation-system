import java.util.ArrayList;
import java.util.Date;

public class Service {
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Booking> bookings;

    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Create or update a room
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try {
            Room existingRoom = findRoomByNumber(roomNumber);
            if (existingRoom != null) {
                if (existsRoomWithTypeAndPrice(roomType, roomPricePerNight, roomNumber)) {
                    throw new IllegalArgumentException("Cannot update room: Another room already exists with type " +
                            roomType + " and price " + roomPricePerNight);
                }

                existingRoom.setRoomType(roomType);
                existingRoom.setPricePerNight(roomPricePerNight);
                System.out.println("Room " + roomNumber + " updated successfully");
            } else {
                if (existsRoomWithTypeAndPrice(roomType, roomPricePerNight, -1)) {
                    throw new IllegalArgumentException("Cannot create room: A room already exists with type " +
                            roomType + " and price " + roomPricePerNight);
                }
                Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
                rooms.add(newRoom);
                System.out.println("Room " + roomNumber + " created successfully");
            }
        } catch (Exception e) {
            System.out.println("Error setting room: " + e.getMessage());
        }
    }

    private boolean existsRoomWithTypeAndPrice(RoomType roomType, int pricePerNight, int excludeRoomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() != excludeRoomNumber &&
                    room.getRoomType() == roomType &&
                    room.getPricePerNight() == pricePerNight) {
                return true;
            }
        }
        return false;
    }

    // Create or update a user
    public void setUser(int userId, int balance) {
        try {
            User existingUser = findUserById(userId);
            if (existingUser != null) {
                existingUser.setBalance(balance);
                System.out.println("User " + userId + " updated successfully");
            } else {
                User newUser = new User(userId, balance);
                users.add(newUser);
                System.out.println("User " + userId + " created successfully");
            }
        } catch (Exception e) {
            System.out.println("Error setting user: " + e.getMessage());
        }
    }

    // Book a room
    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            // Validate dates
            if (checkIn.after(checkOut) || checkIn.equals(checkOut)) {
                System.out.println("Error: Invalid dates. Check-in must be before check-out");
                return;
            }

            // Find user and room
            User user = findUserById(userId);
            Room room = findRoomByNumber(roomNumber);

            if (user == null) {
                System.out.println("Error: User " + userId + " not found");
                return;
            }

            if (room == null) {
                System.out.println("Error: Room " + roomNumber + " not found");
                return;
            }

            // Calculate number of nights and total cost
            long diffInMillies = checkOut.getTime() - checkIn.getTime();
            int numberOfNights = (int) (diffInMillies / (1000 * 60 * 60 * 24));
            int totalCost = numberOfNights * room.getPricePerNight();

            // Check if user has enough balance
            if (user.getBalance() < totalCost) {
                System.out.println("Error: User " + userId + " has insufficient balance. " +
                        "Required: " + totalCost + ", Available: " + user.getBalance());
                return;
            }

            // Check if room is available for the period
            if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
                System.out.println("Error: Room " + roomNumber + " is not available for the requested period");
                return;
            }

            // Create booking and deduct balance
            Booking booking = new Booking(userId, roomNumber, checkIn, checkOut, room, user);
            user.deductBalance(totalCost);
            bookings.add(booking);

            System.out.println("Booking successful! Booking ID: " + booking.getBookingId() +
                    ", Total cost: " + totalCost + ", Nights: " + numberOfNights);

        } catch (Exception e) {
            System.out.println("Error booking room: " + e.getMessage());
        }
    }

    // Check if room is available for a specific period
    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                if (!(checkOut.before(booking.getCheckIn()) || checkIn.after(booking.getCheckOut()))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Find room by number
    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // Find user by ID
    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    // Print all rooms and bookings (latest to oldest)
    public void printAll() {
        System.out.println("\n=== ALL ROOMS AND BOOKINGS (Latest to Oldest) ===");
        System.out.println("\n--- ROOMS ---");
        if (rooms.isEmpty()) {
            System.out.println("No rooms found");
        } else {
            for (int i = rooms.size() - 1; i >= 0; i--) {
                System.out.println(rooms.get(i));
            }
        }

        // Print bookings (latest created to oldest)
        System.out.println("\n--- BOOKINGS ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found");
        } else {
            for (int i = bookings.size() - 1; i >= 0; i--) {
                System.out.println(bookings.get(i));
            }
        }
    }

    // Print all users (latest to oldest)
    public void printAllUsers() {
        System.out.println("\n=== ALL USERS (Latest to Oldest) ===");
        if (users.isEmpty()) {
            System.out.println("No users found");
        } else {
            for (int i = users.size() - 1; i >= 0; i--) {
                System.out.println(users.get(i));
            }
        }
    }
}
