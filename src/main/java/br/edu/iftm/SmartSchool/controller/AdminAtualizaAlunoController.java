package br.edu.iftm.SmartSchool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.iftm.SmartSchool.helper.Utils;
import br.edu.iftm.SmartSchool.model.Aluno;
import br.edu.iftm.SmartSchool.repository.AlunoRepository;

@Controller
public class AdminAtualizaAlunoController {

    @Autowired
    AlunoRepository repo;

    @GetMapping(value = "cadastroaluno")
    String cadastroAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "cadastroaluno";
    }

    @PostMapping(value = "cadastroaluno")
    public String gravarAluno(@Valid Aluno aluno, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "cadastroaluno";
        }
        try {
            repo.gravaAluno(aluno);
            ra.addFlashAttribute("sucessmensage","Aluno cadastrado com sucesso!");
            return "redirect:/cadastroaluno";
        } catch (DataIntegrityViolationException e) {
            System.out.println("----------------> " + e.getMessage());
            ra.addFlashAttribute("dangermensage","Já existe um usuário com este nome!");
            return "redirect:/cadastroaluno";
        } catch (Exception e) {
            System.out.println("----------------> " + e.getMessage());
            ra.addFlashAttribute("dangermensage","Erro catastrofico!");
            return "redirect:/cadastroaluno";
        } 
    }

    @RequestMapping(value = "/manteralunos", method = RequestMethod.GET)
    public String buscaDadosAluno(@RequestParam(value = "identidadeAluno", required = false) String identidadeAluno,
            Model model) {
        Aluno aluno = new Aluno();
        if (identidadeAluno == null || identidadeAluno.isEmpty()) {
            model.addAttribute("aluno", aluno);
            return "manteralunos";
        }
        identidadeAluno = identidadeAluno.replace(".", "").replace("-", "");
        // Verifica se e CPF ou NOME//
        if (Utils.isLong(identidadeAluno)) {
            // Validação deu CPF na busca//
            if (identidadeAluno.length() < 11 || identidadeAluno.length() > 11) {
                model.addAttribute("aluno", aluno);
                return "manteralunos";
            }
            Aluno a = repo.buscaPorCpf(identidadeAluno);
            if (a != null) {
                aluno = a;
            }
        } else {
            // Validação deu nome na busca//
            Aluno a = repo.buscaPorLogin(identidadeAluno);
            if (a != null) {
                aluno = a;
            }
        }
        model.addAttribute("aluno", aluno);
        return "manteralunos";
    }

    @RequestMapping(value = "/manteralunos", method = RequestMethod.POST)
    public String atualizarAluno(@RequestParam(value = "usuario.cpf", required = true) String cpf, Aluno aluno,
            Model model) {
        Integer result = repo.atualizarAluno(cpf, aluno);
        if (result != null && result > 0) {
            model.addAttribute("sucessmensage", "Aluno atualizado com sucesso!");
        }
        model.addAttribute("alunoModel", new Aluno());
        return "manteralunos";
    }

    @RequestMapping(value = "/manteralunos", method = RequestMethod.DELETE)
    public String excluirAluno(@RequestParam(value = "login", required = true) String login, Model model,
            RedirectAttributes ra) {
        Integer result = repo.excluirAluno(login);

        if (result != null && result > 0) {
            ra.addFlashAttribute("sucessmensage", "Aluno excluido com sucesso!");
        }
        return "redirect:/manteralunos";
    }
}
