package aac.br.springmvc_tres.controller;

import aac.br.springmvc_tres.model.dto.request.RegistrationRequestDto;
import aac.br.springmvc_tres.model.dto.response.AddressDto;
import aac.br.springmvc_tres.model.dto.response.RegistrationResponseDto;
import aac.br.springmvc_tres.model.service.PostalCodeService;
import aac.br.springmvc_tres.model.service.StudentRegistrationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentRegistrationController {


    private final StudentRegistrationService registerStudentCourse;
    private final PostalCodeService postalCodeService;

    @PostMapping("/register_course")
    public ModelAndView registerInCourse(@ModelAttribute RegistrationRequestDto requestDto) {

        AddressDto addressDto = postalCodeService.findAddressByCep(requestDto.getPostalCode());

        requestDto.setCity(addressDto.getLocalidade());
        requestDto.setPostalCode(addressDto.getCep());
        requestDto.setStreet(addressDto.getLogradouro());
        requestDto.setNeighborhood(addressDto.getBairro());
        requestDto.setFu(addressDto.getUf());

        RegistrationResponseDto response = registerStudentCourse.registerStudentCourse(requestDto);

        ModelAndView modelAndView = new ModelAndView("studentRegistration");
        modelAndView.addObject("studentRegistration", response);

        return modelAndView;
    }

    @PostMapping("/edit_register")
    public ModelAndView editRegister(@ModelAttribute RegistrationRequestDto requestDto) {

        RegistrationResponseDto response = registerStudentCourse.registerStudentCourse(requestDto);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("index", response);

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id, Model model) {
        try {
            registerStudentCourse.deleteStudentById(id);
            model.addAttribute("message", "Estudante excluído com sucesso!");
            model.addAttribute("alertType", "success");
        } catch (EntityNotFoundException e) {
            model.addAttribute("message", "Estudante não encontrado.");
            model.addAttribute("alertType", "danger");
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao excluir o estudante.");
            model.addAttribute("alertType", "danger");
        }
        return "index";
    }

}
