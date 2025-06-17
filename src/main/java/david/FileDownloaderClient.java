package david;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloaderClient {
    public static void main(String[] args) {

        // 1. Define both URLs for clarity
        String standardUrl = "http://localhost:8081/download-standard";
        String streamUrl = "http://localhost:8081/download-stream";

        // 2. This is the ONLY line you need to change to switch tests
        String chosenUrl = streamUrl; // <-- To test the other method, change this to standardUrl

        // 3. Determine the filename based on the chosen URL
        String savePath;
        if (chosenUrl.contains("stream")) {
            savePath = "downloaded_file_FROM_STREAM.zip";
        } else {
            savePath = "downloaded_file_FROM_STANDARD.zip";
        }

        // 4. The rest of the code uses these variables
        System.out.println(">>> Starting download from: " + chosenUrl);
        System.out.println(">>> Saving file to: " + savePath); 

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(chosenUrl)).build();
        try {
            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get(savePath)));
            if (response.statusCode() == 200) {
                System.out.println(">>> File downloaded successfully to: " + savePath);
                System.out.println(">>> Server response headers:");
                response.headers().map().forEach((k, v) -> System.out.println("  " + k + ":" + v));
            } else {
                System.out.println(">>> Download failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
