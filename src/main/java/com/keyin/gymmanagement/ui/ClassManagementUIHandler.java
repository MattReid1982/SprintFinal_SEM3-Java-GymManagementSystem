package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.GymClassDAO;
import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.models.GymClass;
import com.keyin.gymmanagement.models.Member;
import java.util.List;
import java.util.Scanner;

public class ClassManagementUIHandler {
    private final Scanner scanner;
    private final GymClassDAO gymClassDAO;
    private final MemberDAO memberDAO;
    private final UIHelper uiHelper;

    public ClassManagementUIHandler(Scanner scanner, GymClassDAO gymClassDAO, MemberDAO memberDAO) {
        this.scanner = scanner;
        this.gymClassDAO = gymClassDAO;
        this.memberDAO = memberDAO;
        this.uiHelper = new UIHelper();
    }

    public void classManagementMenu() {
        boolean running = true;
        while (running) {
            uiHelper.clearTerminal();
            uiHelper.printSubmenuHeader("CLASS MANAGEMENT MENU");
            uiHelper.printOption("1. View All Classes");
            uiHelper.printOption("2. Enroll Member in Class");
            uiHelper.printOption("3. Remove Member from Class");
            uiHelper.printOption("4. View Class Details");
            uiHelper.printOption("5. Add New Class");
            uiHelper.printOption("6. Edit Class");
            uiHelper.printOption("7. Delete Class");
            uiHelper.printOption("8. Back to Main Menu");
            uiHelper.printPrompt("Choose an option: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> displayClasses();
                case "2" -> enrollMemberInClass();
                case "3" -> removeMemberFromClass();
                case "4" -> viewClassDetails();
                case "5" -> addNewClass();
                case "6" -> editClass();
                case "7" -> deleteClass();
                case "8" -> running = false;
                default -> uiHelper.printError("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    public void displayClasses() {
        List<GymClass> classes = gymClassDAO.findAll();
        uiHelper.printSection("─── ALL CLASSES ───");
        for (GymClass gymClass : classes) {
            System.out.println(gymClass);
        }
    }

    private void viewClassDetails() {
        displayClasses();
        uiHelper.printPrompt("Enter Class ID to view details: ");
        int id = InputHelper.getIntInput(scanner);
        GymClass gymClass = gymClassDAO.findById(id);

        if (gymClass == null) {
            uiHelper.printError("Class not found.");
            return;
        }

        uiHelper.printSection(String.format("CLASS DETAILS: %s", gymClass.getClassName()));
        System.out.println(gymClass);
        System.out.println("Capacity Remaining: " + (gymClass.getCapacity() - gymClass.getEnrolled()));
    }

    private void enrollMemberInClass() {
        displayMembers();
        uiHelper.printPrompt("Enter Member ID: ");
        int memberId = InputHelper.getIntInput(scanner);

        Member member = memberDAO.findById(memberId);
        if (member == null) {
            uiHelper.printError("Member not found.");
            return;
        }

        displayClasses();
        uiHelper.printPrompt("Enter Class ID: ");
        int classId = InputHelper.getIntInput(scanner);

        GymClass gymClass = gymClassDAO.findById(classId);
        if (gymClass == null) {
            uiHelper.printError("Class not found.");
            return;
        }

        if (gymClass.addMember()) {
            if (gymClassDAO.update(gymClass)) {
                uiHelper.printSuccess("Member " + member.getName() + " enrolled successfully!");
            } else {
                uiHelper.printError("Failed to save enrollment.");
            }
        } else {
            uiHelper.printError("Class is full. Cannot enroll member.");
        }
    }

    private void removeMemberFromClass() {
        displayClasses();
        uiHelper.printPrompt("Enter Class ID: ");
        int classId = InputHelper.getIntInput(scanner);

        GymClass gymClass = gymClassDAO.findById(classId);
        if (gymClass == null) {
            uiHelper.printError("Class not found.");
            return;
        }

        if (gymClass.removeMember()) {
            if (gymClassDAO.update(gymClass)) {
                uiHelper.printSuccess("Member removed successfully!");
            } else {
                uiHelper.printError("Failed to save removal.");
            }
        } else {
            uiHelper.printError("No members to remove.");
        }
    }

    private void addNewClass() {
        uiHelper.printSection("ADD NEW CLASS");
        uiHelper.printPrompt("Enter Class ID: ");
        int classId = InputHelper.getIntInput(scanner);
        uiHelper.printPrompt("Enter Class Name: ");
        String className = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Schedule: ");
        String schedule = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Capacity: ");
        int capacity = InputHelper.getIntInput(scanner);

        GymClass gymClass = new GymClass(classId, className, schedule, capacity);
        if (gymClassDAO.create(gymClass)) {
            uiHelper.printSuccess("Class added successfully!");
        } else {
            uiHelper.printError("Failed to add class.");
        }
    }

    private void editClass() {
        uiHelper.printSection("EDIT CLASS");
        displayClasses();
        uiHelper.printPrompt("Enter Class ID to edit: ");
        int classId = InputHelper.getIntInput(scanner);
        GymClass gymClass = gymClassDAO.findById(classId);

        if (gymClass == null) {
            uiHelper.printError("Class not found.");
            return;
        }

        uiHelper.printWarning("Feature not yet implemented.");
    }

    private void deleteClass() {
        uiHelper.printSection("DELETE CLASS");
        displayClasses();
        uiHelper.printPrompt("Enter Class ID to delete: ");
        int classId = InputHelper.getIntInput(scanner);

        if (gymClassDAO.delete(classId)) {
            uiHelper.printSuccess("Class deleted successfully!");
        } else {
            uiHelper.printError("Failed to delete class.");
        }
    }

    private void displayMembers() {
        uiHelper.printSection("─── ALL MEMBERS ───");
        memberDAO.findAll().forEach(System.out::println);
    }
}
