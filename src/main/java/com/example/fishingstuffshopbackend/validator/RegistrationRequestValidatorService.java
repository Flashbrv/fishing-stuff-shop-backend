package com.example.fishingstuffshopbackend.validator;

import com.example.fishingstuffshopbackend.dto.RegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class RegistrationRequestValidatorService implements Predicate<RegistrationRequest> {
    @Override
    public boolean test(RegistrationRequest registrationRequest) {
        // TODO: Implement validation logic
        return true;
    }
}
