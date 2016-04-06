package net.nicksawyer.addressbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.nicksawyer.addressbook.dao.AddressRepository;
import net.nicksawyer.addressbook.model.Address;

@RestController
public class AddressController {
    @Autowired
    AddressRepository addressRepository;

    @RequestMapping("/address")
    public Iterable<Address> listAddresses() {
        return addressRepository.findAll();
    }

    @RequestMapping("/address/{id}")
    public Address getAddress(@PathVariable int id) {
        return addressRepository.findOne(id);
    }
}
