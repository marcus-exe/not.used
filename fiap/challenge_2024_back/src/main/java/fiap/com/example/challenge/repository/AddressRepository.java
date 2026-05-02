package fiap.com.example.challenge.repository;

import fiap.com.example.challenge.model.employee.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
