package com.karatesan.BasicSpringSecurity.repositories;

import java.util.List;


import com.karatesan.BasicSpringSecurity.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(int customerId);

}
