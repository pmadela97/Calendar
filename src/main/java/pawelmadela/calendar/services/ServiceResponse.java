package pawelmadela.calendar.services;


/**
 * Class that represents response that contains information about services results.
 * If service didn't response expected results, we have information what kind of problem occurs as <code>responseStatus</code>.
 * If everything is ok, we receive expected object and right status.
 * Fields are constants, to prevent changes in response.
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
