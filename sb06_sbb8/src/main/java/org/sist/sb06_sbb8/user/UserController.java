package org.sist.sb06_sbb8.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/signup")
	public String signup( UserCreateForm userCreateForm ) {
		return "/user/signup_form";
	}

	@PostMapping("/signup")
	public String signup( @Valid UserCreateForm userCreateForm
			, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {	return "/user/signup_form"; }

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			
			return "/user/signup_form";
		}

		try {
			// 유효성 검사 통과~
			this.userService.create(userCreateForm.getUsername()
					, userCreateForm.getEmail()
					, userCreateForm.getPassword1());
			
		} catch (DataIntegrityViolationException e) {
			// 유효성 검사 통과 못함~
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미 등록된 사용자 입니다.");
			return "/user/signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "/user/signup_form";
		}
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "/user/login_form";
	}
	

}
