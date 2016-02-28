package org.huel.beasp.service.common;

import java.util.List;

import org.huel.beasp.entity.common.Province;
import org.huel.beasp.repository.common.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 省业务逻辑层
 * @author 001
 *
 */
@Service("provinceService")
public class ProvinceService {
	
	@Autowired private ProvinceRepository provinceRepository;
	
	/**
	 * 按 provinceId 获取所在区域
	 * @param areaId
	 * @return
	 */
	@Transactional(readOnly=true)
	public Province getByProvinceId(Integer provinceId) {
		return provinceRepository.getById(provinceId);
	}
	
	/**
	 * 按 provinceName 获取所在区域
	 * @param name
	 * @return
	 */
	@Transactional(readOnly=true)
	public Province getByProvinceName(String name) {
		return provinceRepository.getByName(name);
	}
	
	/**
	 * 获取所有省份
	 * @return
	 */
	@Transactional
	public List<Province> getAll() {
		return provinceRepository.findAll();
	}
}
