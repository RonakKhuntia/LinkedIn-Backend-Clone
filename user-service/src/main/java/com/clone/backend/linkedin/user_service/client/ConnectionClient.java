package com.clone.backend.linkedin.user_service.client;

import com.clone.backend.linkedin.user_service.model.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "connections-service", path = "/connections")
public interface ConnectionClient {

    @PostMapping("/core/create")
    void createPerson(@RequestBody PersonDto person);

}
