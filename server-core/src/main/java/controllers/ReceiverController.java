package controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import services.DBService;

import java.util.List;


@RestController
public class ReceiverController {
    private static final Logger log = LoggerFactory.getLogger(ReceiverController.class);
    private final RestTemplate restTemplate;
    public ReceiverController (@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    DBService dbService;

    //Метод для приёма координат от tracker-core
    @RequestMapping(value = "/coordinates", method = RequestMethod.POST)
    public String coordinates(@RequestBody Point point) throws JsonProcessingException {

        log.info(point.toString());
        dbService.setPoint(point);
        Response response = new Response();
        response.setMessage("ok");
        response.setResult(true);
        return response.toString();
    }

    //Метод приёма запросов на N-количество последних точек координат.
    // В поле "request" ожидается строка вида: getLastPoints=4 или getLastPoints=all
    @RequestMapping(value = "/tracker", method = RequestMethod.POST)
    public String tracker(@RequestBody Request request) throws JsonProcessingException {
        //проверка запроса
        if (request.getRequest().contains("getLastPoints")) { //Синтаксис запроса верен
            //отправка запроса в базу
            dbService.setRequest(request);

            //обработка запроса
            String[] message = request.getRequest().split("=");
            dbService.run("run");
            List<Point> list = dbService.getPoints();
            PointList pointList = new PointList();

            //подготовка ответа
            if(message[1].equalsIgnoreCase("all")) {
                for(Point p : list) {
                    pointList.addPoint(p);
                }
            } else {
                int j = Integer.getInteger(message[1]);
                for(int i = list.size()-(list.size()-j); i < list.size(); i++ ) {
                    pointList.addPoint(list.get(i));
                }
            }

            //ответ на запрос с верным синтаксисом
            return pointList.toJson();

        } else { //синтаксис запроса не верен
            //подготовка ответа на случай не верного запроса
            Response response = new Response();
            response.setMessage("fail");
            response.setResult(false);

            //ответ на запрос с неверным синтаксисом
            return response.toJson();
        }
    }

    //метод приёма запросов на список пользователей в системе. В поле "request" ожидается строка вида: getListUsers=root
    //где значение после знака равенства - роль пользователя, сделавшего запрос
    //пользователь client не может делать подобные запросы, но если и сделает, ему вернётся сообщение об ошибке.
    //пользователь manager получит в ответ список пользователей с ролью client
    //пользователь root получит полный список пользователей в системе
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String users (@RequestBody Request request) throws JsonProcessingException {
        //проверка запроса
        if (request.getRequest().contains("getListUsers")) { //Синтаксис запроса верен
            //отправка запроса в базу
            dbService.setRequest(request);

            //обработка запроса
            dbService.run("run");
            List<User> list = dbService.getUsers();
            String[] message = request.getRequest().split("=");
            UserList userList = new UserList();

            //подготовка ответа
            if(message[1].equalsIgnoreCase("root")) {
                for(User u : list) {
                    userList.addUser(u);
                }
                //ответ на запрос с верным синтаксисом
                return userList.toJson();
            } else if(message[1].equalsIgnoreCase("manager")) {
                for(User u : list) {
                    if(u.getRole().equalsIgnoreCase("manager") || u.getRole().equalsIgnoreCase("client")) {
                        userList.addUser(u);
                    }
                }
                //ответ на запрос с верным синтаксисом
                return userList.toJson();
            } else {
                Response response = new Response();
                response.setMessage("error");
                response.setResult(false);
                //ответ на запрос с верным синтаксисом, но выполненным по ошибке и не имеющим прав к получению списка пользователей.
                return response.toJson();
            }

        } else { //синтаксис запроса не верен
            //подготовка ответа на случай не верного запроса
            Response response = new Response();
            response.setMessage("fail");
            response.setResult(false);

            //ответ на запрос с неверным синтаксисом
            return response.toJson();
        }
    }
    //метод приёма запросов на внесение изменений в БД.
    //В поле "request" ожидается строка вида: updUser=id:10;role:root;name:manager;pass:pass в случае изменения полей пользователя
    //В поле "request" ожидается строка вида: updPoint=id:10;lat:38.760888;lon:44.670992;alt:97.0 в случае изменения полей точки GPS
    //При этом скорость и время менять не разрешается, так как подправить координаты можно по причине плохой точности например, а
    //время и скорость синхронизируются с тахографом внутри автомобиля, здесь разночтений быть не должно.
    @RequestMapping(value = "/updater", method = RequestMethod.POST)
    public String updater (@RequestBody Request request) throws JsonProcessingException {
        //проверка запроса
        if(request.getRequest().contains("upd")) {//Синтаксис запроса верен
            //отправка запроса в базу
            dbService.setRequest(request);

            //Подготовка к выполнению запроса на обновление данных
            String[] fullMessage = request.getRequest().split("=");
            String[] message;

            if(request.getRequest().contains("updPoint")) {
                message = fullMessage[1].split(";");
                String[] temp;
                int id = 0;
                String lat = "no data";
                String lon = "no data";
                String alt = "no data";
                for(String s : message) {
                    if(s.contains("lat")) {
                        temp = s.split(":");
                        lat = temp[1];
                    } else if(s.contains("lon")) {
                        temp = s.split(":");
                        lat = temp[1];
                    } else if(s.contains("alt")) {
                        temp = s.split(":");
                        lat = temp[1];
                    } else if(s.contains("id")) {
                        temp = s.split(":");
                        id = Integer.parseInt(temp[1]);
                    }
                }
                dbService.updUser(id, lat, lon, alt);

            } else if(request.getRequest().contains("updUsr")) {
                message = fullMessage[1].split(";");
                String[] temp;
                int id = 0;
                String role = "no data";
                String name = "no data";
                String pass = "no data";
                for(String s : message) {
                    if(s.contains("role")) {
                        temp = s.split(":");
                        role = temp[1];
                    } else if(s.contains("name")) {
                        temp = s.split(":");
                        name = temp[1];
                    } else if(s.contains("pass")) {
                        temp = s.split(":");
                        pass = temp[1];
                    } else if(s.contains("id")) {
                        temp = s.split(":");
                        id = Integer.parseInt(temp[1]);
                    }
                }
                dbService.updUser(id, role, name, pass);
            }

            //подготовка ответа на верный запрос
            Response response = new Response();
            response.setMessage("ok");
            response.setResult(true);

            //ответ на запрос с неверным синтаксисом
            return response.toJson();

        } else {
            //подготовка ответа на случай не верного запроса
            Response response = new Response();
            response.setMessage("fail");
            response.setResult(false);

            //ответ на запрос с неверным синтаксисом
            return response.toJson();
        }
    }
}


