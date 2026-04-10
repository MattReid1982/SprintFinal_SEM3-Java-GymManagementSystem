package Models;

// Member class extending User, representing a member in the GMS
class Member extends User {
    private String membershipType;
    private boolean active;

    public Member(int id, String name, String email, String membershipType, boolean active) {
        super(id, name, email);
        this.membershipType = membershipType;
        this.active = active;
    }

    // Getters
    public String getMembershipType() {
        return membershipType;
    }

    public boolean isActive() {
        return active;
    }

    // Setters
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    // toString method to display member information
    @Override
    public String toString() {
        return super.toString() + ", Membership Type: " + membershipType + ", Active: " + active;
    }
}
