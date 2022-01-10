package main.day13.helpers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
        addIDstoSet();
    }

    // args processing
    private Path validateArgs(ApplicationArguments args) {
        List<String> dataDir = args.getOptionValues("dataDir");
        if (dataDir == null || dataDir.isEmpty()) {
            System.err.println("No data directory specified.");
        } else {
            logger.info(dataDir.get(0));
            return Path.of(dataDir.get(0));
        }
        return Path.of("");
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

    // add all file names to set
    private void addIDstoSet() {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(this.dirPath)) {
            for (Path p : ds) {
                this.generatedIDs.add(p.getFileName().toString());
            }
        } catch (IOException e) {
            logger.error("Failed to read from directory.", e);
        }
    }

    // optimized id generator
    private synchronized String generateID(int numchars) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        //
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(random.nextInt()));
        }

        if (!generatedIDs.add(sb.toString().substring(0, numchars))) {
            return generateID(8);
        }

        return sb.toString().substring(0, numchars);
    }

    // creates contact file
    public boolean createContactFile(ContactModel contactModel) {
        Path filePath = dirPath.resolve(generateID(8));
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
        if (this.generatedIDs.contains(id)) {
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
        return null;
    }

}