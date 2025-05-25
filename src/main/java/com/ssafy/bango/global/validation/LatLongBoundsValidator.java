package com.ssafy.bango.global.validation;

import com.ssafy.bango.domain.rentalhouse.dto.request.LatLongBoundsRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LatLongBoundsValidator implements ConstraintValidator<ValidLatLongBounds, LatLongBoundsRequest> {
    @Override
    public boolean isValid(LatLongBoundsRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;

        context.disableDefaultConstraintViolation();
        boolean validLat = validateLatitudeRange(value, context);
        boolean validLong = validateLongitudeRange(value, context);

        return validLat && validLong;
    }

    private boolean validateLatitudeRange(LatLongBoundsRequest value, ConstraintValidatorContext context) {
        if (value.getMinLatitude() > value.getMaxLatitude()) {
            addViolation(context, "minLatitude", "최소 위도는 최대 위도보다 작거나 같아야 합니다.");
            return false;
        }
        return true;
    }

    private boolean validateLongitudeRange(LatLongBoundsRequest value, ConstraintValidatorContext context) {
        if (value.getMinLongitude() > value.getMaxLongitude()) {
            addViolation(context, "minLongitude", "최소 경도는 최대 경도보다 작거나 같아야 합니다.");
            return false;
        }
        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String property, String message) {
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(property)
            .addConstraintViolation();
    }
}
