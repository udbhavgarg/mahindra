package com.mahindrafinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mahindra.response.ErrorDetails;
import com.mahindra.response.ErrorResponse;
import com.mahindra.response.SuccessResponse;
import com.mahindrafinance.entity.Lead;
import com.mahindrafinance.exception.LeadException;
import com.mahindrafinance.repo.LeadRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LeadService {

	@Autowired
	LeadRepository leadRepository;

	public ResponseEntity<?> createLead(Lead lead) throws LeadException {

		boolean isLeadUnique = isLeadIdUnique(lead.getLeadId());
		if (!isLeadUnique) {
			String errorCode = "E10010";
			List<String> errorMessages = List.of("Lead Already Exists in the database with the lead id");
			ErrorDetails errorDetails = new ErrorDetails(errorCode, errorMessages);
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		ResponseEntity<?> validationResponse = validateLeadData(lead);

		if (validationResponse != null) {
			return validationResponse;
		}
		try {
			leadRepository.save(lead);
			SuccessResponse successResponse = new SuccessResponse("Created Leads Successfully");
			return ResponseEntity.ok().body(successResponse);
		} catch (Exception e) {
			String errorCode = "E100XX";
			List<String> errorMessages = List.of("Unexpected error occurred while creating lead");
			ErrorDetails errorDetails = new ErrorDetails(errorCode, errorMessages);
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

	}

	private boolean isLeadIdUnique(Long leadId) {
		return !leadRepository.existsById(leadId);
	}

	private ResponseEntity<?> validateLeadData(Lead lead) {
		List<String> errorMessages = new ArrayList<>();

		if (lead.getLeadId() == null || lead.getLeadId() <= 0) {
			errorMessages.add("Lead ID must be a unique positive integer.");
		}

		if (lead.getFirstName() == null || !lead.getFirstName().matches("^[A-Za-z]+$")) {
			errorMessages.add("First name must contain only alphabets and is mandatory.");
		}

		if (lead.getMiddleName() != null && !lead.getMiddleName().matches("^[A-Za-z]*$")) {
			errorMessages.add("Middle name must contain only alphabets.");
		}

		if (lead.getLastName() == null || !lead.getLastName().matches("^[A-Za-z]+$")) {
			errorMessages.add("Last name must contain only alphabets and is mandatory.");
		}

		if (lead.getMobileNumber() == null || !lead.getMobileNumber().matches("^[6-9]\\d{9}$")) {
			errorMessages.add("Mobile number must be a valid 10-digit number starting from 6 to 9.");
		}

		if (lead.getEmail() == null || !lead.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			errorMessages.add("Invalid email format.");
		}

		if (lead.getGender() == null || !lead.getGender().matches("^(Male|Female|Others)$")) {
			errorMessages.add("Invalid gender. Gender should be Male, Female, or Others.");
		}

		if (lead.getDob() == null || !lead.getDob().matches("^\\d{2}/\\d{2}/\\d{4}$")) {
			errorMessages.add("Date of Birth must be in the format dd/mm/yyyy and is mandatory.");
		}

		if (!errorMessages.isEmpty()) {
			ErrorDetails errorDetails = new ErrorDetails("E1001", errorMessages);
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		return null;
	}

	public ResponseEntity<List<Lead>> getAll() {
		return (ResponseEntity<List<Lead>>) ResponseEntity.ok(leadRepository.findAll());
	}

	public ResponseEntity<?> getLeadsByMobileNumber(String mobileNumber) {
		if (!mobileNumber.matches("^[6-9]\\d{9}$")) {
			String errorCode = "E1002";
			List<String> errorMessages = List.of("Invalid Mobile No");
			ErrorDetails errorDetails = new ErrorDetails(errorCode, errorMessages);
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		Lead lead = leadRepository.findByMobileNumber(mobileNumber);
		if (lead == null) {
			String errorCode = "E1003";
			List<String> errorMessages = List.of("No lead found to this mobile no");
			ErrorDetails errorDetails = new ErrorDetails(errorCode, errorMessages);
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status", "success");
		responseBody.put("data", lead);

		return ResponseEntity.ok(responseBody);

	}

	public ResponseEntity<?> getLeadById(Long id) {
		Optional<Lead> leadOptional = leadRepository.findById(id);
		if (leadOptional.isPresent()) {
			Lead lead = leadOptional.get();
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("status", "success");
			responseBody.put("data", lead);
			return ResponseEntity.ok(responseBody);
		} else {
			String errorCode = "E1004";
			List<String> errorMessages = List.of("Lead not found");
			ErrorDetails errorDetails = new ErrorDetails(errorCode, errorMessages);
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	public ResponseEntity<?> deleteLead(Long id) {
		Optional<Lead> leadOptional = leadRepository.findById(id);
		if (leadOptional.isPresent()) {
			Lead lead = leadOptional.get();
			leadRepository.delete(lead);
			SuccessResponse successResponse = new SuccessResponse("Lead deleted successfully");
			return ResponseEntity.ok(successResponse);
		} else {
			String errorCode = "E1005";
			List<String> errorMessages = List.of("Lead not found");
			ErrorDetails errorDetails = new ErrorDetails(errorCode, errorMessages);
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}