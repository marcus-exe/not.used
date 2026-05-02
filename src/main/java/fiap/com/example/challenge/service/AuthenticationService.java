package fiap.com.example.challenge.service;

import fiap.com.example.challenge.model.employee.Employee;
import fiap.com.example.challenge.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean authenticate(String email, String password) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(email);

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            // Assuming the password is stored in plain text (not recommended), you can compare it directly
            return employee.getPassword().equals(password);
        }

        return false;
    }
}
