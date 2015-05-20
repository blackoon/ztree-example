package org.jeeframework.cyberstar.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeeframework.core.service.impl.AbstractService;
import org.jeeframework.cyberstar.dao.IDepartmentDAO;
import org.jeeframework.cyberstar.entity.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends AbstractService<Department, Long, IDepartmentDAO> {

	private static final Log log = LogFactory.getLog(DepartmentService.class);

	@Resource
	private IDepartmentDAO departmentDAO;

	@Override
	public IDepartmentDAO getGenericDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(IDepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	/**
	 * 获取节点信息
	 * @param depts
	 * @return
	 */
	public JSONArray getAllNodes(List<Department> depts) {

		JSONArray arr = new JSONArray();
		Long id;
		for (Department dept : depts) {
			JSONObject json = new JSONObject();
			id = dept.getId();
			json.put("id", id);
			if (dept.getParent().getId() != null)
				json.put("pId", dept.getParent().getId());
			else
				json.put("pId", 0);
			json.put("name", dept.getName());

			arr.add(json);

		}
		return arr;
	}

	public Department getEntityById(Long entityId) {
		return departmentDAO.get(entityId);
	}

	@Override
	public Department saveOrUpdate(Department entity) {
		return departmentDAO.saveOrUpdate(entity);
	}

	@Override
	public void delete(Department entity) {
		// TODO Auto-generated method stub
		departmentDAO.delete(entity);
	}

	public List<Department> getNodes() {
		return departmentDAO.getNodes();
	}

	public List<Department> findAll() {
		return departmentDAO.findAll();
	}

	public boolean findByParentId(Long entityId) {
		return departmentDAO.findByParentId(entityId);
	}

}
