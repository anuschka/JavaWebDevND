package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Pet> getAllPetsByIds(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }

    @Override
    public Pet getPetById(Long petId) {

        Optional<Pet> pet = petRepository.findById(petId);

        if (pet.isPresent()) {
            return pet.get();
        } else {
            return null;
        }
    }

    @Override
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getAllPetsByOwnerId(long ownerId) {
        Optional<Customer> customer = customerRepository.findById(ownerId);
        if(customer.isPresent()) {
            List<Pet> pets = customer.get().getPets();
            return pets;
        } else {
            return null;
        }
    }


}