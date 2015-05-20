package org.jeeframework.cyberstar.web.actions.department;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.jeeframework.core.service.IGenericService;
import org.jeeframework.core.struts2.action.hibernate.ServiceBaseManageAction;
import org.jeeframework.core.struts2.utils.Struts2Utils;
import org.jeeframework.cyberstar.entity.Department;
import org.jeeframework.cyberstar.service.DepartmentService;

public class DepartmentAction extends ServiceBaseManageAction<Department, Long> {
	private static final long serialVersionUID = -3961634515859025776L;

	@Resource
	private DepartmentService departmentService;
	private Department department;
	private String name;

	@Override
	public IGenericService<Department, Long> getGenericService() {
		return departmentService;
	}

	public Department getModel() {
		return department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void prepare() throws Exception {
		if (getRequestId() == null || getRequestId() == 0) {
			department = new Department();
		} else {
			//department = departmentService.getEntityById(getRequestId());
		}

	}

	@Override
	public String execute() throws Exception {
		System.out.println("excute");
		return SUCCESS;
	}

	@Action(value = "department", results = { @Result(name = "success", location = "/department/ztreeTest.jsp"), @Result(name = "error", location = "/department/ztreeTest.jsp") })
	public String getNode() throws Exception {

		/**填充数据测试
		    List<Department> dept = departmentService.getNodes();
		    Department d1 = new Department();
			Department d2 = new Department();
			Department d3 = new Department();
			d3.setId(388832l);
			d3.setName("部门1");
			d1.setId(669988l);
			d1.setName("子节点1");
			d1.setParent(d3);
			d2.setId(455888l);
			d2.setName("子节点2");
			d2.setParent(d3);
			dept.add(d3);
			dept.add(d1);
			dept.add(d2);
		 **/
		JSONArray array = departmentService.getAllNodes(departmentService.getNodes());
		Struts2Utils.renderJson(array);
		return "success";
	}

	public String addNode() throws Exception {
		/**父 **/
		Department dept = new Department();
		dept.setId(getRequestId());
		/**子**/
		department = new Department();
		department.setName(getName());
		department.setParent(dept);

		departmentService.saveOrUpdate(department);
		JSONObject obj = new JSONObject();
		obj.put("nodeId", department.getId());
		Struts2Utils.renderJson(obj);
		return "success";
	}

	public String editNode() throws Exception {
		department = departmentService.get(getRequestId());
		department.setName(getName());
		departmentService.update(department);
		return "success";
	}

	public String delNode() throws Exception {
		/** 失败的情况下返回0表示处理失败，即不删除节点**/
		JSONObject obj = new JSONObject();
		if (!departmentService.findByParentId(getRequestId())) {
			obj.put("result", 0);
		} else {
			department = departmentService.get(getRequestId());
			departmentService.delete(department);
			obj.put("result", 1);
		}
		Struts2Utils.renderJson(obj);
		return "success";
	}
}
