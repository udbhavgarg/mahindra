package com.mahindrafinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahindrafinance.entity.Lead;
import com.mahindrafinance.service.LeadService;

import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {

	@Autowired
	LeadService leadService;

	@PutMapping("/create")
	public ResponseEntity<?> createLead(@RequestBody Lead lead) {
		return leadService.createLead(lead);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		ResponseEntity<?> response = leadService.getLeadById(id);
		return response;
	}

	@GetMapping("/findByMobileNumber/{mobileNumber}")
	public ResponseEntity<?> getLeadsByMobileNumber(@PathVariable String mobileNumber) {
		return leadService.getLeadsByMobileNumber(mobileNumber);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return leadService.deleteLead(id);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Lead>> getAllLeads() {
		return leadService.getAll();
	}
}
