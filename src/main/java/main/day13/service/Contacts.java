package main.day13.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import main.day13.models.ContactModel;

/**
 * Contacts
 */
@Component
public class Contacts {
    // handles all file handling for the application
    private Logger logger = LoggerFactory.getLogger(Contacts.class);
    private final Path dirPath;

    @Autowired
    public Contacts(ApplicationArguments args) {
        List<String> dataDir = args.getOptionValues("dataDir");
        this.dirPath = Path.of(dataDir.get(0));
        checkDirectory();
    }

    // checks and create if directory does not exist
    private void checkDirectory() {
        if (!Files.exists(this.dirPath)) {
            try {
                Files.createDirectories(this.dirPath);
            } catch (IOException e) {
                logger.error("Failed to create directory", e);
            }
        }
    }

    // creates contact file
    public boolean createContactFile(ContactModel contactModel) {
        Path filePath = dirPath.resolve(contactModel.getID());
        List<String> contactFields = Arrays.asList(
                contactModel.getName(),
                contactModel.getEmail(),
                contactModel.getPhoneNumber());

        // create and write to file
        try {
            Files.write(filePath, contactFields, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Failed to create file", e);
            return false;
        }
        return true;
    }

    public ContactModel getContactFile(String id) {
        Path filePath = dirPath.resolve(id);
        // check if id exists
        ContactModel contactModel = new ContactModel();
        try {
            List<String> contactFields = Files.readAllLines(filePath);
            contactModel.setName(contactFields.get(0));
            contactModel.setEmail(contactFields.get(1));
            contactModel.setPhoneNumber(contactFields.get(2));
        } catch (IOException e) {
            logger.error("Failed to read file", e);
            return null;
        }
        return contactModel;
    }
}