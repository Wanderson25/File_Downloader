package david;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class FileDownloadController {
    
    private File createDummyFile() throws IOException {
        File tempFile = File.createTempFile("dummy-data", ".zip");
        // Write 10MB of dummy data so we can see the download progress
        Files.write(tempFile.toPath(), new byte[10 * 1024 * 1024]);
        tempFile.deleteOnExit();
        return tempFile;
    }

    @GetMapping("/download-standard")
    public ResponseEntity<Resource> downloadStandard() throws IOException {
        File file = createDummyFile();
        Resource resource = new FileSystemResource(file);
        System.out.println("Serving '/download-standard'. Spring will set Content-Length.");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
            .body(resource);
    }

    @GetMapping("/download-stream")
    public ResponseEntity<StreamingResponseBody> downloadStream() throws IOException {
        File file = createDummyFile();
        System.out.println("Serving '/download-stream'. Using StreamingResponseBody for chunked transfer.");
        StreamingResponseBody stream = outputStream -> {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.transferTo(outputStream);
            }
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(stream);
    }
}
