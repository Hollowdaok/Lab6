
import com.denys.Theater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TheaterTest {
    Theater theater;

    @BeforeEach
    void setUp() {
        theater = new Theater(5, 10, 20);
    }

    @Test
    void BookSeats_ValidSeats_ShouldSetThemBooked() {
        int hallNumber = 0;
        int row = 1;

        theater.bookSeats(hallNumber, row, 6, 4, 11, 7, 13);

        assertTrue(theater.isSeatBooked(hallNumber, row, 6));
        assertTrue(theater.isSeatBooked(hallNumber, row, 4));
        assertTrue(theater.isSeatBooked(hallNumber, row, 11));
        assertTrue(theater.isSeatBooked(hallNumber, row, 7));
        assertTrue(theater.isSeatBooked(hallNumber, row, 13));
    }

    @Test
    void BookSeats_InvalidSeats_ShouldThrow() {
        int hallNumber = 99;
        int row = 99;
        int seat = 99;

        assertThrows(Exception.class, () -> theater.bookSeats(hallNumber, row, seat));
    }

    @Test
    void CancelBooking_BookedSeats_ShouldSetThemUnbooked() {
        int hallNumber = 0;
        int row = 1;
        theater.bookSeats(hallNumber, row, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);

        theater.cancelBooking(hallNumber, row, 1, 4, 6, 9, 12);

        assertFalse(theater.isSeatBooked(hallNumber, row, 1));
        assertFalse(theater.isSeatBooked(hallNumber, row, 4));
        assertFalse(theater.isSeatBooked(hallNumber, row, 6));
        assertFalse(theater.isSeatBooked(hallNumber, row, 9));
        assertFalse(theater.isSeatBooked(hallNumber, row, 12));
    }

    @Test
    void CheckAvailability_AvailableSeats_ShouldReturnTrue() {
        int hallNumber = 0;
        int row = 2;
        int numSeats = 7;
        theater.bookSeats(hallNumber, row, 0, 10, 12, 6);

        var actual = theater.checkAvailability(hallNumber,row, numSeats);

        assertTrue(actual);
    }

    @Test
    void CheckAvailability_UnavailableSeats_ShouldReturnFalse() {
        int hallNumber = 0;
        int row = 0;
        theater.bookSeats(hallNumber, row, 0, 2, 5, 7, 10, 15);

        var actual = theater.checkAvailability(hallNumber, row, 10);
        actual = theater.checkAvailability(hallNumber, row, 7);

        assertFalse(actual);
    }

    @Test
    void PrintSeatingArrangement_ShouldNotThrow() {
        int hallNumber = 0;
        int row = 0;
        theater.bookSeats(hallNumber, row, 0, 2, 5, 7, 10, 15);

        assertDoesNotThrow(() -> theater.printSeatingArrangement(0));
    }

    @Test
    void FindBestAvailable_HallWithBookedSeats_ShouldReturnBest(){
        int hall = 0;
        int numSeats = 3;
        var expected = new Theater.SeatIndex();
        expected.row = 1;
        expected.seat = 7;
        theater.bookSeats(hall, 1, 1, 6, 10, 15);

        var actual = theater.findBestAvailable(hall, numSeats);

        assertTrue(actual.isPresent());
        assertEquals(expected.row, actual.get().row);
        assertEquals(expected.seat, actual.get().seat);
    }

    @Test
    void FindBestAvailable_HallWithoutRequiredNumberOfSeats_ShouldReturnEmptyOptional(){
        int hall = 0;
        int numSeats = 99;

        var actual = theater.findBestAvailable(hall, numSeats);

        assertFalse(actual.isPresent());
    }

    @Test
    void AutoBook_HallWithBookedSeats_ShouldReturnAndBookBest(){
        theater.bookSeats(0, 0, 1, 6, 10, 15);
        var expected = new Theater.SeatIndex();
        expected.row = 0;
        expected.seat = 7;

        var actual = theater.autoBook(0, 3);

        assertTrue(actual.isPresent());
        assertEquals(expected.row, actual.get().row);
        assertEquals(expected.seat, actual.get().seat);
        assertTrue(theater.isSeatBooked(0, 0, 7));
        assertTrue(theater.isSeatBooked(0, 0, 8));
        assertTrue(theater.isSeatBooked(0, 0, 9));
    }

    @Test
    void AutoBook_HallWithoutRequiredNumberOfSeats_ShouldReturnEmptyOptional(){
        int hall = 0;
        int numSeats = 99;

        var actual = theater.autoBook(hall, numSeats);

        assertFalse(actual.isPresent());
    }
}