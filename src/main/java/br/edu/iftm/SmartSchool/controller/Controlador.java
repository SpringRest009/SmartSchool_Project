package br.edu.iftm.SmartSchool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Controlador {

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @RequestMapping("/aluno")
    String aluno() {
        return "pagaluno";
    }

    @RequestMapping("/ajustealuno")
    String ajuste() {
        return "ajuste";
    }
}
