package com.cst242.finalproject.server.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@code FileIOInterface} refer to that for
 * documentation.
 *
 * @author James DeCarlo
 */
public class FileIO implements FileIOInterface {

    BufferedReader reader;
    PrintWriter writer;

    int accountNumber;

    @Override
    public boolean register(String loginId, int hashedPassword, String firstName, String lastName, String screenName) {

        File file = new File("userlist.dat");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                return false;
            }
        }

        if (file.length() == 0) {
            try {
                writer = new PrintWriter(file);

                accountNumber = 1001;

                User user = new User(accountNumber, loginId, hashedPassword, firstName, lastName, screenName);

                //Writing the User object to the file.
                writer.println(user.toString());

                writer.close();

            } catch (IOException e) {
                return false;
            }

        } else {
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (IOException e) {
                return false;
            }

            List<User> users = new ArrayList<>();

            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    if (!line.equals("")) {
                        User u = new User(line.trim());
                        users.add(u);
                    }
                }
                reader.close();
            } catch (IOException ex) {
                return false;
            }

            // check if login id is unique and find max account number index
            accountNumber = 1000;

            for (User u : users) {                
                if (u.getAccountNumber() > accountNumber) {
                    accountNumber = u.getAccountNumber();
                }
                if (u.getLoginId().toLowerCase().equals(loginId.toLowerCase())) {
                    return false;
                }
            }

            // all went well create user and add to list array
            accountNumber++;
            User user = new User(accountNumber, loginId, hashedPassword, firstName, lastName, screenName);
            users.add(user);

            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException ex) {
                return false;
            }

            for (User u : users) {                
                writer.append(u.toString());
                writer.flush();
            }
                        
            writer.close();
            
        }
        return true;
    }

    @Override
    public User loginUser(String loginId, int hashedPassword
    ) {
        User user = new User(1001, "JohnDoe", 6969, "John", "Doe", "SomeGuy");

        return user;
    }

    @Override
    public boolean updateUser(int accountNumber, int hashedPassword, String firstName, String lastName, String screenName
    ) {
        return false;
    }

}
