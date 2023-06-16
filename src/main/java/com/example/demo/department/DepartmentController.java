package com.example.demo.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.employee.Employee;

@RestController
@RequestMapping(path = "api/department")
public class DepartmentController {
	private final DepartmentService departmentService;

	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@GetMapping()
	public List<Department> getDepartments() {
		return departmentService.getDepartments();
	}
	
	@PostMapping
	public void registerNewDepartment(@RequestBody Department department) {
		departmentService.addNewDepartment(department);
	}
	
	@DeleteMapping(path = "{departmentId}")
	public void deleteDepartment(@PathVariable("departmentId") Long departmentId) {
		departmentService.deleteDepartment(departmentId);
	}
	
	@PutMapping(path = "{departmentId}")
	public void updateDepartment(
			@PathVariable("departmentId") Long department_id,
			@RequestParam(required = false) String department_name,
			@RequestParam(required = false) Long manager_id
			) {
		departmentService.updateDepartment(department_id, department_name, manager_id);
	}
}
