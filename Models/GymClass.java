package Models;

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

    public boolean addMember() {
        if (enrolled < capacity) {
            enrolled++;
            return true; // Successfully added a member
        } else {
            return false; // Class is full
        }
    }

    public boolean removeMember() {
        if (enrolled > 0) {
            enrolled--;
            return true; // Successfully removed a member
        } else {
            return false; // No members to remove
        }
    }

    @Override
    public String toString() {
        return "Class: " + className + " | Schedule: " + classSchedule + " | Capacity: " + capacity + " | Enrolled: " + enrolled;
    
    }
}
