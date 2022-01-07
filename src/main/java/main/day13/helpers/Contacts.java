package main.day13.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
    private Set<String> generatedIDs = new HashSet<String>();

    @Autowired
    public Contacts(ApplicationArguments args) {
        this.dirPath = validateArgs(args);
        checkDirectory();
        addIDtoSet();
    }

    // args processing
    private Path validateArgs(ApplicationArguments args) {
        List<String> dataDir = args.getOptionValues("dataDir");
        if (dataDir == null || dataDir.isEmpty()) {
            System.err.println("No data directory specified. Exiting.");
            System.exit(1);
        } else {
            logger.info(dataDir.get(0));
            return Path.of(dataDir.get(0));
        }
        return Path.of("");
    }

    // checks and create if directory does not exist
    private void checkDirectory() {
        // if (!Files.exists(this.dirPath)) {
        // try {
        // Files.createDirectories(this.dirPath);
        // } catch (IOException e) {
        // logger.error("Failed to create directory", e);
        // }
        // }
        File dir = this.dirPath.toFile();
        if (dir.mkdirs()) {
            return;
        } else {
            logger.error("Failed to create directory");
        }
    }

    // add all file names to set
    private void addIDtoSet() {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(this.dirPath)) {
            for (Path p : ds) {
                this.generatedIDs.add(p.toString());
            }
        } catch (IOException e) {
            logger.error("Failed to read from directory.", e);
        }
    }

    // returns a randomly generated 8-digit hex value and adds it to a set
    private String generateRandomId() {
        Random random = new Random();
        String generatedID;

        do {
            generatedID = Integer.toHexString(random.nextInt());
        } while (!this.generatedIDs.add(generatedID));
        return generatedID;
    }

    // creates contact file
    public boolean createContactFile(List<String> contactFields) {
        String contactID = generateRandomId();
        Path filePath = dirPath.resolve(contactID);

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
        if (!Files.exists(filePath)) {
            return null;
        } else {
            ContactModel contactModel = new ContactModel();
            try {
                List<String> contactFields = Files.readAllLines(filePath);
                contactModel.setName(contactFields.get(0));
                contactModel.setEmail(contactFields.get(1));
                contactModel.setPhoneNumber(contactFields.get(2));
            } catch (IOException e) {
                logger.error("Failed to read file", e);
            }
            return contactModel;
        }

    }

}