package com.controller.validators;

import com.controller.entity.User;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MyValidator implements ConstraintValidator<TestValidation, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if(user.getUsername().length()==4){

        }
        return false;
    }
}
