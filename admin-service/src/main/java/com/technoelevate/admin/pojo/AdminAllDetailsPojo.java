package com.technoelevate.admin.pojo;

import java.util.List;

import com.technoelevate.admin.entity.Coach;
import com.technoelevate.admin.entity.FitnessPlan;
import com.technoelevate.admin.entity.Transformation;

import lombok.Data;

@Data
public class AdminAllDetailsPojo {
	
	private String adminName;

	private List<Coach> coachs;


	private List<FitnessPlan> fitnessPlans;

	private List<Transformation> transformations;
}
