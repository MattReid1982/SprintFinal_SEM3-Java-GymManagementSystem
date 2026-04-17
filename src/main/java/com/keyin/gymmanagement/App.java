package com.keyin.gymmanagement;

import com.keyin.gymmanagement.dao.AdminDAO;
import com.keyin.gymmanagement.dao.GymClassDAO;
import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.dao.TrainerDAO;
import com.keyin.gymmanagement.db.DatabaseConnection;
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

            MemberDAO memberDAO = new MemberDAO(connection);
            TrainerDAO trainerDAO = new TrainerDAO(connection);
            AdminDAO adminDAO = new AdminDAO(connection);
            GymClassDAO gymClassDAO = new GymClassDAO(connection);

            members = memberDAO.findAll();
            trainers = trainerDAO.findAll();
            admins = adminDAO.findAll();
            classes = gymClassDAO.findAll();
            printSuccess("Connected to PostgreSQL on 127.0.0.1 using default username 'postgres'.");
        } catch (SQLException e) {
            printError("Failed to connect to PostgreSQL database. Please ensure the database is running.");
            e.printStackTrace();
            return;
        }

        GymClassDAO gymClassDAO = new GymClassDAO(connection);
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
                case "5" -> enrollMember(scanner, members, classes, gymClassDAO);
                case "6" -> removeMember(scanner, members, classes, gymClassDAO);
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
            GymClassDAO gymClassDAO) {
        Member member = chooseMember(scanner, members);
        if (member == null) {
            return;
        }
        GymClass gymClass = chooseClass(scanner, classes);
        if (gymClass == null) {
            return;
        }

        if (gymClass.addMember()) {
            persistClassEnrollment(gymClassDAO, gymClass);
            printSuccess(String.format("%s has been enrolled in %s.", member.getName(), gymClass.getClassName()));
        } else {
            printError(
                    String.format("Cannot enroll %s. %s is already full.", member.getName(), gymClass.getClassName()));
        }
    }

    private static void removeMember(Scanner scanner, List<Member> members, List<GymClass> classes,
            GymClassDAO gymClassDAO) {
        Member member = chooseMember(scanner, members);
        if (member == null) {
            return;
        }
        GymClass gymClass = chooseClass(scanner, classes);
        if (gymClass == null) {
            return;
        }

        if (gymClass.removeMember()) {
            persistClassEnrollment(gymClassDAO, gymClass);
            printSuccess(String.format("%s has been removed from %s.", member.getName(), gymClass.getClassName()));
        } else {
            printError(String.format("Cannot remove %s. No members are currently enrolled in %s.", member.getName(),
                    gymClass.getClassName()));
        }
    }

    private static void persistClassEnrollment(GymClassDAO gymClassDAO, GymClass gymClass) {
        if (gymClassDAO == null) {
            return;
        }

        if (gymClassDAO.update(gymClass)) {
            printSuccess("Changes saved to database.");
        } else {
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
