package ResourceClasses;

import Utils.Consts;
import Utils.APIHelper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {
    private int userId;
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int size() {
        ObjectMapper objectMapper = new ObjectMapper();

        String urlStr = Consts.JSONPlaceholder_ADDRESS + "/albums/" + id + "/photos";
        String photosJsonArrayStr = APIHelper.getResponse(urlStr);
        List<Object> albumPhotos = null;
        try {
            albumPhotos = objectMapper.readValue(photosJsonArrayStr, new TypeReference<List<Object>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (albumPhotos == null) return 0;
        return albumPhotos.size();
    }

    // can be ignored - for debugging
    @Override
    public String toString() {
        return "{id=" + id + ", title='" + title + '\'' + '}';
    }
}