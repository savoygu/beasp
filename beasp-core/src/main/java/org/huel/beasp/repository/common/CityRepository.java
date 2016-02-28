package org.huel.beasp.repository.common;

import java.util.List;

import org.huel.beasp.entity.common.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * 市持久层
 * @author 001
 *
 */
public interface CityRepository extends JpaRepository<City, Integer> {
	
	@Query(value="SELECT ci_id, ci_city FROM beasp_city c WHERE c.ci_province=?", nativeQuery=true)
	public List<City> getCitiesByProvinceId(Integer provinceId);
	
	public City getById(Integer id);
	
	public City getByName(String name);
}
