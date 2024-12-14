package aac.br.springmvc_tres.controller;

import aac.br.springmvc_tres.config.security.custom.CustomUserDetails;
import aac.br.springmvc_tres.model.dto.request.UserRequestDto;
import aac.br.springmvc_tres.model.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for handling home and login routes.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    /**
     * Handle GET requests to /home.
     * @return View name for home page
     */
    @GetMapping("/home")
    public String home() {
        return "home"; // Maps to home.html
    }

    /**
     * Handle GET requests to /login.
     * @return View name for login page
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // Maps to login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/course_registration")
    public ModelAndView courseRegistration(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Integer userId = userDetails.getId();
        var user = userService.findById(userId);

        ModelAndView modelAndView = new ModelAndView("courses");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView createUser(@ModelAttribute UserRequestDto requestDto) {
        userService.createUser(requestDto);

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("message", "User registered successfully!");

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        request.getSession().invalidate();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("message", "Logout realizado com sucesso!");

        return "login";
    }
}