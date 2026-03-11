package com.billing.repository;

import com.billing.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    List<Customer> findByActive(Boolean active);
    
    @Query("SELECT c FROM Customer c WHERE "+
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR "+
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR "+
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Customer> searchCustomers(@Param("search") String search);
}