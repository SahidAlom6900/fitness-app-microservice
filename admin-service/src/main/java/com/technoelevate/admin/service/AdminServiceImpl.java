package com.technoelevate.admin.service;

import static com.technoelevate.admin.common.AdminConstants.ROLE_SERVICE;
import static com.technoelevate.admin.common.AdminConstants.ROLE_SERVICE_FALLBACK_MESSAGE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.admin.entity.Admin;
import com.technoelevate.admin.entity.Coach;
import com.technoelevate.admin.entity.FitnessPlan;
import com.technoelevate.admin.entity.Transformation;
import com.technoelevate.admin.exceprion.FallBackException;
import com.technoelevate.admin.exceprion.FileNotFoundException;
import com.technoelevate.admin.pojo.AdminAllDetailsPojo;
import com.technoelevate.admin.pojo.AdminPojo;
import com.technoelevate.admin.pojo.CoachPojo;
import com.technoelevate.admin.pojo.FitnessPlanPojo;
import com.technoelevate.admin.pojo.TransformationPojo;
import com.technoelevate.admin.repository.AdminRepository;
import com.technoelevate.admin.repository.CoachRepository;
import com.technoelevate.admin.repository.FitnessPlanRepository;
import com.technoelevate.admin.repository.TransformationRepository;
import com.technoelevate.admin.response.ResponseMessage;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Transactional
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private CoachRepository coachRepository;
	@Autowired
	private FitnessPlanRepository fitnessPlanRepository;
	@Autowired
	private TransformationRepository transRepository;
	@Autowired
	private ObjectMapper mappper;
	@Autowired
	private RestTemplate restTemplate;

	private Integer adminId = 1;

	@Value("${file.upload.location:C:\\fitness}")
	private String dir;

	private Path getPath(String fileName) {
		return Paths.get(this.dir + "\\" + fileName).toAbsolutePath().normalize();
	}

	@CircuitBreaker(name = ROLE_SERVICE, fallbackMethod = "roleFallBack")
	@Override
	public AdminPojo addAdmin(AdminPojo adminPojo) {
		String url = "http://role-service/api/v1/role/" + "ADMIN";
		Admin admin = new Admin();
		ResponseMessage responseMessage = restTemplate.getForObject(url, ResponseMessage.class);
		if (responseMessage == null) {
			return null;
		}
		Integer roleId = (Integer) responseMessage.getData();
		BeanUtils.copyProperties(adminPojo, admin);
		admin.setRoleId(roleId);
		Admin admin2 = adminRepository.save(admin);
		BeanUtils.copyProperties(admin2, adminPojo);
		return adminPojo;
	}

	@Override
	public CoachPojo addCoach(MultipartFile multipartFile, String data) {
		CoachPojo coachPojo;
		try {
			coachPojo = mappper.readValue(data, CoachPojo.class);
			Coach coach = new Coach();
			BeanUtils.copyProperties(coachPojo, coach);
			String filename = addFile("Coach", multipartFile);
			coach.setImagePath(filename);
			Coach coach2 = coachRepository.save(coach);
			BeanUtils.copyProperties(coach2, coachPojo);
			return coachPojo;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FitnessPlanPojo addPlan(FitnessPlanPojo fitnessPlanPojo) {
		FitnessPlan fitnessPlan = new FitnessPlan();
		BeanUtils.copyProperties(fitnessPlanPojo, fitnessPlan);
		FitnessPlan fitnessPlan2 = fitnessPlanRepository.save(fitnessPlan);
		BeanUtils.copyProperties(fitnessPlan2, fitnessPlanPojo);
		return fitnessPlanPojo;
	}

	@Override
	public TransformationPojo addTransformation(MultipartFile multipartFile, String data) {
		TransformationPojo transformationPojo;
		try {
			transformationPojo = mappper.readValue(data, TransformationPojo.class);
			Transformation transformation = new Transformation();
			BeanUtils.copyProperties(transformationPojo, transformation);
			List<Coach> coachs = transformation.getCoachs().stream()
					.map(coach -> coachRepository.findByCoachId(coach.getCoachId())).collect(Collectors.toList());
			String videoPath = addFile("Transformation", multipartFile);
			transformation.setVideoPath(videoPath);
			transformation.setCoachs(coachs);
			Transformation transformation2 = transRepository.save(transformation);
			BeanUtils.copyProperties(transformation2, transformationPojo);
			return transformationPojo;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String addFile(String folderName, MultipartFile multipartFile) {
		try {
			Path dirLocation = getPath(folderName);
			if (multipartFile != null) {
				Files.createDirectories(dirLocation);
				String filename = multipartFile.getOriginalFilename();
				Path filePath = dirLocation.resolve(filename);
				Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				return filePath.toString();
			} else {
				throw new FileNotFoundException("");
			}
		} catch (IOException | FileNotFoundException exception) {
			throw new FileNotFoundException(exception.getMessage());
		}
	}

	@Override
	public AdminAllDetailsPojo fetchAdminDetails() {
		Admin admin = adminRepository.findByAdminId(adminId);
		List<Coach> coachs = coachRepository.findAll();
		List<FitnessPlan> plans = fitnessPlanRepository.findAll();
		List<Transformation> transforms = transRepository.findAll();
		if (admin == null)
			return null;
		AdminAllDetailsPojo adminPojo = new AdminAllDetailsPojo();
		adminPojo.setAdminName(admin.getAdminName());
		adminPojo.setCoachs(coachs);
		adminPojo.setFitnessPlans(plans);
		adminPojo.setTransformations(transforms);
		return adminPojo;
	}

	@Override
	public CoachPojo getCoach(int coachId) {
		Coach coach = coachRepository.findByCoachId(coachId);
		CoachPojo coachPojo = new CoachPojo();
		BeanUtils.copyProperties(coach, coachPojo);
		return coachPojo;
	}

	@Override
	public FitnessPlanPojo getPlan(int planId) {
		FitnessPlan plan = fitnessPlanRepository.findByPlanId(planId);
		if (plan == null) {
			return null;
		}
		FitnessPlanPojo fitnessPlanPojo = new FitnessPlanPojo();
		BeanUtils.copyProperties(plan, fitnessPlanPojo);
		return fitnessPlanPojo;
	}

	@Override
	public List<CoachPojo> getCoachs(List<Integer> coachIds) {
		List<CoachPojo> coachPojos = new ArrayList<>();
		coachIds.stream().forEach(coachId -> {
			Coach coach = coachRepository.findByCoachId(coachId);
			CoachPojo coachPojo = new CoachPojo();
			BeanUtils.copyProperties(coach, coachPojo);
			coachPojos.add(coachPojo);
		});
		return coachPojos;
	}

	public AdminPojo roleFallBack(Exception exception) {
		throw new FallBackException(ROLE_SERVICE_FALLBACK_MESSAGE);
	}
}
