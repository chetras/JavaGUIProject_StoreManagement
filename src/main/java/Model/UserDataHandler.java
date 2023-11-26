package Model;

import Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDataHandler {
    private static final String FILE_PATH = "user.txt";

    public static void saveUser(User user) {
        List<User> users = loadUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.println(u.getFullname() + "," + u.getUsername() + "," + u.getEmail() + "," + u.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    User user = new User();
                    user.setFullname(parts[0]);
                    user.setUsername(parts[1]);
                    user.setEmail(parts[2]);
                    user.setPassword(parts[3]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void deleteUser(User user) {
        List<User> users = loadUsers();
        users.remove(user);
        saveUsers(users);
        System.out.println("User deleted: " + user.getUsername());
    }

    static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                if (u.isActive()) {
                    writer.println(u.getFullname() + "," + u.getUsername() + "," + u.getEmail() + "," + u.getPassword());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
