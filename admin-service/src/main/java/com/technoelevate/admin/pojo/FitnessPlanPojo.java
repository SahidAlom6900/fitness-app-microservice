package com.technoelevate.admin.pojo;

import lombok.Data;

@Data
public class FitnessPlanPojo {
	private int planId;
	private String duration;
	private String[] activities;
}
