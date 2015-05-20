package org.jeeframework.cyberstar.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_department")
public class Department implements Serializable {

	private static final long serialVersionUID = 5210336364641679435L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 上一级部门
	 */
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Department parent;
	/**
	 * 排序
	 */
	private String pos;
	/**
	 * 编号
	 */
	private String deptNo;
	/**
	 * 
	 */
	private String lft;
	/**
	 * 
	 */
	private String rqt;
	/**
	 * 等级
	 */
	private Integer lvl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getParent() {
		if (parent == null) {
			return new Department();
		}
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getLft() {
		return lft;
	}

	public void setLft(String lft) {
		this.lft = lft;
	}

	public String getRqt() {
		return rqt;
	}

	public void setRqt(String rqt) {
		this.rqt = rqt;
	}

	public Integer getLvl() {
		return lvl;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

}
