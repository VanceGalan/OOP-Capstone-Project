package model;
// NO seatType, NO VIP, NO price — price comes from Screening.price
public class Seat {
    private String seatId;
    private String screeningId;
    private char rowLetter;
    private int seatNumber;
    private boolean isReserved;
    public boolean reserveSeat() { if (isReserved) return false; isReserved = true; return true; }
    public void releaseSeat() { isReserved = false; }
}
