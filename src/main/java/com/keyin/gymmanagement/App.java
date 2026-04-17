package com.keyin.gymmanagement;

import com.keyin.gymmanagement.dao.AdminDAO;
import com.keyin.gymmanagement.dao.AuthDAO;
import com.keyin.gymmanagement.dao.GymClassDAO;
import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.dao.MerchandiseDAO;
import com.keyin.gymmanagement.dao.TrainerDAO;
import com.keyin.gymmanagement.db.DatabaseConnection;
import com.keyin.gymmanagement.models.UserAuth;
import com.keyin.gymmanagement.ui.AdminMaintenanceUIHandler;
import com.keyin.gymmanagement.ui.AdminUIHandler;
import com.keyin.gymmanagement.ui.AuthUIHandler;
import com.keyin.gymmanagement.ui.ClassManagementUIHandler;
import com.keyin.gymmanagement.ui.MemberUIHandler;
import com.keyin.gymmanagement.ui.MembershipUIHandler;
import com.keyin.gymmanagement.ui.MerchandiseUIHandler;
import com.keyin.gymmanagement.ui.ReportsUIHandler;
import com.keyin.gymmanagement.ui.TrainerUIHandler;
import com.keyin.gymmanagement.ui.UIHelper;
import com.keyin.gymmanagement.ui.UserManagementUIHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    private static Connection connection;
    private static Scanner scanner;
    private static AuthDAO authDAO;
    private static MemberDAO memberDAO;
    private static TrainerDAO trainerDAO;
    private static AdminDAO adminDAO;
    private static GymClassDAO gymClassDAO;
    private static MerchandiseDAO merchandiseDAO;
    private static UserAuth currentUser;

    private static AuthUIHandler authHandler;
    private static MemberUIHandler memberHandler;
    private static TrainerUIHandler trainerHandler;
    private static AdminUIHandler adminHandler;
    private static UIHelper uiHelper;

    public static void main(String[] args) {
        try {
            connection = DatabaseConnection.getConnection();
            DatabaseConnection.initializeSchema(connection);
            DatabaseConnection.seedDefaultsIfEmpty(connection);

            initializeDAOs();
            uiHelper = new UIHelper();
            uiHelper.printSuccess("Connected to PostgreSQL on 127.0.0.1 using default username 'postgres'.");

            scanner = new Scanner(System.in);

            boolean appRunning = true;
            while (appRunning) {
                authHandler = new AuthUIHandler(scanner, authDAO);
                currentUser = authHandler.authenticationMenu();

                if (currentUser == null) {
                    appRunning = false;
                    continue;
                }

                if ("GUEST".equals(currentUser.getRole())) {
                    MembershipUIHandler membershipHandler = new MembershipUIHandler(scanner, memberDAO, authDAO);
                    if (membershipHandler.selectMembership(currentUser)) {
                        mainMenu();
                    }
                } else {
                    mainMenu();
                }
            }
        } catch (SQLException e) {
            if (uiHelper != null) {
                uiHelper.printError("Failed to connect to PostgreSQL database. Please ensure the database is running.");
            } else {
                System.err.println("Failed to connect to PostgreSQL database: " + e.getMessage());
            }
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private static void initializeDAOs() {
        authDAO = new AuthDAO(connection);
        memberDAO = new MemberDAO(connection);
        trainerDAO = new TrainerDAO(connection);
        adminDAO = new AdminDAO(connection);
        gymClassDAO = new GymClassDAO(connection);
        merchandiseDAO = new MerchandiseDAO(connection);
    }

    private static void mainMenu() {
        boolean running = true;
        while (running) {
            updateHandlersWithCurrentUser();

            if ("MEMBER".equals(currentUser.getRole())) {
                running = memberHandler.memberMenu();
            } else if ("TRAINER".equals(currentUser.getRole())) {
                running = trainerHandler.trainerMenu();
            } else {
                running = adminHandler.adminMenu();
            }
        }
    }

    private static void updateHandlersWithCurrentUser() {
        ClassManagementUIHandler classHandler = new ClassManagementUIHandler(scanner, gymClassDAO, memberDAO);
        UserManagementUIHandler userHandler = new UserManagementUIHandler(scanner, memberDAO, trainerDAO, adminDAO);
        MerchandiseUIHandler merchandiseHandler = new MerchandiseUIHandler(scanner, merchandiseDAO);
        ReportsUIHandler reportsHandler = new ReportsUIHandler(scanner, memberDAO, gymClassDAO, trainerDAO,
                merchandiseDAO);
        AdminMaintenanceUIHandler maintenanceHandler = new AdminMaintenanceUIHandler(scanner);

        memberHandler = new MemberUIHandler(scanner, memberDAO, gymClassDAO, merchandiseDAO, currentUser);
        trainerHandler = new TrainerUIHandler(scanner, memberDAO, currentUser, classHandler, reportsHandler);
        adminHandler = new AdminUIHandler(scanner, currentUser, userHandler, classHandler, merchandiseHandler,
                reportsHandler, maintenanceHandler);
    }

    private static void closeResources() {
        if (scanner != null) {
            scanner.close();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
