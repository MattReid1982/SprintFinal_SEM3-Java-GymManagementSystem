package com.keyin.gymmanagement.models;

public class GymClass {
    private int classId;
    private String className;
    private String classSchedule;
    private int capacity;
    private int enrolled;

    public GymClass(int classId, String className, String classSchedule, int capacity) {
        this(classId, className, classSchedule, capacity, 0);
    }

    public GymClass(int classId, String className, String classSchedule, int capacity, int enrolled) {
        this.classId = classId;
        this.className = className;
        this.classSchedule = classSchedule;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }

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

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    public boolean addMember() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean removeMember() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ID: " + classId + " | Class: " + className + " | Schedule: " + classSchedule + " | Capacity: "
                + capacity + " | Enrolled: " + enrolled;
    }
}
