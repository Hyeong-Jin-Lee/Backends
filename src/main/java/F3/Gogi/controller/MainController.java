package F3.Gogi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @GetMapping("/main")
    public String getMainPage(Model model) {
        return "";  // main.html을 렌더링
    }

    @GetMapping("/main/{type}") //이거는 프론트에서 하면 됨
    public String getMeatInfo(@PathVariable String type, Model model) {
        String title = "";
        String description = "";
        String imageUrl = "";

        switch (type.toLowerCase()) {
            case "samgyeopsal":
                title = "삼겹살";
                description = "삼겹살에 대한 설명입니다.";
                imageUrl = "https://via.placeholder.com/600x400?text=Samgyeopsal";
                break;
            case "moksal":
                title = "목살";
                description = "목살에 대한 설명입니다.";
                imageUrl = "https://via.placeholder.com/600x400?text=Moksal";
                break;
            case "galbi":
                title = "갈비";
                description = "갈비에 대한 설명입니다.";
                imageUrl = "https://via.placeholder.com/600x400?text=Galbi";
                break;
            case "apdari":
                title = "앞다리";
                description = "앞다리에 대한 설명입니다.";
                imageUrl = "https://via.placeholder.com/600x400?text=Apdari";
                break;
            default:
                title = "Unknown";
                description = "알 수 없는 고기 종류입니다.";
                imageUrl = "https://via.placeholder.com/600x400?text=Unknown";
                break;
        }
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("imageUrl", imageUrl);
        return "meatinfo";  // meatinfo.html을 렌더링
    }
}