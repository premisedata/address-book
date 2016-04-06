package net.nicksawyer.addressbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import net.nicksawyer.addressbook.dao.PersonRepository;
import net.nicksawyer.addressbook.model.Person;

import java.util.Date;

@RestController
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/person")
    public Iterable<Person> listPeople() {
        return personRepository.findByExpiresAtIsNull();
    }

    @RequestMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        ResponseEntity<Person> response;
        Person person = personRepository.findOneByIdAndExpiresAtIsNull(id);
        if (person == null) {
            response = new ResponseEntity<Person>(person, HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<Person>(person, HttpStatus.OK);
        }

        return response;
    }

    // TODO: Determine if transaction should be broken out into its own method
    @Transactional
    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        ResponseEntity<Person> response;
        if (id != person.getId()) {
            // Mismatched IDs between URL and payload
            response = new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
            return response;
        }
        Person existing = personRepository.findOne(id);
        if (existing == null) {
            // This is an invalid ID
            response = new ResponseEntity<Person>(existing, HttpStatus.BAD_REQUEST);
            return response;
        }
        if (existing.getExpiresAt() != null) {
            // The corresponding object's expiresAt field is not null
            response = new ResponseEntity<Person>(existing, HttpStatus.CONFLICT);
            return response;
        }
        existing.setExpiresAt(new Date());
        personRepository.save(existing);
        person.setId(null);
        Person updated = personRepository.save(person);
        response = new ResponseEntity<Person>(updated, HttpStatus.OK);

        return response;
    }
}
