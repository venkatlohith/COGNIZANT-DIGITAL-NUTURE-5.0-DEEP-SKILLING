package com.cognizant.composite.client;

import com.cognizant.composite.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// "name" alone is resolved via Eureka - no hardcoded host/port needed,
// since both composite-service and account-service register with the
// same eureka-discovery-server.
@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts/{number}")
    Account getAccount(@PathVariable("number") String number);
}
