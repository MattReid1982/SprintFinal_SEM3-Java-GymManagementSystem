package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.GymClassDAO;
import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.dao.MerchandiseDAO;
import com.keyin.gymmanagement.dao.TrainerDAO;
import com.keyin.gymmanagement.models.GymClass;
import com.keyin.gymmanagement.models.Member;
import com.keyin.gymmanagement.models.Merchandise;
import com.keyin.gymmanagement.models.Trainer;
import java.util.List;
import java.util.Scanner;

public class ReportsUIHandler {
    private final Scanner scanner;
    private final MemberDAO memberDAO;
    private final GymClassDAO gymClassDAO;
    private final TrainerDAO trainerDAO;
    private final MerchandiseDAO merchandiseDAO;
    private final UIHelper uiHelper;

    public ReportsUIHandler(Scanner scanner, MemberDAO memberDAO, GymClassDAO gymClassDAO,
            TrainerDAO trainerDAO, MerchandiseDAO merchandiseDAO) {
        this.scanner = scanner;
        this.memberDAO = memberDAO;
        this.gymClassDAO = gymClassDAO;
        this.trainerDAO = trainerDAO;
        this.merchandiseDAO = merchandiseDAO;
        this.uiHelper = new UIHelper();
    }

    public void reportsMenu() {
        boolean running = true;
        while (running) {
            uiHelper.printHeader("╔════════════════════════════════════════╗");
            uiHelper.printHeader("║           REPORTS MENU                 ║");
            uiHelper.printHeader("╚════════════════════════════════════════╝");
            uiHelper.printOption("1. Member Summary Report");
            uiHelper.printOption("2. Class Enrollment Report");
            uiHelper.printOption("3. Trainer Statistics");
            uiHelper.printOption("4. Merchandise Inventory Report");
            uiHelper.printOption("5. Revenue Report");
            uiHelper.printOption("6. Back to Main Menu");
            uiHelper.printPrompt("Choose an option: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> memberSummaryReport();
                case "2" -> classEnrollmentReport();
                case "3" -> trainerStatistics();
                case "4" -> merchandiseInventoryReport();
                case "5" -> revenueReport();
                case "6" -> running = false;
                default -> uiHelper.printError("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    private void memberSummaryReport() {
        List<Member> members = memberDAO.findAll();
        uiHelper.printSection("─── MEMBER SUMMARY REPORT ───");
        System.out.println("Total Members: " + members.size());
        System.out.println("Active Members: " + members.stream().filter(Member::isActive).count());
        System.out.println("Inactive Members: " + members.stream().filter(m -> !m.isActive()).count());
    }

    private void classEnrollmentReport() {
        List<GymClass> classes = gymClassDAO.findAll();
        uiHelper.printSection("─── CLASS ENROLLMENT REPORT ───");
        for (GymClass gymClass : classes) {
            double occupancyRate = (gymClass.getEnrolled() * 100.0) / gymClass.getCapacity();
            System.out.println(String.format("%s | Enrolled: %d/%d (%.1f%%)",
                    gymClass.getClassName(), gymClass.getEnrolled(), gymClass.getCapacity(), occupancyRate));
        }
    }

    private void trainerStatistics() {
        List<Trainer> trainers = trainerDAO.findAll();
        uiHelper.printSection("─── TRAINER STATISTICS ───");
        System.out.println("Total Trainers: " + trainers.size());
        double avgExperience = trainers.stream().mapToInt(Trainer::getYearsExperience).average().orElse(0);
        System.out.println(String.format("Average Experience: %.1f years", avgExperience));
    }

    private void merchandiseInventoryReport() {
        List<Merchandise> items = merchandiseDAO.findAll();
        uiHelper.printSection("─── MERCHANDISE INVENTORY REPORT ───");
        double totalInventoryValue = items.stream().mapToDouble(m -> m.getPrice() * m.getQuantityInStock()).sum();
        System.out.println("Total Products: " + items.size());
        System.out.println("Total Units in Stock: " + items.stream().mapToInt(Merchandise::getQuantityInStock).sum());
        System.out.println(String.format("Total Inventory Value: $%.2f", totalInventoryValue));
    }

    private void revenueReport() {
        uiHelper.printSection("─── REVENUE REPORT ───");
        uiHelper.printWarning("Feature coming soon: Integrate with sales transactions");
    }
}
