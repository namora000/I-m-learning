package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "REQUEST")
    private String request;

    public int getId() {
        return id;
    }
    public String getRequest() {
        return request;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setRequest(String request) {
        this.request = request;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
    public String toString() {
        return "Request{ id="+id+
                ", request="+request+" }";
    }


}
