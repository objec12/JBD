package com.objectia.JBD_HandsOnLearning.feign;

import com.objectia.JBD_HandsOnLearning.dtos.CheckFraudDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "fraud" , url = "http://localhost:9097")
public interface FraudFeignClient {

    @PostMapping("fraud-controller/check-fraud")
    ResponseEntity<Boolean> checkFraud(@RequestBody CheckFraudDTO checkFraudDTO);

}
