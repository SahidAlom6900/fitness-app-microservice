package com.technoelevate.user_service.response;

import java.util.List;

import com.technoelevate.user_service.pojo.CoachPojo2;

import lombok.Data;

@Data
public class CoachResponse {
	private List<CoachPojo2> data;
}
