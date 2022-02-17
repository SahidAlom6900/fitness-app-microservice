package com.technoelevate.user_service.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technoelevate.user_service.converter.StringListConverter;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "user_details")
@JsonIgnoreProperties({ "password" })
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String userName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Kolkata")
	private LocalDate dob;
	private String email;
	private long phoneNumber;
	private String address;
	private String password;
	private String gender;
	private int steps;
	private int coins;

	@Convert(converter = StringListConverter.class)
	private List<String> coachId;

	private int planId;
	
	private int roleId;
}
