import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIHelper {
    static final int SUCCESS = 200;

    static public String getResponse(String urlStr) {
        String responseStr = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check if the response code is successful
            if (connection.getResponseCode() == SUCCESS) {
                // Create a scanner to read the response
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();

                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }

                // Close the scanner and connection
                scanner.close();
                connection.disconnect();

                responseStr = response.toString();
            } else {
                System.out.println("Failed to fetch data. Response code: " + connection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }
}
