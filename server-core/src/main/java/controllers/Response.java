package controllers;

public class Response {

    String message;
    boolean success;

    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    //Геттеры
    public String getMessage() {
        return message;
    }
    public Boolean getSuccess() {
        return success;
    }
    //Сеттеры
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
