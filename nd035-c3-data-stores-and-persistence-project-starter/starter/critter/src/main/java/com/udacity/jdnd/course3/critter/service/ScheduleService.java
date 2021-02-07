package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule save(Schedule schedule);
    List<Schedule> getAllSchedules();
    List<Schedule> getAllSchedulesByPetId(Long petId);
    List<Schedule> getAllSchedulesByEmployeeId(Long employeeId);
    List<Schedule> getAllSchedulesByCustomerId(Long customerId);
}