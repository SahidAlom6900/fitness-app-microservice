package com.technoelevate.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.admin.pojo.AdminAllDetailsPojo;
import com.technoelevate.admin.pojo.AdminPojo;
import com.technoelevate.admin.pojo.CoachPojo;
import com.technoelevate.admin.pojo.FitnessPlanPojo;
import com.technoelevate.admin.pojo.TransformationPojo;
import com.technoelevate.admin.response.ResponseMessage;
import com.technoelevate.admin.service.AdminService;

@RestController
@RequestMapping("api/v1/admin")
public class AdminControler {
	@Autowired
	private AdminService adminService;

	@PostMapping
	public ResponseEntity<ResponseMessage> addAdmin(@RequestBody AdminPojo adminPojo) {
		AdminPojo admin = adminService.addAdmin(adminPojo);
		if (admin != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", admin), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", admin), HttpStatus.OK);
	}// end addAdmin method

	@GetMapping
	public ResponseEntity<ResponseMessage> fetchAdmin() {
		AdminAllDetailsPojo admin = adminService.fetchAdminDetails();
		if (admin != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", admin), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", admin), HttpStatus.OK);
	}// end fetchAdmin method

	@PostMapping("coach")
	public ResponseEntity<ResponseMessage> addCoach(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("data") String data) {
		CoachPojo coachs = adminService.addCoach(multipartFile, data);
		if (coachs != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", coachs), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", coachs), HttpStatus.OK);
	}// end addCoach method

	@PostMapping("plan")
	public ResponseEntity<ResponseMessage> addPlan(@RequestBody FitnessPlanPojo fitnessPlanPojo) {
		FitnessPlanPojo plans = adminService.addPlan(fitnessPlanPojo);
		if (plans != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", plans), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", plans), HttpStatus.OK);
	}// end addPlan method

	@PostMapping("transformtion")
	public ResponseEntity<ResponseMessage> addTransformation(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("data") String data) {
		TransformationPojo transformation = adminService.addTransformation(multipartFile, data);
		if (transformation != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", transformation), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", transformation), HttpStatus.OK);
	}// end addTransformtion method

	@GetMapping("coach/{coachId}")
	public ResponseEntity<ResponseMessage> getcoach(@PathVariable("coachId") int coachId) {
		CoachPojo coach = adminService.getCoach(coachId);
		if (coach != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", coach), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", coach), HttpStatus.OK);
	}// end addTransformtion method

	@GetMapping("plan/{planId}")
	public ResponseEntity<ResponseMessage> getPlan(@PathVariable("planId") int planId) {
		FitnessPlanPojo plan = adminService.getPlan(planId);
		if (plan != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", plan), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", plan), HttpStatus.OK);
	}// end addTransformtion method

	@PostMapping("coachs")
	public ResponseEntity<ResponseMessage> getCoachs(@RequestBody List<Integer> coachIds) {
		List<CoachPojo> coachPojo = adminService.getCoachs(coachIds);
		if (!coachPojo.isEmpty())
			return new ResponseEntity<>(new ResponseMessage(false, "", coachPojo), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", coachPojo), HttpStatus.OK);
	}// end getCoachs method
}
