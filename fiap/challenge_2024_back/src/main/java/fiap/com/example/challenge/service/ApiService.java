package fiap.com.example.challenge.service;

import fiap.com.example.challenge.dto.AddressDTO;
import fiap.com.example.challenge.model.employee.Address;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Address getAddress(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            ResponseEntity<AddressDTO> response = restTemplate.getForEntity(url, AddressDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {

                AddressDTO dto = response.getBody();

                assert dto != null;
                return mapToAddress(dto);

            } else {
                throw new RuntimeException("Failed to fetch address for CEP: " + cep);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching address for CEP: " + cep, e);
        }
    }

    private @NotNull Address mapToAddress(AddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setUf(dto.getState());
        address.setCep(dto.getCep());
        return address;
    }
}

