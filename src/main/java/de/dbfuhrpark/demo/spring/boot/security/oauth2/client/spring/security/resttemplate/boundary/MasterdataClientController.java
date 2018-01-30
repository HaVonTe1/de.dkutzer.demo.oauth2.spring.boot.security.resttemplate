package de.dbfuhrpark.demo.spring.boot.security.oauth2.client.spring.security.resttemplate.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterdataClientController {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String getPersons(@Value("${oauth2-client.person}") String endpoint) {
        ResponseEntity<String> entity = restTemplate.getForEntity(endpoint, String.class);
        return entity.getBody();
    }


}
