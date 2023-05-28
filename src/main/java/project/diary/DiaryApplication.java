package project.diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class DiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiaryApplication.class, args);
	}

	@Controller
	public static class HomeController {

		@GetMapping("/")
		public String login(Model model) {
			// 모델에 필요한 데이터를 추가할 수도 있습니다.
			model.addAttribute("message", "Welcome to the Diary Application!");
			return "Login.html";
		}
	}
	@Controller
	public static class SignupController {

		@GetMapping("/api/user/signup")
		public String signup() {
			return "Signup.html";
		}
	}

	@Controller
	public static class CalendarController {

		@GetMapping("/api/calendar")
		public String calendar() {
			return "Calendar.html";
		}
	}


	@Controller
	public static class MyPageController {

		@GetMapping("/api/user/mypage/{userLoginId}/my")
		public String myPage() {
			return "MyPage.html";
		}
	}




}
