package com.cst242.finalproject.server.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class implements the {@code FileIOInterface} refer to that for
 * documentation.
 *
 * @author James DeCarlo
 */
public class FileIO implements FileIOInterface {

    @Override
    public boolean register(String loginId, int hashedPassword, String firstName, String lastName, String screenName) {
        File file = new File("userlist.dat");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            int accountNumber = 1000;

            while (objectInputStream.available() > 0) {
                User user = (User) objectInputStream.readObject();
                if (user.getAccountNumber() > accountNumber) {
                    accountNumber = user.getAccountNumber();
                }
                if(user.getLoginId().toLowerCase().equals(loginId.toLowerCase())){
                    return false;
                }
            }

            accountNumber++;
            User user = new User(accountNumber, loginId, hashedPassword, firstName, lastName, screenName);

            //Writing the User object to the file.
            objectOutputStream.writeObject(user);

            fileInputStream.close();
            objectInputStream.close();
            fileOutputStream.close();
            objectOutputStream.close();
            
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public User loginUser(String loginId, int hashedPassword) {
        User user = new User(1001, "JohnDoe", 6969, "John", "Doe", "SomeGuy");

        return user;
    }

    @Override
    public boolean updateUser(int accountNumber, int hashedPassword, String firstName, String lastName, String screenName) {
        return false;
    }

}
