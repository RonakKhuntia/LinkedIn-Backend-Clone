package com.clone.backend.linkedin.posts_service.clients;

import com.clone.backend.linkedin.posts_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "connections-service", path = "/connections")
public interface ConnectionsClient {

    @GetMapping("/core/{userId}/first-degree")
    List<PersonDto> getFirstConnections();

}
