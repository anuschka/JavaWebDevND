package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getAllSchedulesByPetId(Long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    @Override
    public List<Schedule> getAllSchedulesByEmployeeId(Long employeeId) {
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    @Override
    public List<Schedule> getAllSchedulesByCustomerId(Long customerId) {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            List<Pet> pets = customer.get().getPets();
            List<Schedule> schedules = new ArrayList<>();
            for(Pet p : pets){
                schedules.addAll(scheduleRepository.findAllByPetsId(p.getId()));
            }
            return schedules;
        } else {
            return null;
        }
    }


}
