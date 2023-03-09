package com.tmtd.tmtdspring.Controllers;
import com.tmtd.tmtdspring.Models.User;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import com.tmtd.tmtdspring.Services.*;
import com.tmtd.tmtdspring.Models.*;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

	private final ConfirmationTokenService confirmationTokenService;

	@GetMapping("/sign-in")
	String signIn() {

		return "sign-in";
	}

	@GetMapping("/sign-up")
	String signUpPage(User user) {

		return "sign-up";
	}

	@PostMapping("/sign-up")
	String signUp(User user) {

		userService.signUpUser(user);

		return "redirect:/sign-in";
	}

	@GetMapping("/sign-up/confirm")
	String confirmMail(@RequestParam("token") String token) {

		Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

		optionalConfirmationToken.ifPresent(userService::confirmUser);

		return "redirect:/sign-in";
	}

}
