package com.example.demo.department;

import java.util.Optional;

import com.example.demo.employee.Employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
	@Id
	@SequenceGenerator(
		name = "department_sequence",
		sequenceName = "department_sequence",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "department_sequence"
	)
	private Long department_id;
	private String department_name;
	@OneToOne
	@JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
	private Employee manager_id;
	
	public Department() {}
	
	public Department(String department_name, Employee manager_id) {
		this.department_name = department_name;
		this.manager_id = manager_id;
	}
	
	public Department(Long department_id, String department_name, Employee manager_id) {
		this.department_id = department_id;
		this.department_name = department_name;
		this.manager_id = manager_id;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public Employee getManager_id() {
		return manager_id;
	}

	public void setManager_id(Employee manager_id) {
		this.manager_id = manager_id;
	}

	@Override
	public String toString() {
		return "Department [department_id=" + department_id + ", department_name=" + department_name + ", manager_id="
				+ manager_id + "]";
	}
}
