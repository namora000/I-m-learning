package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {
    private String message;
    private boolean result;

    public void setMessage(String message) {
        this.message = message;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }
    public boolean getResult() {
        return result;
    }
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
    public String toString() {
        return "{" +
                "\"answer\": {" +
                "\"message\":" + "\"" + message + "\", " +
                "\"result\":" + "\"" + result + "\", " +
                "}" +
                "}";
    }
}
