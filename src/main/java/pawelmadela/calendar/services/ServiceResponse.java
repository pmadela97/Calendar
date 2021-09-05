package pawelmadela.calendar.services;


/**
 * Class that represents response which contains information about services results
 * if service didn't receive expected result, we have information what kind of problem occurs as ResponseStatus
 * if everything is ok, we receive expected object and right status.
 * Contains only getters and fields are constants, to prevent changes in response
 */
public class ServiceResponse<E,T> {
    private final E responseStatus;
    private final T responseObject;



    public ServiceResponse(E responseStatus, T responseObject){
        this.responseStatus = responseStatus;
        this.responseObject = responseObject;
    }

    public E getResponseStatus() {
        return responseStatus;
    }

    public T getResponseObject() {
        return responseObject;
    }
}
