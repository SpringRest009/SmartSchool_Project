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
import br.edu.iftm.SmartSchool.model.Professor;
import br.edu.iftm.SmartSchool.repository.ProfessorRepository;

@Controller
public class AdminAtualizaProfessorController {
    @Autowired
    ProfessorRepository repoP;

    @GetMapping (value = "cadastroprofessor")
    String cadastroProfessor(Model model){
        model.addAttribute("professor", new Professor());
        return "cadastroprofessor";
    }

    @PostMapping (value = "cadastroprofessor")
    public String gravarProfessor(@Valid Professor professor, BindingResult bindingResult, RedirectAttributes raP){
        if(bindingResult.hasErrors()) {
            return "cadastroprofessor";
        }
        try {
            repoP.gravarProfessor(professor);
            raP.addFlashAttribute("sucessmensage","Professor cadastrado com sucesso!");
            return "redirect:/cadastroprofessor";
        } catch (DataIntegrityViolationException e) {
            System.out.println("----------------> " + e.getMessage());
            raP.addFlashAttribute("dangermensage","Já existe um usuário com este nome!");
            return "redirect:/cadastroprofessor";
        } catch (Exception e) {
            System.out.println("----------------> " + e.getMessage());
            raP.addFlashAttribute("dangermensage","Erro catastrofico!");
            return "redirect:/cadastroprofessor";
        } 
    }

    @RequestMapping (value = "/manterprofessores", method = RequestMethod.GET)
	public String buscaDadosProfessor(@RequestParam(value = "identidadeProfessor", required = false) String identidadeProfessor, Model model) {
        Professor professor = new Professor();
        if(identidadeProfessor == null || identidadeProfessor.isEmpty()){
            model.addAttribute("professor", professor);
            return "manterprofessores";
        }
        identidadeProfessor = identidadeProfessor.replace(".", "").replace("-", "");
		//Verifica se e CPF ou NOME//
        if(Utils.isLong(identidadeProfessor)){
            //Validação deu CPF na busca//
            if(identidadeProfessor.length() < 11 || identidadeProfessor.length() > 11){
                model.addAttribute("professor", professor);
                return "manterprofessores";
            }
            Professor p = repoP.buscaPorCpfP(identidadeProfessor);
            if(p != null){
                professor = p;
            }
        } else{
            //Validação deu nome na busca//
            Professor p = repoP.buscaPorLoginP(identidadeProfessor);
            if(p != null){
                professor = p;
            }
        }
        model.addAttribute("professor", professor);
		return "manterprofessores";
	}

    @RequestMapping(value = "/manterprofessores", method = RequestMethod.POST)
	public String atualizarProfessor(@RequestParam(value = "usuario.cpf", required = true) String cpf, Professor professor, Model model) {
		Integer result = repoP.atualizarProfessor(cpf, professor);
        if(result != null && result > 0){
            model.addAttribute("sucessmensage", "Professor atualizado com sucesso!");
        }
        model.addAttribute("professorModel", new Professor());
		return "manterprofessores";
	}

	@RequestMapping(value = "/manterprofessores", method = RequestMethod.DELETE)
	public String excluirProfessor(@RequestParam(value = "login", required = true) String login, Model model, RedirectAttributes raP) {
		Integer result= repoP.excluirProfessor(login);

        if(result != null && result > 0){
            raP.addFlashAttribute("sucessmensage", "Professor excluido com sucesso!");
        }
		return "redirect:/manterprofessores";
	}
}
