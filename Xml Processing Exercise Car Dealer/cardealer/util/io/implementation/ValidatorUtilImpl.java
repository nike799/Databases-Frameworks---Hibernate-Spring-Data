package cardealer.util.io.implementation;

import cardealer.util.io.contract.ValidatorUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl implements ValidatorUtil {
    private Validator validator;

    @Override
    public <T> boolean isValid(T object) {
        return this.validator.validate(object).size() == 0;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> violations(T object) {
        return this.validator.validate(object);
    }
}
