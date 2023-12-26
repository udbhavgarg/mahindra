package com.mahindrafinance.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mahindra.response.ErrorResponse;
import com.mahindrafinance.entity.Lead;
import com.mahindrafinance.repo.LeadRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

public class LeadServiceTest {
	@InjectMocks
	private LeadService leadService;

	@Mock
	private LeadRepository leadRepository;

	public void testCreateLead_LeadIdExists() {
		Lead sampleLead = new Lead();
		sampleLead.setLeadId(5678L);
		sampleLead.setFirstName("Vineet");
		sampleLead.setMiddleName("");
		sampleLead.setLastName("KV");
		sampleLead.setMobileNumber("8877887788");
		sampleLead.setGender("Male");
		sampleLead.setDob("dd/mm/yyyy");
		sampleLead.setEmail("v@gmail.com");

		Mockito.when(leadRepository.existsById(sampleLead.getLeadId())).thenReturn(true);
		ResponseEntity<?> response = leadService.createLead(sampleLead);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		if (response.getBody() instanceof ErrorResponse) {
			ErrorResponse errorResponse = (ErrorResponse) response.getBody();
			assertEquals("error", errorResponse.getStatus());
			assertNotNull(errorResponse.getErrorResponse());
			assertEquals("E10010", errorResponse.getErrorResponse().getCode());
			assertEquals(1, errorResponse.getErrorResponse().getMessages().size());
			assertEquals("Lead Already Exists in the database with the lead id",
					errorResponse.getErrorResponse().getMessages().get(0));
		} else {
			fail("Unexpected response type");
		}
	}

	@Test
	public void testGetLeadsByMobileNumber_ValidMobileNumber() {
		String mobileNumber = "8877887788";

		Mockito.when(leadRepository.findByMobileNumber(mobileNumber)).thenReturn(new Lead());

		ResponseEntity<?> response = leadService.getLeadsByMobileNumber(mobileNumber);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());

		if (response.getBody() instanceof Map) {
			Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
			assertEquals("success", responseBody.get("status"));
			assertNotNull(responseBody.get("data"));
		} else {
			fail("Unexpected response type");
		}
	}

	@Test
	public void testGetLeadsByMobileNumber_InvalidMobileNumber() {
		String mobileNumber = "123456789";
		ResponseEntity<?> response = leadService.getLeadsByMobileNumber(mobileNumber);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());

		if (response.getBody() instanceof ErrorResponse) {
			ErrorResponse errorResponse = (ErrorResponse) response.getBody();
			assertEquals("error", errorResponse.getStatus());
			assertNotNull(errorResponse.getErrorResponse());
			assertEquals("E1002", errorResponse.getErrorResponse().getCode());
			assertEquals(1, errorResponse.getErrorResponse().getMessages().size());
			assertEquals("Invalid Mobile No", errorResponse.getErrorResponse().getMessages().get(0));
		} else {
			fail("Unexpected response type");
		}
	}
}
