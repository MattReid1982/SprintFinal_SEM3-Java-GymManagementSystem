package com.keyin.gymmanagement.models;

/** Represents a gym member with a membership type and active status. */
public class Member extends User {
  private String membershipType;
  private boolean active;

  /**
   * Creates a member.
   *
   * @param id member ID
   * @param name member name
   * @param email member email
   * @param membershipType membership type label
   * @param active membership status
   */
  public Member(int id, String name, String email, String membershipType, boolean active) {
    super(id, name, email);
    this.membershipType = membershipType;
    this.active = active;
  }

  /** Gets membership type. */
  public String getMembershipType() {
    return membershipType;
  }

  /** Checks if member is active. */
  public boolean isActive() {
    return active;
  }

  /** Sets the membership type. */
  public void setMembershipType(String membershipType) {
    this.membershipType = membershipType;
  }

  /** Sets member active status. */
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return super.toString() + ", Membership Type: " + membershipType + ", Active: " + active;
  }
}
