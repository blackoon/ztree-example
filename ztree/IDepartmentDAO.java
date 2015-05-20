package org.jeeframework.cyberstar.dao;

import java.util.List;

import org.jeeframework.core.dao.IGenericDAO;
import org.jeeframework.cyberstar.entity.Department;

public interface IDepartmentDAO extends IGenericDAO<Department, Long> {

	/**
	 * 查询部门节点
	 * @return list
	 */
	public List<Department> getNodes();

	/**
	 * 查询所有部门信息
	 * @return list
	 */
	public List<Department> findAll();

	/**
	 * 保存或修改部门信息
	 * @return 
	 */
	public Department saveOrUpdate(Department dept);

	/**
	 * 删除部门信息
	 */
	public void delete(Department dept);

	/**
	 * 根据ID获得一个实体
	 * @param entityId
	 * @return
	 */
	public Department getEntityById(Long entityId);

	/**
	 * 根据parent_id查询有无已存在实体
	 * @param entityId
	 * @return
	 */
	public boolean findByParentId(Long entityId);
}