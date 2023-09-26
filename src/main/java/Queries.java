import ResourceClasses.Album;
import ResourceClasses.Todo;
import ResourceClasses.User;
import Utilities.APIHelper;
import Utilities.Consts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Queries {


    // Method 1 ("Create a method that returns the summary for each user, his/her uncompleted tasks (todos)")
    public static Collection<String> getTaskSummariesByUser() {

        // get all users
        Collection<User> users = getUsers();

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
        getUncompletedByUser(userId).stream().
                forEach(task ->
                        taskSummary.append("\t" + task +"\n"));
        return taskSummary.toString();
    }



    // Method 2 ("Create a method that returns the uncompleted tasks of a given user id")
    public static Collection<Todo> getUncompletedByUser(int userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArrayStr = APIHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/users" + "/" + userId + "/" + "todos");

        List<Todo> userTodos = null;
        try {
            userTodos = objectMapper.readValue(jsonArrayStr, new TypeReference<List<Todo>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userTodos.stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
    }


    // Method 3 ("Create a method that returns the summary for each user, the email of each replier (in a comment) per
    //            each post that the user has posted. If the post had 0 replies, do not show it.")



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



    // Method 1 ("Create a method that returns the summary for each user, his/her uncompleted tasks (todos)")
    public static Collection<User> getUsers() {
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

    /*
        for (Todo t : getUncompletedByUser(10)) {
            System.out.println(t);
            System.out.println(t.isCompleted());
        }

    */
        // System.out.println(getUncompletedByUser(10));
        for (String s : getTaskSummariesByUser()) {
            System.out.println(s);
        }

    }

}
