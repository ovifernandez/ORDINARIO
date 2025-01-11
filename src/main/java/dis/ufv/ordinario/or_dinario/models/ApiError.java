package dis.ufv.ordinario.or_dinario.models;

public class ApiError {
    private int statusCode;
    private String message;

    public ApiError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static ApiError notFound(String id) {
        return new ApiError(404, "El recurso con ID " + id + " no existe.");
    }

}
