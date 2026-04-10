package Models;

// GymClass class representing a gym class in the GMS, with attributes for class details and methods to manage enrollment
public class GymClass {
    private int classId;
    private String className;
    private String classSchedule;
    private int capacity;
    private int enrolled;

    public GymClass(int classId, String className, String classSchedule, int capacity) {
        this.classId = classId;
        this.className = className;
        this.classSchedule = classSchedule;
        this.capacity = capacity;
        this.enrolled = 0; // Initially, no one is enrolled
    }

    // Getters 
    public int getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getClassSchedule() {
        return classSchedule;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    // Setters
    // Note: classId is typically not changed, so no setter for it
    public boolean addMember() {
        if (enrolled < capacity) {
            enrolled++;
            return true; // Successfully added a member
        } else {
            return false; // Class is full
        }
    }

    // Note: This method removes a member from the class
    public boolean removeMember() {
        if (enrolled > 0) {
            enrolled--;
            return true; // Successfully removed a member
        } else {
            return false; // No members to remove
        }
    }

    // toString method to display gym class information
    @Override
    public String toString() {
        return "Class: " + className + " | Schedule: " + classSchedule + " | Capacity: " + capacity + " | Enrolled: " + enrolled;
    
    }
}
