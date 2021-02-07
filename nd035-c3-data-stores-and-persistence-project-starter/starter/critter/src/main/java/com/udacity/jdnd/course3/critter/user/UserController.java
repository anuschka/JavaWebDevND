package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.service.EmployeeServiceImpl;
import com.udacity.jdnd.course3.critter.service.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    CustomerServiceImpl customerService;
    PetServiceImpl petService;
    EmployeeServiceImpl employeeService;


    public UserController(CustomerServiceImpl customerService, PetServiceImpl petService, EmployeeServiceImpl employeeService) {
        this.customerService = customerService;
        this.petService = petService;
        this.employeeService = employeeService;

    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        // Saving customer data and returning it
        Customer createdCustomer = customerService.save(convertCustomerDTOToCustomer(customerDTO));

        //if the getPets() is not null we will map all the ids of the pets that a given Customer has and we
        // will return it, if there is no pet id it will be returned in response null
        if(createdCustomer.getPets() != null) {
            customerDTO.setPetIds(createdCustomer.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList())
            );
        }

        // Setting the id to customer DTO so that it is also presented to the front end side.
        customerDTO.setId(createdCustomer.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream().map(this::convertCustomerToCustomerDTO).collect(Collectors.toList());
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPetById(petId);
        Customer customer = pet.getCustomer();
        CustomerDTO customerDTO = convertCustomerToCustomerDTO(customer);
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee createdEmployee = employeeService.save(convertEmployeeDTOToEmployee(employeeDTO));
        employeeDTO.setId(createdEmployee.getId());
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee retrievedEmployee = employeeService.getEmployeeById(employeeId);
        EmployeeDTO employeeDTO = convertEmployeeToEmployeeDTO(retrievedEmployee);
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.save(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        List<Employee> employees = employeeService.getEmployeesByAvailability(skills, day);
        if (!employees.isEmpty()) {
            List<EmployeeDTO> employeeDTOS = employees.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
            return employeeDTOS;
        }
        return null;
    }

    /**
     * Helper Method
     * @param customerDTO   customerDTO object to be converted into entity
     * @return  entity form of Customer
     */
    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());

        return customer;
    }
    /**
     * Helper Method
     * @param customer  customer object to be converted into DTO
     * @return DTO form of Customer
     */
    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());

        List<Long> petIds = new ArrayList<>();

        if (!customer.getPets().isEmpty()) {
            petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
    /**
     * Helper Method
     * @param employeeDTO   employeeDTO object to be converted into entity
     * @return entity form of Employee
     */
    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        if(employeeDTO.getDaysAvailable()!=null){
            employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        }
        return employee;
    }

    /**
     * Helper Method
     * @param employee  employee object to be converted into DTO
     * @return DTO form of employee
     */
    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        if(employee.getDaysAvailable()!=null){
            employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        }
        return employeeDTO;
    }


}
