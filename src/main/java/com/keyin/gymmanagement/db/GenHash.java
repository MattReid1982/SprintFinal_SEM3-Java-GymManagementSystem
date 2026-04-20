package com.keyin.gymmanagement.db;
import org.mindrot.jbcrypt.BCrypt;

public class GenHash {
    public static void main(String[] args) {
        String[] passwords = { "admin123", "member123" };
        for (String pwd : passwords) {
            String hash = BCrypt.hashpw(pwd, BCrypt.gensalt());
            System.out.println("Password '" + pwd + "' -> " + hash);
        }
    }
}
