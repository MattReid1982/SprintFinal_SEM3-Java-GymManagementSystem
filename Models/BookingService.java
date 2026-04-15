package Models;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final List<Booking> bookings = new ArrayList<>();
    private int bookingIdCounter = 1;

    public Booking bookClass(Member member, GymClass gymClass) {

        int count = Math.toIntExact(bookings.stream()
                .filter(b -> b.getGymClass().equals(gymClass))
                .count());

        if (count >= gymClass.getCapacity()) {
            throw new IllegalStateException("Class is full");
        }

        Booking booking = new Booking(bookingIdCounter++, member, gymClass);
        bookings.add(booking);
        return booking;
    }

    public List<Booking> getBookingsForMember(Member member) {
        return bookings.stream()
                .filter(b -> b.getMember().equals(member))
                .toList();
    }
}
