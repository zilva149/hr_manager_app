package com.example.demo.department;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
		super();
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
	}

	public List<Department> getDepartments() {
		return departmentRepository.findAll();
	}

	public void addNewDepartment(Department department) {
		Optional<Employee> managerOptional = employeeRepository.findById(department.getManager_id().getEmployee_id());
		
		if (!managerOptional.isPresent()) {
			throw new IllegalStateException("Employee with id " + department.getManager_id().getEmployee_id() + " does not exist. A manager must be an existing employee.");
		}
		
		departmentRepository.save(department);
	}

	public void deleteDepartment(Long departmentId) {
		boolean exists = departmentRepository.existsById(departmentId);
		
		if(!exists) {
			throw new IllegalStateException("Department with id " + departmentId + " does not exist");
		}
		
		departmentRepository.deleteById(departmentId);
	}

	@Transactional
	public void updateDepartment(Long departmentId, String department_name, Long manager_id) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new IllegalStateException("Department with id " + departmentId + " does not exist"));
		
		Employee manager = employeeRepository.findById(manager_id)
				.orElseThrow(() -> new IllegalStateException("Employee with id " + manager_id + " does not exist. A manager must be an existing employee."));
		
		if (department_name != null &&
				department_name.length() > 0 &&
				!Objects.equals(department.getDepartment_name(), department_name)) {
			department.setDepartment_name(department_name.trim());
		}
		
		
		if (!Objects.equals(department.getManager_id().getEmployee_id(), manager_id)) {
			department.setManager_id(manager);
		}
	}
}
