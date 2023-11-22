package services;
public class ServiceResponse<T> {
	
    private int responseCode;
    private T responseBody;

    public ServiceResponse(int responseCode, T responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public T getResponseBody() {
        return responseBody;
    }
}
