package org.huel.beasp.entity.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 省
 * 
 * @author 001
 *
 */
@Table(name = "beasp_province")
@Entity
public class Province implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9092900053579812199L;
	/**省份id**/
	private Integer id;
	/**省份名称**/
	private String name;

	@Id @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="pr_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="pr_province", length=32, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Province other = (Province) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
