package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.service.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetServiceImpl petService;
    CustomerServiceImpl customerService;

    public PetController(PetServiceImpl petService, CustomerServiceImpl customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        Pet petSaved = petService.savePet(pet);

        if(petDTO.getOwnerId() != 0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            customer.getPets().add(petSaved);
            customerService.save(customer);
        }

        petDTO.setId(petSaved.getId());
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Helper Method
     * @param petDTO    petDTO object to be converted
     * @return      entity form of pet
     */
    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        if(petDTO.getOwnerId()!=0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
        }
        return pet;
    }
}
