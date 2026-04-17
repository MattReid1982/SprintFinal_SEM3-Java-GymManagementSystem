package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.AdminDAO;
import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.dao.TrainerDAO;
import com.keyin.gymmanagement.models.Admin;
import com.keyin.gymmanagement.models.Member;
import com.keyin.gymmanagement.models.Trainer;
import java.util.List;
import java.util.Scanner;

public class UserManagementUIHandler {
    private final Scanner scanner;
    private final MemberDAO memberDAO;
    private final TrainerDAO trainerDAO;
    private final AdminDAO adminDAO;
    private final UIHelper uiHelper;

    public UserManagementUIHandler(Scanner scanner, MemberDAO memberDAO, TrainerDAO trainerDAO, AdminDAO adminDAO) {
        this.scanner = scanner;
        this.memberDAO = memberDAO;
        this.trainerDAO = trainerDAO;
        this.adminDAO = adminDAO;
        this.uiHelper = new UIHelper();
    }

    public void userManagementMenu() {
        boolean running = true;
        while (running) {
            uiHelper.clearTerminal();
            uiHelper.printSubmenuHeader("USER MANAGEMENT MENU");
            uiHelper.printOption("1. View All Members");
            uiHelper.printOption("2. View All Trainers");
            uiHelper.printOption("3. View All Admins");
            uiHelper.printOption("4. Add New Member");
            uiHelper.printOption("5. Add New Trainer");
            uiHelper.printOption("6. Add New Admin");
            uiHelper.printOption("7. Edit Member");
            uiHelper.printOption("8. Delete Member");
            uiHelper.printOption("9. Back to Main Menu");
            uiHelper.printPrompt("Choose an option: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> displayMembers();
                case "2" -> displayTrainers();
                case "3" -> displayAdmins();
                case "4" -> addNewMember();
                case "5" -> addNewTrainer();
                case "6" -> addNewAdmin();
                case "7" -> editMember();
                case "8" -> deleteMember();
                case "9" -> running = false;
                default -> uiHelper.printError("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    private void displayMembers() {
        List<Member> members = memberDAO.findAll();
        uiHelper.printSection("─── ALL MEMBERS ───");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    private void displayTrainers() {
        List<Trainer> trainers = trainerDAO.findAll();
        uiHelper.printSection("─── ALL TRAINERS ───");
        for (Trainer trainer : trainers) {
            System.out.println(trainer);
        }
    }

    private void displayAdmins() {
        List<Admin> admins = adminDAO.findAll();
        uiHelper.printSection("─── ALL ADMINS ───");
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    private void addNewMember() {
        uiHelper.printSection("ADD NEW MEMBER");
        uiHelper.printPrompt("Enter Member ID: ");
        int id = InputHelper.getIntInput(scanner);
        uiHelper.printPrompt("Enter Name: ");
        String name = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Email: ");
        String email = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Membership Type (Standard/Premium/Student): ");
        String membershipType = scanner.nextLine().trim();
        uiHelper.printPrompt("Is Active (true/false): ");
        boolean active = InputHelper.getBooleanInput(scanner);

        Member member = new Member(id, name, email, membershipType, active);
        if (memberDAO.create(member)) {
            uiHelper.printSuccess("Member added successfully!");
        } else {
            uiHelper.printError("Failed to add member.");
        }
    }

    private void addNewTrainer() {
        uiHelper.printSection("ADD NEW TRAINER");
        uiHelper.printPrompt("Enter Trainer ID: ");
        int id = InputHelper.getIntInput(scanner);
        uiHelper.printPrompt("Enter Name: ");
        String name = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Email: ");
        String email = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Speciality: ");
        String speciality = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Years of Experience: ");
        int yearsExp = InputHelper.getIntInput(scanner);

        Trainer trainer = new Trainer(id, name, email, speciality, yearsExp);
        if (trainerDAO.create(trainer)) {
            uiHelper.printSuccess("Trainer added successfully!");
        } else {
            uiHelper.printError("Failed to add trainer.");
        }
    }

    private void addNewAdmin() {
        uiHelper.printSection("ADD NEW ADMIN");
        uiHelper.printPrompt("Enter Admin ID: ");
        int id = InputHelper.getIntInput(scanner);
        uiHelper.printPrompt("Enter Name: ");
        String name = scanner.nextLine().trim();
        uiHelper.printPrompt("Enter Email: ");
        String email = scanner.nextLine().trim();

        Admin admin = new Admin(id, name, email);
        if (adminDAO.create(admin)) {
            uiHelper.printSuccess("Admin added successfully!");
        } else {
            uiHelper.printError("Failed to add admin.");
        }
    }

    private void editMember() {
        uiHelper.printSection("EDIT MEMBER");
        displayMembers();
        uiHelper.printPrompt("Enter Member ID to edit: ");
        int memberId = InputHelper.getIntInput(scanner);
        Member member = memberDAO.findById(memberId);

        if (member == null) {
            uiHelper.printError("Member not found.");
            return;
        }

        uiHelper.printPrompt("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            member.setName(name);
        }

        uiHelper.printPrompt("Enter new membership type (or press Enter to keep current): ");
        String membershipType = scanner.nextLine().trim();
        if (!membershipType.isEmpty()) {
            member.setMembershipType(membershipType);
        }

        if (memberDAO.update(member)) {
            uiHelper.printSuccess("Member updated successfully!");
        } else {
            uiHelper.printError("Failed to update member.");
        }
    }

    private void deleteMember() {
        uiHelper.printSection("DELETE MEMBER");
        displayMembers();
        uiHelper.printPrompt("Enter Member ID to delete: ");
        int memberId = InputHelper.getIntInput(scanner);

        if (memberDAO.delete(memberId)) {
            uiHelper.printSuccess("Member deleted successfully!");
        } else {
            uiHelper.printError("Failed to delete member.");
        }
    }
}
