package com.keyin.gymmanagement;

import com.keyin.gymmanagement.db.AdminRepository;
import com.keyin.gymmanagement.db.DatabaseConnection;
import com.keyin.gymmanagement.db.GymClassRepository;
import com.keyin.gymmanagement.db.MemberRepository;
import com.keyin.gymmanagement.db.TrainerRepository;
import com.keyin.gymmanagement.models.Admin;
import com.keyin.gymmanagement.models.GymClass;
import com.keyin.gymmanagement.models.Member;
import com.keyin.gymmanagement.models.Trainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";

    private static void printHeader(String text) {
        System.out.println(BOLD + CYAN + text + RESET);
    }

    private static void printOption(String text) {
        System.out.println(GREEN + text + RESET);
    }

    private static void printPrompt(String text) {
        System.out.print(BOLD + YELLOW + text + RESET);
    }

    private static void printSuccess(String text) {
        System.out.println(GREEN + text + RESET);
    }

    private static void printWarning(String text) {
        System.out.println(YELLOW + text + RESET);
    }

    private static void printError(String text) {
        System.out.println(RED + text + RESET);
    }

    private static void printSection(String text) {
        System.out.println(BOLD + PURPLE + text + RESET);
    }

    public static void main(String[] args) {
        Connection connection = null;
        List<Member> members;
        List<Trainer> trainers;
        List<Admin> admins;
        List<GymClass> classes;

        try {
            connection = DatabaseConnection.getConnection();
            DatabaseConnection.initializeSchema(connection);
            DatabaseConnection.seedDefaultsIfEmpty(connection);

            members = MemberRepository.findAll(connection);
            trainers = TrainerRepository.findAll(connection);
            admins = AdminRepository.findAll(connection);
            classes = GymClassRepository.findAll(connection);
            printSuccess("Connected to PostgreSQL on 127.0.0.1 using default username 'postgres'.");
        } catch (SQLException e) {
            printWarning("PostgreSQL unavailable. Running with in-memory sample data.");
            members = sampleMembers();
            trainers = sampleTrainers();
            admins = sampleAdmins();
            classes = sampleClasses();
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            printPrompt("Choose an option: ");
            String selection = scanner.nextLine().trim();

            switch (selection) {
                case "1" -> displayMembers(members);
                case "2" -> displayTrainers(trainers);
                case "3" -> displayAdmins(admins);
                case "4" -> displayClasses(classes);
                case "5" -> enrollMember(scanner, members, classes, connection);
                case "6" -> removeMember(scanner, members, classes, connection);
                case "7" -> {
                    printSuccess("Exiting Gym Management Console. Goodbye!");
                    running = false;
                }
                default -> printError("Invalid option. Please choose a number from 1 to 7.");
            }

            System.out.println();
        }

        scanner.close();
        closeConnectionQuietly(connection);
    }

    private static void closeConnectionQuietly(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException ignored) {
            // No action needed while shutting down.
        }
    }

    private static void printMenu() {
        printHeader("=== Gym Management Console ===");
        printOption("1. Show all members");
        printOption("2. Show all trainers");
        printOption("3. Show all admins");
        printOption("4. Show all classes");
        printOption("5. Enroll member in a class");
        printOption("6. Remove member from a class");
        printOption("7. Exit");
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
        printSection("--- Members ---");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    private static void displayTrainers(List<Trainer> trainers) {
        printSection("--- Trainers ---");
        for (Trainer trainer : trainers) {
            System.out.println(trainer);
        }
    }

    private static void displayAdmins(List<Admin> admins) {
        printSection("--- Admins ---");
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    private static void displayClasses(List<GymClass> classes) {
        printSection("--- Gym Classes ---");
        for (GymClass gymClass : classes) {
            System.out.println(gymClass);
        }
    }

    private static void enrollMember(Scanner scanner, List<Member> members, List<GymClass> classes,
            Connection connection) {
        Member member = chooseMember(scanner, members);
        if (member == null) {
            return;
        }
        GymClass gymClass = chooseClass(scanner, classes);
        if (gymClass == null) {
            return;
        }

        if (gymClass.addMember()) {
            persistClassEnrollment(connection, gymClass);
            printSuccess(String.format("%s has been enrolled in %s.", member.getName(), gymClass.getClassName()));
        } else {
            printError(
                    String.format("Cannot enroll %s. %s is already full.", member.getName(), gymClass.getClassName()));
        }
    }

    private static void removeMember(Scanner scanner, List<Member> members, List<GymClass> classes,
            Connection connection) {
        Member member = chooseMember(scanner, members);
        if (member == null) {
            return;
        }
        GymClass gymClass = chooseClass(scanner, classes);
        if (gymClass == null) {
            return;
        }

        if (gymClass.removeMember()) {
            persistClassEnrollment(connection, gymClass);
            printSuccess(String.format("%s has been removed from %s.", member.getName(), gymClass.getClassName()));
        } else {
            printError(String.format("Cannot remove %s. No members are currently enrolled in %s.", member.getName(),
                    gymClass.getClassName()));
        }
    }

    private static void persistClassEnrollment(Connection connection, GymClass gymClass) {
        if (connection == null) {
            return;
        }

        try {
            GymClassRepository.updateEnrolledCount(connection, gymClass.getClassId(), gymClass.getEnrolled());
        } catch (SQLException e) {
            printWarning("Enrollment updated in memory but could not be saved to PostgreSQL.");
        }
    }

    private static Member chooseMember(Scanner scanner, List<Member> members) {
        displayMembers(members);
        printPrompt("Enter member ID: ");
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            for (Member member : members) {
                if (member.getId() == id) {
                    return member;
                }
            }
            printError("Member not found.");
        } catch (NumberFormatException e) {
            printError("Invalid member ID.");
        }
        return null;
    }

    private static GymClass chooseClass(Scanner scanner, List<GymClass> classes) {
        displayClasses(classes);
        printPrompt("Enter class ID: ");
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            for (GymClass gymClass : classes) {
                if (gymClass.getClassId() == id) {
                    return gymClass;
                }
            }
            printError("Class not found.");
        } catch (NumberFormatException e) {
            printError("Invalid class ID.");
        }
        return null;
    }
}
