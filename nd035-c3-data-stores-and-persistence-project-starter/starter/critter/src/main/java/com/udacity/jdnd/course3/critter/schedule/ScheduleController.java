package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeServiceImpl;
import com.udacity.jdnd.course3.critter.service.PetServiceImpl;
import com.udacity.jdnd.course3.critter.service.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleServiceImpl scheduleService;
    EmployeeServiceImpl employeeService;
    PetServiceImpl petService;

    public ScheduleController(ScheduleServiceImpl scheduleService, EmployeeServiceImpl employeeService, PetServiceImpl petService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule createdSchedule = scheduleService.save(convertScheduleDTOToSchedule(scheduleDTO));
        scheduleDTO.setId(createdSchedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByPetId(petId);
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByEmployeeId(employeeId);
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByCustomerId(customerId);
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        return scheduleDTOS;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();

        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        List<Long> employeeIDs = scheduleDTO.getEmployeeIds();
        List<Employee> employees = employeeService.getAllEmployeesByIds(employeeIDs);
        schedule.setEmployees(employees);

        List<Long> petIDs = scheduleDTO.getPetIds();
        List<Pet> pets = petService.getAllPetsByIds(petIDs);
        schedule.setPets(pets);

        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }
}