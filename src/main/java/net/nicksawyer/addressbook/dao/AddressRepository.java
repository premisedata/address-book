package net.nicksawyer.addressbook.dao;

import org.springframework.data.repository.CrudRepository;

import net.nicksawyer.addressbook.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
