package F3.Gogi.controller;

import F3.Gogi.service.MeatGradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/meat")
public class MeatGradeController {

    @Autowired
    private MeatGradeService meatGradeService;

    @PostMapping("/grade")
    public ResponseEntity<String> uploadFileAndGradeMeat(@RequestParam("file") MultipartFile file) {
        try {
            String grade = meatGradeService.gradeMeat(file);
            return ResponseEntity.ok(grade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process the image: " + e.getMessage());
        }
    }
}