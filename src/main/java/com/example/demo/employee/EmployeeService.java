package com.example.demo.employee;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	public void addNewEmployee(Employee employee) {
		if (employee.getSalary() < 22000) {
			throw new IllegalStateException("Employee salary cannot be below 22000");
		}
		
		String[] nameArr = employee.getName().split("\\s");
		
		if(nameArr.length > 1) {
			employee.setName(nameArr[0] + " " + nameArr[1].charAt(0) + ".");
		}
		
		employee.setName(employee.getName().trim());
		
		employeeRepository.save(employee);
	}

	public void deleteEmployee(Long employeeId) {
		boolean exists = employeeRepository.existsById(employeeId);
		
		if(!exists) {
			throw new IllegalStateException("Employee with id " + employeeId + " does not exist");
		}
		
		employeeRepository.deleteById(employeeId);
	}

	@Transactional
	public void updateEmployee(Long employeeId, String name, Long department_id, Long project_id, String role, Double salary) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new IllegalStateException("employee with id " + employeeId + " does not exist"));
		
		if (name != null &&
				name.length() > 0 &&
				!Objects.equals(employee.getName(), name)) {
			String[] nameArr = name.split("\\s");
			
			if(nameArr.length > 1) {
				employee.setName((nameArr[0] + " " + nameArr[1].charAt(0) + ".").trim());
			} else {
				employee.setName(name.trim());
			}
		}
		
		if (role != null &&
				role.length() > 0 &&
				!Objects.equals(employee.getRole(), role)) {
			employee.setRole(role);
		}
		
		if (salary != null &&
				salary > 0 &&
				!Objects.equals(employee.getSalary(), salary)) {
			if (salary < 22000) {
				throw new IllegalStateException("Employee salary cannot be below 22000");
			}
			
			employee.setSalary(salary);
		}
	}
}
