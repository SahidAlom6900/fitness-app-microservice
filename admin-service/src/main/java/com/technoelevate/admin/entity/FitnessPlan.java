package com.technoelevate.admin.entity;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.technoelevate.admin.converter.StringArrayConverter;

import lombok.Data;
@SuppressWarnings("serial")
@Data
@Entity
public class FitnessPlan implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int planId;
	private String duration;
	@Convert(converter = StringArrayConverter.class)
	private String[] activities;

}
