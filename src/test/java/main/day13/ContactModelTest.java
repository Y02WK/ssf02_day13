package main.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import main.day13.models.ContactModel;

@TestInstance(Lifecycle.PER_CLASS)
public class ContactModelTest {

    private ContactModel model;

    @BeforeAll
    public void setUp() {
        model = new ContactModel();
        model.setName("Kin");
        model.setEmail("Kin@Kin.com");
        model.setPhoneNumber("12345678");
    }

    @AfterAll
    public void tearDown() {
        model = null;
    }

    @Test
    public void testName() {
        // model = new ContactModel();
        // model.setName("Kin");
        assertEquals(model.getName(), "Kin");
    }

    @Test
    public void testEmail() {
        // model = new ContactModel();
        // model.setEmail("Kin@Kin.com");
        assertEquals(model.getEmail(), "Kin@Kin.com");
    }

    @Test
    public void testPhoneNumber() {
        // model = new ContactModel();
        // model.setPhoneNumber("12345678");
        assertEquals(model.getPhoneNumber(), "12345678");
    }
}
