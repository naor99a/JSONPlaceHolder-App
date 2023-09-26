import ResourceClasses.*;
import Utilities.APIHelper;
import Utilities.Consts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class Queries {

    public static String getUserUncompletedTasksSummary(int userId) {
        StringBuilder taskSummary = new StringBuilder();
        Main.getUncompletedByUser(userId).
                forEach(task ->
                        taskSummary.append("\t").append(task).append("\n"));
        return taskSummary.toString();
    }


    public static Collection<Todo> getTodosForUser(int userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArrayStr = APIHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/users" + "/" + userId + "/" + "todos");

        // get all user's Todos
        List<Todo> userTodos = null;
        try {
            userTodos = objectMapper.readValue(jsonArrayStr, new TypeReference<List<Todo>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userTodos;
    }


    public static Collection<User> getAllUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArrayStr = APIHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/users");

        List<User> users = null;
        try {
            users = objectMapper.readValue(jsonArrayStr, new TypeReference<List<User>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return users;
    }

}
