package com.zeroleaf.web.domain;

import com.zeroleaf.web.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zeroleaf on 2015/4/18.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
