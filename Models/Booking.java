package Models;

public class Booking {
    private int id;
    private Member member;
    private GymClass gymClass;

    public Booking(int id, Member member, GymClass gymClass) {
        this.id = id;
        this.member = member;
        this.gymClass = gymClass;
    }

    // getters and setters


    public GymClass getGymClass() {
        return gymClass;
    }

    public Member getMember() {
        return member;
    }
}

