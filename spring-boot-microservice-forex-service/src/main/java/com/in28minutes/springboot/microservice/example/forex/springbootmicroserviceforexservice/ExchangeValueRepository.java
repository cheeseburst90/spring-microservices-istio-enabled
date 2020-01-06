
package com.in28minutes.springboot.microservice.example.forex.springbootmicroserviceforexservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
	//@Query("SELECT val.id, val.from, val.to, val.conversionMultiple, val.port FROM ExchangeValue val  WHERE val.from=(:pFrom) AND val.to= (:pTo)")
	ExchangeValue findByFromAndTo(String from, String to);

}
