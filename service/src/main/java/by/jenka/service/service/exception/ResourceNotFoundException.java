package by.jenka.service.service.exception;


public class ResourceNotFoundException extends ApiCityServiceException {

    private static final String MESSAGE = "Resource %s with Key %s not found";

    public ResourceNotFoundException(Class<?> clazz, Object primaryKey) {
        super(MESSAGE.formatted(clazz.getSimpleName(), primaryKey));
    }
}
