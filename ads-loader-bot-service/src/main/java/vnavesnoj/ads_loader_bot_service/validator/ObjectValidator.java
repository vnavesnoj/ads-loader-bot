package vnavesnoj.ads_loader_bot_service.validator;

import vnavesnoj.ads_loader_bot_service.exception.ObjectValidationException;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface ObjectValidator<T> {

    T validate(T object) throws ObjectValidationException;
}
