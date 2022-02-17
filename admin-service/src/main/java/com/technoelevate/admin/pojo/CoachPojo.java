package com.technoelevate.admin.pojo;

import lombok.Data;

@Data
public class CoachPojo {
	private int coachId;
	private String coachName;
	private String email;
	private long phoneNumber;
	private String[] specialization;
	private String imagePath;
}
