package Spring.WebMVC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

	@GetMapping("/greet")
	public String greet(Model model) {
		model.addAttribute("message", "Hello from Spring Web MVC without Spring Boot!");
		return "greeting";
	}
}
