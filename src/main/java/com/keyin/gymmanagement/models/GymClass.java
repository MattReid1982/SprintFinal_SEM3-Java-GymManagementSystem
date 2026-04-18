package com.keyin.gymmanagement.models;

/** Represents a gym class and enrollment data. */
public class GymClass {
  private int classId;
  private String className;
  private String classSchedule;
  private int capacity;
  private int enrolled;

  /** Creates a class with zero enrolled members. */
  public GymClass(int classId, String className, String classSchedule, int capacity) {
    this(classId, className, classSchedule, capacity, 0);
  }

  /** Creates a class with full enrollment details. */
  public GymClass(int classId, String className, String classSchedule, int capacity, int enrolled) {
    this.classId = classId;
    this.className = className;
    this.classSchedule = classSchedule;
    this.capacity = capacity;
    this.enrolled = enrolled;
  }

  /** Gets class ID. */
  public int getClassId() {
    return classId;
  }

  /** Gets class name. */
  public String getClassName() {
    return className;
  }

  /** Gets class schedule. */
  public String getClassSchedule() {
    return classSchedule;
  }

  /** Gets class capacity. */
  public int getCapacity() {
    return capacity;
  }

  /** Gets number of enrolled members. */
  public int getEnrolled() {
    return enrolled;
  }

  /** Sets the number of enrolled members. */
  public void setEnrolled(int enrolled) {
    this.enrolled = enrolled;
  }

  /** Attempts to add one member to the class. */
  public boolean addMember() {
    if (enrolled < capacity) {
      enrolled++;
      return true;
    }
    return false;
  }

  /** Attempts to remove one enrolled member from the class. */
  public boolean removeMember() {
    if (enrolled > 0) {
      enrolled--;
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "ID: "
        + classId
        + " | Class: "
        + className
        + " | Schedule: "
        + classSchedule
        + " | Capacity: "
        + capacity
        + " | Enrolled: "
        + enrolled;
  }
}
