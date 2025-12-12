package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/zipcode")
public class ZipcodeController {

    private final AddressService addressService;

    @GetMapping("/lookup/{zipcode}")
    public ResponseEntity<?>getAddressByZipCode(@PathVariable String zipcode){

        log.info("Received request for CEP: {}", zipcode);

        var response = addressService.getAddressByZipCode(zipcode);

        log.info("Response for CEP {}: {}", zipcode, response);

        return ResponseEntity.ok(response);
    }
}
