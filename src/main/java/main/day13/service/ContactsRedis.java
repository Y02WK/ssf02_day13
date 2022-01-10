package main.day13.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import main.day13.exceptions.ContactNotFoundException;
import main.day13.models.ContactModel;

/**
 * ContactsRedis
 */
@Component
public class ContactsRedis implements ContactsRepo {
    @Autowired
    private RedisTemplate<String, ContactModel> template;

    public void saveContact(ContactModel contactModel) {
        template.opsForValue().set(contactModel.getID(), contactModel);
    }

    public ContactModel getContact(String id) throws ContactNotFoundException {
        Optional<ContactModel> contactOpt = Optional.ofNullable(template.opsForValue().get(id));
        if (contactOpt.isPresent()) {
            return contactOpt.get();
        }
        throw new ContactNotFoundException("Contact not found");
    }
}