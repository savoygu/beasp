package org.huel.beasp.repository.common;

import org.huel.beasp.entity.common.Province;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 省持久层
 * @author 001
 *
 */
public interface ProvinceRepository extends JpaRepository<Province, Integer>{
	public Province getById(Integer id);
	public Province getByName(String name);
}
