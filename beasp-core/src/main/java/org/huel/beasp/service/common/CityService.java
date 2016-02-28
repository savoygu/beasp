package org.huel.beasp.service.common;

import java.util.List;

import org.huel.beasp.entity.common.City;
import org.huel.beasp.repository.common.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cityService")
public class CityService {
	@Autowired private CityRepository cityRepository;
	
	/**
	 * 按 cityId 获取城市 
	 * @param cityId
	 * @return
	 */
	@Transactional(readOnly=true)
	public City getById(Integer cityId) {
		return cityRepository.getById(cityId);
	}
	
	/**
	 * 按 cityName 获取所在区域
	 * @param name
	 * @return
	 */
	@Transactional(readOnly=true)
	public City getByCityName(String name) {
		return cityRepository.getByName(name);
	}
	
	/**
	 * 获取指定省id 的城市
	 * @param provinceId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<City> getCitiesByProvinceId(Integer provinceId) {
		return cityRepository.getCitiesByProvinceId(provinceId);
	}
}
