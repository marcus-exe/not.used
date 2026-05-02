package fiap.com.example.challenge.service;

import fiap.com.example.challenge.model.employee.Address;
import fiap.com.example.challenge.repository.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getAddressById(UUID id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }

    public Address updateAddress(UUID id, Address updatedAddress) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setStreet(updatedAddress.getStreet());
                    existingAddress.setNeighborhood(updatedAddress.getNeighborhood());
                    existingAddress.setNumber(updatedAddress.getNumber());
                    existingAddress.setCity(updatedAddress.getCity());
                    existingAddress.setUf(updatedAddress.getUf());
                    existingAddress.setCep(updatedAddress.getCep());
                    return addressRepository.save(existingAddress);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
    }
}
