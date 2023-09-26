import ResourceClasses.*;
import Utilities.APIHelper;
import Utilities.Consts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Queries {
    // Method 1 ("Create a method that returns the summary for each user, his/her uncompleted tasks (todos)")
    public static Collection<String> getTaskSummariesByUser() {

        // get all users
        Collection<User> users = getAllUsers();

        List<String> summaries = new ArrayList<>();
        for (User user : users) {
            summaries.add(
                    user.toString()
                            + "\nUncompleted tasks:\n"
                            + getUserUncompletedTasksSummary(user.getId()));
        }

        return summaries;
    }

    private static String getUserUncompletedTasksSummary(int userId) {
        StringBuilder taskSummary = new StringBuilder();
        getUncompletedByUser(userId).
                forEach(task ->
                        taskSummary.append("\t").append(task).append("\n"));
        return taskSummary.toString();
    }



    // Method 2 ("Create a method that returns the uncompleted tasks of a given user id")
    public static Collection<Todo> getUncompletedByUser(int userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArrayStr = APIHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/users" + "/" + userId + "/" + "todos");

        // get all user's Todos
        List<Todo> userTodos = null;
        try {
            userTodos = objectMapper.readValue(jsonArrayStr, new TypeReference<List<Todo>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // filter and return only uncompleted
        return userTodos.stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
    }
    /*
    private static Collection<Todo> getTodosForUser(int userId) {
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
    }*/


    // Method 3 ("Create a method that returns the summary for each user, the email of each replier (in a comment) per
    //            each post that the user has posted. If the post had 0 replies, do not show it.")
    public static Collection<String> getRepliersByUserPosts() {
        return getAllUsers().stream()
                .map(User::getPostsSummary)
                .collect(Collectors.toList());
    }


    // Method 4 ("Create a method that returns all albums of a specific user that contains more photos than a given threshold")
    public static Collection<Album> getExceedingAlbumsByUser(int userId, int threshold) {
        ObjectMapper objectMapper = new ObjectMapper();

        String urlStr = Consts.JSONPlaceholder_ADDRESS + "/users/" + userId + "/albums";

        String albumsJsonArrayStr = APIHelper.getResponse(urlStr);
        List<Album> userAlbums = null;
        try {
            userAlbums = objectMapper.readValue(albumsJsonArrayStr, new TypeReference<List<Album>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userAlbums.stream()
                .filter(album -> ( album.size() > threshold))
                .collect(Collectors.toList());
    }

    private static Collection<User> getAllUsers() {
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

    public static void main(String[] args) {

        // Printing outputs for each method

        // Method 1
        System.out.println("===================== Method 1 =====================");
        System.out.println(getTaskSummariesByUser());

        System.out.println("===================== Method 2 =====================");
        System.out.println(getUncompletedByUser(1));

        System.out.println("===================== Method 3 =====================");
        System.out.println(getRepliersByUserPosts());

        System.out.println("===================== Method 4 =====================");
        System.out.println(getExceedingAlbumsByUser(1, 30));

        System.out.println(getRepliersByUserPosts().stream().collect(Collectors.joining("\n\n")));

    }

}
