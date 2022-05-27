package jdev.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import controllers.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseTest {

    private String mes = "ok";

    private boolean res = true;
    private String json = "{\"message\":\""+mes+"\",\"result\":"+res+"}";
    @Test
    public void encodeResp() throws JsonProcessingException {

        Response resp = new Response();
        resp.setMessage(mes);
        resp.setResult(res);
        assertTrue(resp.toJson().contains(Boolean.toString(res)));
        assertTrue(resp.toJson().contains(mes));
        assertEquals(json, resp.toJson());
        assertEquals(json, resp.toString());
        System.out.println(resp.toJson());
        System.out.println(resp.toString());

    }

}
