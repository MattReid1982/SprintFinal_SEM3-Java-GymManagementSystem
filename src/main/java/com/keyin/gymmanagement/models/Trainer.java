package com.keyin.gymmanagement.models;

/**
 * Represents a trainer with specialization and years of experience.
 */
public class Trainer extends User {
    private String speciality;
    private int yearsExperience;

    /**
     * Creates a trainer.
     *
     * @param id              trainer ID
     * @param name            trainer name
     * @param email           trainer email
     * @param speciality      trainer specialty area
     * @param yearsExperience years of professional experience
     */
    public Trainer(int id, String name, String email, String speciality, int yearsExperience) {
        super(id, name, email);
        this.speciality = speciality;
        this.yearsExperience = yearsExperience;
    }

    /**
     * Gets trainer specialty.
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Gets years of experience.
     */
    public int getYearsExperience() {
        return yearsExperience;
    }

    /**
     * Sets trainer specialty.
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    /**
     * Sets years of trainer experience.
     */
    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    @Override
    public String toString() {
        return super.toString() + ", Speciality: " + speciality + ", Years of Experience: " + yearsExperience;
    }
}
