import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    private int numberOfNights;

    private RoomType roomType;
    private int roomPricePerNight;
    private int userBalanceAfterBooking;

    private static int bookingCounter = 1;

    public Booking(int userId, int roomNumber, Date checkIn, Date checkOut,
                   Room room, User user) {
        this.bookingId = bookingCounter++;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = new Date(checkIn.getTime());
        this.checkOut = new Date(checkOut.getTime());

        // Calculate number of nights
        long diffInMillies = checkOut.getTime() - checkIn.getTime();
        this.numberOfNights = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        // Store snapshot of room data
        this.roomType = room.getRoomType();
        this.roomPricePerNight = room.getPricePerNight();

        // Calculate total cost
        this.totalCost = numberOfNights * roomPricePerNight;

        // Store user balance after booking
        this.userBalanceAfterBooking = user.getBalance() - totalCost;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Date getCheckIn() {
        return new Date(checkIn.getTime());
    }

    public Date getCheckOut() {
        return new Date(checkOut.getTime());
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getRoomPricePerNight() {
        return roomPricePerNight;
    }

    public int getUserBalanceAfterBooking() {
        return userBalanceAfterBooking;
    }

    @Override
    public String toString() {
        return String.format("Booking{ID=%d, UserID=%d, Room=%d, CheckIn=%tF, CheckOut=%tF, " +
                        "Nights=%d, RoomType=%s, Price/Night=%d, TotalCost=%d, UserBalanceAfter=%d}",
                bookingId, userId, roomNumber, checkIn, checkOut, numberOfNights,
                roomType, roomPricePerNight, totalCost, userBalanceAfterBooking);
    }
}
