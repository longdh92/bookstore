package com.myweb.bookstore.check;

import com.myweb.bookstore.entity.Customer;
import com.myweb.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CustomerCheck implements Validator {
    @Autowired
    private CustomerService customerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","NotEmpty");
        if (customer.getEmail().matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}")){
            errors.rejectValue("email","not regex");
        }
        if (customerService.findByEmail(customer.getEmail())!=null){
            errors.rejectValue("email","Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","Not Empty");
        if (customer.getPassword().length()<8||customer.getPassword().length()>32){
            errors.rejectValue("password","Size.userForm.password");
        }

    }
}
