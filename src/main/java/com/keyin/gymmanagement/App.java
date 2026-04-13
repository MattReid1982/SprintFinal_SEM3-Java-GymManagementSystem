package com.keyin.gymmanagement;

import com.keyin.gymmanagement.models.Admin;
import com.keyin.gymmanagement.models.GymClass;
import com.keyin.gymmanagement.models.Member;
import com.keyin.gymmanagement.models.Trainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        List<Member> members = sampleMembers();
        List<Trainer> trainers = sampleTrainers();
        List<Admin> admins = sampleAdmins();
        List<GymClass> classes = sampleClasses();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String selection = scanner.nextLine().trim();

            switch (selection) {
                case "1" -> displayMembers(members);
                case "2" -> displayTrainers(trainers);
                case "3" -> displayAdmins(admins);
                case "4" -> displayClasses(classes);
                case "5" -> enrollMember(scanner, members, classes);
                case "6" -> removeMember(scanner, members, classes);
                case "7" -> {
                    System.out.println("Exiting Gym Management Console. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose a number from 1 to 7.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== Gym Management Console ===");
        System.out.println("1. Show all members");
        System.out.println("2. Show all trainers");
        System.out.println("3. Show all admins");
        System.out.println("4. Show all classes");
        System.out.println("5. Enroll member in a class");
        System.out.println("6. Remove member from a class");
        System.out.println("7. Exit");
    }

    private static List<Member> sampleMembers() {
        List<Member> members = new ArrayList<>();
        members.add(new Member(1, "Ava Carter", "ava.carter@example.com", "Standard", true));
        members.add(new Member(2, "Noah Johnson", "noah.johnson@example.com", "Premium", true));
        members.add(new Member(3, "Mia Lopez", "mia.lopez@example.com", "Student", false));
        return members;
    }

    private static List<Trainer> sampleTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer(1, "Liam Smith", "liam.smith@example.com", "Strength Training", 6));
        trainers.add(new Trainer(2, "Emma Brown", "emma.brown@example.com", "Yoga", 4));
        return trainers;
    }

    private static List<Admin> sampleAdmins() {
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin(1, "Olivia Hall", "olivia.hall@example.com"));
        return admins;
    }

    private static List<GymClass> sampleClasses() {
        List<GymClass> classes = new ArrayList<>();
        classes.add(new GymClass(1, "Morning Strength", "Mon/Wed/Fri 07:00", 12));
        classes.add(new GymClass(2, "Evening Yoga", "Tue/Thu 18:30", 10));
        return classes;
    }

    private static void displayMembers(List<Member> members) {
        System.out.println("--- Members ---");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    private static void displayTrainers(List<Trainer> trainers) {
        System.out.println("--- Trainers ---");
        for (Trainer trainer : trainers) {
            System.out.println(trainer);
        }
    }

    private static void displayAdmins(List<Admin> admins) {
        System.out.println("--- Admins ---");
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    private static void displayClasses(List<GymClass> classes) {
        System.out.println("--- Gym Classes ---");
        for (GymClass gymClass : classes) {
            System.out.println(gymClass);
        }
    }

    private static void enrollMember(Scanner scanner, List<Member> members, List<GymClass> classes) {
        Member member = chooseMember(scanner, members);
        if (member == null) {
            return;
        }
        GymClass gymClass = chooseClass(scanner, classes);
        if (gymClass == null) {
            return;
        }

        if (gymClass.addMember()) {
            System.out.printf("%s has been enrolled in %s.%n", member.getName(), gymClass.getClassName());
        } else {
            System.out.printf("Cannot enroll %s. %s is already full.%n", member.getName(), gymClass.getClassName());
        }
    }

    private static void removeMember(Scanner scanner, List<Member> members, List<GymClass> classes) {
        Member member = chooseMember(scanner, members);
        if (member == null) {
            return;
        }
        GymClass gymClass = chooseClass(scanner, classes);
        if (gymClass == null) {
            return;
        }

        if (gymClass.removeMember()) {
            System.out.printf("%s has been removed from %s.%n", member.getName(), gymClass.getClassName());
        } else {
            System.out.printf("Cannot remove %s. No members are currently enrolled in %s.%n", member.getName(),
                    gymClass.getClassName());
        }
    }

    private static Member chooseMember(Scanner scanner, List<Member> members) {
        displayMembers(members);
        System.out.print("Enter member ID: ");
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            for (Member member : members) {
                if (member.getId() == id) {
                    return member;
                }
            }
            System.out.println("Member not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID.");
        }
        return null;
    }

    private static GymClass chooseClass(Scanner scanner, List<GymClass> classes) {
        displayClasses(classes);
        System.out.print("Enter class ID: ");
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            for (GymClass gymClass : classes) {
                if (gymClass.getClassId() == id) {
                    return gymClass;
                }
            }
            System.out.println("Class not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid class ID.");
        }
        return null;
    }
}
