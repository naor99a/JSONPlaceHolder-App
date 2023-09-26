import ResourceClasses.Album;
import ResourceClasses.Todo;
import ResourceClasses.User;
import Utilities.APIHelper;

import Utilities.Consts;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    // Method 1 ("Create a method that returns the summary for each user, his/her uncompleted tasks (todos)")
    public static Collection<String> getTaskSummariesByUser() {
        Collection<User> users = Queries.getAllUsers();

        List<String> summaries = new ArrayList<>();
        for (User user : users) {
            summaries.add(
                    user.getSummary() +
                            "\nUncompleted tasks:\n" +
                            Queries.getUserUncompletedTasksSummary(user.getId()));
        }

        return summaries;
    }


    // Method 2 ("Create a method that returns the uncompleted tasks of a given user id")
    public static Collection<Todo> getUncompletedByUser(int userId) {
        // filter and return only uncompleted
        return Queries.getTodosForUser(userId).
                stream().
                filter(todo -> !todo.isCompleted()).
                collect(Collectors.toList());
    }


    // Method 3 ("Create a method that returns the summary for each user, the email of each replier (in a comment) per
    //            each post that the user has posted. If the post had 0 replies, do not show it.")
    public static Collection<String> getRepliersByUserPosts() {
        return Queries.getAllUsers().
                stream().
                map(User::getPostsSummary).
                collect(Collectors.toList());
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

        return userAlbums.stream().
                filter(album -> ( album.size() > threshold)).
                collect(Collectors.toList());
    }

    public static void main(String[] args) {
        // Printing outputs for each method
        System.out.println("===================== Method 1 Example =====================");
        Collection<String > result1 = getTaskSummariesByUser();
        System.out.println(String.join("\n\n", result1));

        System.out.println("\n\n===================== Method 2 Example =====================");
        System.out.println("Exmaple - running for user #5");
        System.out.println(getUncompletedByUser(5));

        System.out.println("\n\n===================== Method 3 Example =====================");
        Collection<String> result3 = getRepliersByUserPosts();
        System.out.println(String.join("\n\n", result3));

        System.out.println("\n\n===================== Method 4 Example =====================");
        System.out.println("Exmaple - running for user #5, with threshold of 30");
        System.out.println(getExceedingAlbumsByUser(5, 30));

    }

}