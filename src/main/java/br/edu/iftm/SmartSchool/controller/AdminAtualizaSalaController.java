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
import br.edu.iftm.SmartSchool.model.Sala;
import br.edu.iftm.SmartSchool.repository.SalaRepository;

@Controller
public class AdminAtualizaSalaController {

    @Autowired
    SalaRepository repoS;

    @GetMapping (value = "cadastrosala")
    String cadastroSala(Model model){
        model.addAttribute("sala", new Sala());
        return "cadastroSala";
    }

    @PostMapping (value = "cadastrosala")
    public String gravarSala(@Valid Sala sala, BindingResult bindingResult, RedirectAttributes raS){
        if(bindingResult.hasErrors()) {
            return "cadastrosala";
        }
        try {
            repoS.gravarSala(sala);
            raS.addFlashAttribute("sucessmensage","Sala cadastrada com sucesso!");
            return "redirect:/cadastrosala";
        } catch (DataIntegrityViolationException e) {
            System.out.println("----------------> " + e.getMessage());
            raS.addFlashAttribute("dangermensage","Sala ja cadastrada!");
            return "redirect:/cadastrosala";
        } catch (Exception e) {
            System.out.println("----------------> " + e.getMessage());
            raS.addFlashAttribute("dangermensage","Erro catastrofico!");
            return "redirect:/cadastrosala";
        } 
    }

    
    @RequestMapping(value = "/mantersala", method = RequestMethod.POST)
	public String atualizarSala(@RequestParam(value = "cod_sala", required = true) String cod_sala, Sala sala, Model model) {
		Integer result = repoS.atualizarSala(cod_sala, sala);
        if(result != null && result > 0){
            model.addAttribute("sucessmensage", "Sala atualizada com sucesso!");
        }
        model.addAttribute("cod_sala", new Sala());
		return "mantersala";
	}

    @RequestMapping (value = "/mantersala", method = RequestMethod.GET)
	public String buscaDadosSala(@RequestParam(value = "identidadeSala", required = false) String identidadeSala, Model model) {
        Sala sala = new Sala();
        if(identidadeSala == null || identidadeSala.isEmpty()){
            model.addAttribute("sala", sala);
            return "mantersala";
        }else{
            if(identidadeSala != ""){
                Sala saC = repoS.buscaPorCodSala(identidadeSala);
                sala = saC;
            }
        }
        model.addAttribute("sala", sala);
        return "mantersala";
	}

	@RequestMapping(value = "/mantersala", method = RequestMethod.DELETE)
	public String excluirSala(@RequestParam(value = "cod_sala", required = true) String cod_sala, RedirectAttributes raS) {
		Integer result = repoS.excluirSala(cod_sala);

        if(result != null && result > 0){
            raS.addFlashAttribute("sucessmensage", "Sala excluida com sucesso!");
        }
		return "redirect:/mantersala";
	}
}
