package org.huel.beasp.repository.common;

import java.util.List;

import org.huel.beasp.entity.common.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SchoolRepository extends JpaRepository<School, Integer>{
	
	@Query(value="SELECT sh_id, sh_shool FROM beasp_school s WHERE s.sh_city=?", nativeQuery=true)
	public List<School> getSchoolsByCityId(Integer cityId);
	
	public School getById(Integer id);
	
	public School getByName(String name);
}
