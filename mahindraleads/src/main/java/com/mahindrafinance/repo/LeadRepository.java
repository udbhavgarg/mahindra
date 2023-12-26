package com.mahindrafinance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahindrafinance.entity.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
	Lead findByMobileNumber(String mobileNumber);
}