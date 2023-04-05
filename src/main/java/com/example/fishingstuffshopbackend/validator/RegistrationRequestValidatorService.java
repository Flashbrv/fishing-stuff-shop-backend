package com.example.fishingstuffshopbackend.validator;

import com.example.fishingstuffshopbackend.dto.RegistrationRequest;
import com.example.fishingstuffshopbackend.exception.BadParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Predicate;

import static com.example.fishingstuffshopbackend.utils.CheckParameterUtils.requireNonNullAndNonBlank;

@Service
@Slf4j
public class RegistrationRequestValidatorService implements Predicate<RegistrationRequest> {
    private static final String EMAIL_REGEXP = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static final String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    @Override
    public boolean test(RegistrationRequest registrationRequest) {
        Objects.requireNonNull(registrationRequest);

        log.info("Email: \"{}\" is validating", registrationRequest.getEmail());
        requireNonNullAndNonBlank("Email", registrationRequest.getEmail());
        // "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"
        if (!registrationRequest.getEmail().matches(EMAIL_REGEXP)) {
            throw new BadParameterException("Email is invalid");
        }
        log.info("Email is valid");

        log.info("FirstName: \"{}\" is validating", registrationRequest.getFirstName());
        requireNonNullAndNonBlank("FirstName", registrationRequest.getFirstName());
        if (registrationRequest.getFirstName().trim().length() < 3) {
            throw new BadParameterException("FirstName should be at least 3 character length");
        }
        log.info("FirstName is valid");

        log.info("PhoneNumber: \"{}\" is validating", registrationRequest.getPhoneNumber());
        requireNonNullAndNonBlank("PhoneNumber", registrationRequest.getPhoneNumber());
        String phoneNumber = registrationRequest.getPhoneNumber();
        if (phoneNumber.length() != 12 || !phoneNumber.matches("\\d{12}")) {
            throw new BadParameterException("PhoneNumber has invalid format or length");
        }
        log.info("PhoneNumber is valid");

        log.info("Password is validating");
        requireNonNullAndNonBlank("Password", registrationRequest.getPassword());
        // TODO: uncomment password checks
        // commented for test purposes
//        if (registrationRequest.getPassword().length() < 8) {
//            throw new BadParameterException(String.format("Password should be at least 8 characters length"));
//        }
//        if (!registrationRequest.getPassword().matches(PASSWORD_REGEXP)) {
//            throw new BadParameterException(String.format("Password should has at least one uppercase character and one number"));
//        }

        return true;
    }
}
