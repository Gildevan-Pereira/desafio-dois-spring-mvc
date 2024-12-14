package aac.br.springmvc_tres.controller;

import aac.br.springmvc_tres.config.security.custom.CustomUserDetails;
import aac.br.springmvc_tres.model.dto.request.RegistrationRequestDto;
import aac.br.springmvc_tres.model.dto.response.AddressDto;
import aac.br.springmvc_tres.model.dto.response.RegistrationResponseDto;
import aac.br.springmvc_tres.model.service.PostalCodeService;
import aac.br.springmvc_tres.model.service.StudentRegistrationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentRegistrationController {


    private final StudentRegistrationService registerStudentCourse;
    private final PostalCodeService postalCodeService;

    @PostMapping("/register_course/{id}")
    public ModelAndView registerInCourse(@ModelAttribute RegistrationRequestDto requestDto, @PathVariable("id") Integer userId) {

        AddressDto addressDto = postalCodeService.findAddressByCep(requestDto.getPostalCode());

        requestDto.setUserId(userId);
        requestDto.setCity(addressDto.getLocalidade());
        requestDto.setPostalCode(addressDto.getCep());
        requestDto.setStreet(addressDto.getLogradouro());
        requestDto.setNeighborhood(addressDto.getBairro());
        requestDto.setFu(addressDto.getUf());

        RegistrationResponseDto response = registerStudentCourse.registerStudentCourse(requestDto);

        ModelAndView modelAndView = new ModelAndView("studentRegistration");
        modelAndView.addObject("studentRegistration", response);
        modelAndView.addObject("userId", userId);

        return modelAndView;
    }

    @PostMapping("/edit_register/{id}")
    public ModelAndView editRegister(@ModelAttribute RegistrationRequestDto requestDto, @PathVariable("id") Integer userId) {

        RegistrationResponseDto response = registerStudentCourse.registerStudentCourse(requestDto);

        List<RegistrationResponseDto> list = registerStudentCourse.findAllSubscriptionByUserId(userId);

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("courses", list);
        modelAndView.addObject("userId", userId);

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id, Model model) {
        try {
            registerStudentCourse.deleteSubscriptionById(id);
            model.addAttribute("message", "Estudante excluído com sucesso!");
            model.addAttribute("alertType", "success");
        } catch (EntityNotFoundException e) {
            model.addAttribute("message", "Estudante não encontrado.");
            model.addAttribute("alertType", "danger");
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao excluir o estudante.");
            model.addAttribute("alertType", "danger");
        }
        return "courses";
    }

    @GetMapping("/delete/course/{id}")
    public ModelAndView deleteStudent(@ModelAttribute @PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        registerStudentCourse.deleteSubscriptionById(id);
        List<RegistrationResponseDto> list = registerStudentCourse.findAllSubscriptionByUserId(userDetails.getId());

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("courses", list);
        modelAndView.addObject("userId", userDetails.getId());
        return modelAndView;
    }

//    @GetMapping("/courses/update")
//    public ModelAndView updateList(@ModelAttribute @AuthenticationPrincipal CustomUserDetails userDetails) {
//
//        List<RegistrationResponseDto> list = registerStudentCourse.findAllSubscriptionByUserId(userDetails.getId());
//
//        ModelAndView modelAndView = new ModelAndView("home");
//
//        if (list.isEmpty()){
//            modelAndView.addObject("courses", list);
//        }
//
//        modelAndView.addObject("userId", userDetails.getId());
//
//        return modelAndView;
//    }

}
