package com.karatesan.BasicSpringSecurity.repositories;

import com.karatesan.BasicSpringSecurity.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
