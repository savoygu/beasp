package org.huel.beasp.service.common;

import java.util.List;

import org.huel.beasp.entity.common.School;
import org.huel.beasp.repository.common.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("schoolService")
public class SchoolService {
	@Autowired private SchoolRepository schoolRepository;
	
	/**
	 * 获取学校
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public School getById(Integer id) {
		return schoolRepository.getById(id);
	}
	
	/**
	 * 获取学校
	 * @param name
	 * @return
	 */
	@Transactional(readOnly=true)
	public School getBySchoolName(String name) {
		return schoolRepository.getByName(name);
	}
	
	/**
	 * 获取指定市 id 的学校
	 * @param cityId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<School> getSchoolsByCityId(Integer cityId) {
		return schoolRepository.getSchoolsByCityId(cityId);
	}
}
