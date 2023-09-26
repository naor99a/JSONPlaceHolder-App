import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // UserTodos.getAllUsersTodos();
        // System.out.println("All users:\t " + RESTHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/users"));

        System.out.println("For user 1:\t" + getUncompletedByUser(1));
        System.out.println("For user 5:\t" + getUncompletedByUser(5));
        System.out.println("For user 99:\t" + getUncompletedByUser(99));


        for (int threshold = 40; threshold < 60; threshold+=10) {
            for (int user = 0; user < 12; user++) {
                System.out.println("User " + user + " albums with more than " + threshold + " photos: " + getUserAlbumsBiggerThan(user, threshold));
            }
        }
    }



    public static Collection<Todo> getUncompletedByUser(int userId) {
        // Create an instance of ObjectMapper from Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonArrayStr = APIHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/users" + "/" + userId + "/" + "todos");

        List<Todo> userTodos = null;
        try {
            userTodos = objectMapper.readValue(jsonArrayStr, new TypeReference<List<Todo>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        userTodos.stream().filter(todo -> !todo.isCompleted()).collect(Collectors.toList());
        return userTodos;
    }


    public static Collection<Album> getUserAlbumsBiggerThan(int userId, int threshold) {
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
}