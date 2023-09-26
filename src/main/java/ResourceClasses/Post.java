package ResourceClasses;

import Utilities.APIHelper;
import Utilities.Consts;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Collection<Comment> getComments() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArrayStr = APIHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/posts/" + getId() + "/comments");

        List<Comment> comments = null;
        try {
            comments = objectMapper.readValue(jsonArrayStr, new TypeReference<List<Comment>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public String getPostRepliersSummary() {
        Collection<Comment> comments = getComments();
        if (comments.isEmpty()) return null;

        String emailsString = comments.stream().
                map(Comment::getEmail).distinct().
                collect(Collectors.joining(", "));

        return "Post #" + id + " has replies from: " + emailsString;
    }
}
