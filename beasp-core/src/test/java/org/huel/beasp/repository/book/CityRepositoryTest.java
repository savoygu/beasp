package org.huel.beasp.repository.book;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.huel.beasp.entity.common.City;
import org.huel.beasp.entity.common.School;
import org.huel.beasp.service.common.CityService;
import org.huel.beasp.service.common.SchoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CityRepositoryTest {
	@Autowired private CityService cityService;
	@Autowired private SchoolService schoolService;
	@Autowired private DataSource dataSource;
	
	@Test
	public void testGetSchoolByCityId() {
		List<School>  schools = schoolService.getSchoolsByCityId(1);
		for(School sh : schools) {
			System.out.println(sh.getId() + "------" + sh.getName());
		}
	}
	
	@Test
	public void testGetByCityId() {
		City city = cityService.getById(1);
		System.out.println(city);
	}
	
	@Test
	public void testGetCities() {
		List<City> cities = cityService.getCitiesByProvinceId(5);
		for(City city : cities) {
			System.out.println(city.getName()+"----"+city.getId());
		}
	}
	
	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}

}
