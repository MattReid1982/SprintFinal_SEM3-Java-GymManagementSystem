package Models;

// Admin class extending User, representing an admin in the GMS
class Admin extends User {
    public Admin(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String toString() {
        return super.toString() + " (Admin)";
    }
}
