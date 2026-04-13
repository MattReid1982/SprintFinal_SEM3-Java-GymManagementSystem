package com.keyin.gymmanagement.models;

public class Trainer extends User {
    private String speciality;
    private int yearsExperience;

    public Trainer(int id, String name, String email, String speciality, int yearsExperience) {
        super(id, name, email);
        this.speciality = speciality;
        this.yearsExperience = yearsExperience;
    }

    public String getSpeciality() {
        return speciality;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    @Override
    public String toString() {
        return super.toString() + ", Speciality: " + speciality + ", Years of Experience: " + yearsExperience;
    }
}
