package com.mahindrafinance.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leads")
public class Lead {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leadId;

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = false)
	private String firstName;

	@Column
	private String middleName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String mobileNumber;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private String dob;

	@Column(nullable = false)
	private String email;

}
