package com.technoelevate.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.admin.pojo.AdminAllDetailsPojo;
import com.technoelevate.admin.pojo.AdminPojo;
import com.technoelevate.admin.pojo.CoachPojo;
import com.technoelevate.admin.pojo.FitnessPlanPojo;
import com.technoelevate.admin.pojo.TransformationPojo;

public interface AdminService {
	AdminPojo addAdmin(AdminPojo adminPojo);

	AdminAllDetailsPojo fetchAdminDetails();

	CoachPojo addCoach(MultipartFile multipartFile, String data);

	FitnessPlanPojo addPlan(FitnessPlanPojo adminPojo);

	CoachPojo getCoach(int coachId);

	FitnessPlanPojo getPlan(int planId);

	TransformationPojo addTransformation(MultipartFile multipartFile, String data);

	List<CoachPojo> getCoachs(List<Integer> coachIds);
}
