package com.cg.shopping.orderservice.dao;

import com.cg.shopping.orderservice.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByCustomerId(int customerId);
}
