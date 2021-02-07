package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            return employeeOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Employee> getEmployeesByAvailability(Set<EmployeeSkill> skills, DayOfWeek day) {

        List<Employee> availableEmployeesOnDay = employeeRepository.findAllByDaysAvailableContaining(day);
        List<Employee> availableEmployeesOnDayWSkills = new ArrayList<>();
        for (Employee employee : availableEmployeesOnDay) {
            if (employee.getSkills().containsAll(skills)) {
                availableEmployeesOnDayWSkills.add(employee);
            }
        }
        return availableEmployeesOnDayWSkills;
    }

    @Override
    public List<Employee> getAllEmployeesByIds(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }


}

