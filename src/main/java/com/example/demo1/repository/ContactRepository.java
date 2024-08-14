package com.example.demo1.repository;

import com.example.demo1.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findById(Integer id);
}
