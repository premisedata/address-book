package net.nicksawyer.addressbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.nicksawyer.addressbook.dao.PersonRepository;
import net.nicksawyer.addressbook.model.Person;

@RestController
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/person")
    public Iterable<Person> listPeople() {
        return personRepository.findAll();
    }

    @RequestMapping("/person/{id}")
    public Person getPerson(@PathVariable int id) {
        return personRepository.findOne(id);
    }
}
