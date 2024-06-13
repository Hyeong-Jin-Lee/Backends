package F3.Gogi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class MeatGradeService {

    public String gradeMeat(MultipartFile file) throws Exception {
        // 임시 파일 저장
        UUID uuid = UUID.randomUUID();
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
        File tempFile = new File(tempDir.toFile(), uuid.toString() + ".jpg");
        file.transferTo(tempFile);

        // Python 스크립트 실행
        ProcessBuilder processBuilder = new ProcessBuilder("python", "classify_meat.py", tempFile.getAbsolutePath());
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        // 결과 처리
        if (exitCode == 0) {
            // Python 스크립트가 결과를 표준 출력으로 반환하는 것을 가정
            String grade = new String(process.getInputStream().readAllBytes());
            return grade.trim();
        } else {
            throw new RuntimeException("Error processing image with Python script.");
        }
    }
}
