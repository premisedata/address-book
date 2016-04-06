package net.nicksawyer.addressbook.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="person")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Person {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="dateOfBirth")
    private Date dateOfBirth;
    @Column(name="expiresAt")
    private Date expiresAt;
    // TODO: Can't get this to work, so settling for an alternative solution
    // See: https://en.wikibooks.org/wiki/Java_Persistence/OneToOne#Example_of_simulating_a_OneToOne_using_a_OneToMany_JoinTable
//    @OneToOne
//    @JoinTable(name="person_address",
//               joinColumns=@JoinColumn(name="address_id"),
//               inverseJoinColumns=@JoinColumn(name="person_id"))
//    private Address address;
    @OneToMany
    @JoinTable(name="person_address",
               joinColumns=@JoinColumn(name="address_id"),
               inverseJoinColumns=@JoinColumn(name="person_id"))
    private List<Address> addresses;

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    // TODO: Remove this and fix the JPA annotations above
    public Address getAddress() {
        if (this.addresses.isEmpty()) {
            return null;
        } else {
            return this.addresses.get(0);
        }
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public enum Gender {
        female,
        male
    }
}
