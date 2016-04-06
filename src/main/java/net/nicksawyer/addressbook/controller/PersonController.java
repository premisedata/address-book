package net.nicksawyer.addressbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.nicksawyer.addressbook.dao.PersonRepository;
import net.nicksawyer.addressbook.model.Person;

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

    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        ResponseEntity<Person> response;
        if (id != person.getId()) {
            response = new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
            return response;
        }
        if (personRepository.findOne(id) == null) {
            response = new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
            return response;
        }
        Person updated = personRepository.save(person);
        response = new ResponseEntity<Person>(updated, HttpStatus.OK);

        return response;
    }
}
