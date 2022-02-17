package com.technoelevate.admin.pojo;

import java.util.List;

import com.technoelevate.admin.entity.Coach;

import lombok.Data;

@Data
public class TransformationPojo {
	private int transId;
	private String duration;
	private String videoPath;
	private List<Coach> coachs; 
}
