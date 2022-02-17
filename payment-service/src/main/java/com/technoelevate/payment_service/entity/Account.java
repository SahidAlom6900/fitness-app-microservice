package com.technoelevate.payment_service.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "account_details")
@JsonIgnoreProperties({ "password" })
public class Account implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	
	private long accountNumber;
	
	private String userName;
	
	private String password;
	
	private double blance;
}
