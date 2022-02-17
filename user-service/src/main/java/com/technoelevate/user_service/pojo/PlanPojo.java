package com.technoelevate.user_service.pojo;

import javax.persistence.Convert;

import com.technoelevate.user_service.converter.StringArrayConverter;

import lombok.Data;

@Data
public class PlanPojo {
	private String duration;
	@Convert(converter = StringArrayConverter.class)
	private String[] activities;
}
