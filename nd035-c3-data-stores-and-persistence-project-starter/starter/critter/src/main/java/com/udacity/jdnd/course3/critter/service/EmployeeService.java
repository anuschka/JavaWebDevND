package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee save(Employee employee);
    Employee getEmployeeById(Long employeeId);
    List<Employee> getEmployeesByAvailability(Set<EmployeeSkill> skills, DayOfWeek day);
    List<Employee> getAllEmployeesByIds(List<Long> employeeIds);
}
