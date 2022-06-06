package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class UserList {

    private ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        int i = 0;
        String json = "{\"users\":[";
        for(User u : users) {
            try {
                json = json+u.toJson();
                if(i<users.size()) {
                    json = json+", ";
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        json = json+"]}";
        return json;
    }
}
