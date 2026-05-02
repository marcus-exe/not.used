package fiap.com.example.challenge.controller;

import fiap.com.example.challenge.model.employee.Address;
import fiap.com.example.challenge.service.AddressService;
import fiap.com.example.challenge.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final ApiService apiService;
    private final AddressService addressService;


    @Autowired
    public AddressController(ApiService apiService, AddressService addressService) {
        this.apiService = apiService;
        this.addressService = addressService;
    }

    @GetMapping("/fetch")
    public Address getAddress(@RequestParam String cep) {
        return apiService.getAddress(cep);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable UUID id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping("/add")
    public Address addAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable UUID id, @RequestBody Address updatedAddress) {
        Address address = addressService.updateAddress(id, updatedAddress);
        return ResponseEntity.ok(address);
    }
}
