package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDataHandler {
    File f = new File("user.txt");
    private static final String FILE_PATH = "user.txt";

    public static void saveUser(User user) {
        List<User> users = loadUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.println(u.getUsername() + "," + u.getEmail() + "," + u.getPassword());
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
                if (parts.length == 3) {
                    User user = new User();
                    user.setUsername(parts[0]);
                    user.setEmail(parts[1]);
                    user.setPassword(parts[2]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
