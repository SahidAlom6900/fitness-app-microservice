package com.technoelevate.admin.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
@SuppressWarnings("serial")
@Data
@Entity
public class Transformation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transId;
	private String duration;
	private String videoPath;
	@ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name = "transId")
	@JoinTable(name = "Transformation_Coach", joinColumns = {
			@JoinColumn(name = "transId", referencedColumnName = "transId") }, inverseJoinColumns = {
					@JoinColumn(name = "coachId", referencedColumnName = "coachId") })
	private List<Coach> coachs; 
}
