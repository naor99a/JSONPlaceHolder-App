package ResourceClasses;

import Utilities.APIHelper;
import Utilities.Consts;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String name;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Post> getPosts() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArrayStr = APIHelper.getResponse(Consts.JSONPlaceholder_ADDRESS + "/users/" + getId() + "/posts");

        List<Post> posts = null;
        try {
            posts = objectMapper.readValue(jsonArrayStr, new TypeReference<List<Post>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public String getPostsSummary() {
        Collection<Post> userPosts = getPosts();

        Collection<String> postsSummaries = userPosts.stream().
                map(Post::getPostRepliersSummary).
                filter(Objects::nonNull).
                collect(Collectors.toList());

        String postsSummariesString = String.join("\n", postsSummaries);

        return "User #" + getId() + " posted " + userPosts.size() + " posts." +
                "\nHere are the emails of the repliers to each post:\n" + postsSummariesString;
    }

    public String getSummary() {
        return "User id: " + getId() +
                "\nName: " + getName() +
                "\nUsername: " + getUsername();
    }
}