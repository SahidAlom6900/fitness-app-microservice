package com.technoelevate.user_service.pojo;

import javax.persistence.Convert;

import com.technoelevate.user_service.converter.StringArrayConverter;

import lombok.Data;

@Data
public class CoachPojo2 {
	private String coachName;
	private String email;
	private long phoneNumber;
	@Convert(converter = StringArrayConverter.class)
	private String[] specialization;
	private String imagePath;
}
