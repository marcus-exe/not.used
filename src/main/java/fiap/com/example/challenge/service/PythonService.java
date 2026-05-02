package fiap.com.example.challenge.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class PythonService {
    private final RestTemplate restTemplate;

    public PythonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String submitService(Map<String, Object> jsonData) {
        String url = "http://localhost:5000/submit";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(jsonData, headers);

        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }


}
