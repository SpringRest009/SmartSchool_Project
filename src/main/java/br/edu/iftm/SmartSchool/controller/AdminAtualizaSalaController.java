package br.edu.iftm.SmartSchool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.edu.iftm.SmartSchool.model.Sala;
import br.edu.iftm.SmartSchool.repository.SalaRepository;

@Controller
public class AdminAtualizaSalaController {

    @Autowired
    SalaRepository repoS;

    @GetMapping (value = "cadastrosala")
    String cadastroSala(Model model){
        model.addAttribute("sala", new Sala());
        return "cadastrosala";
    }

    @PostMapping (value = "cadastrosala")
    public String gravarSala(@Valid Sala sala, BindingResult bindingResult, RedirectAttributes raS){
        if(bindingResult.hasErrors()) {
            return "cadastrosala";
        }
        Integer validarS = sala.getCod_sala();

        if(repoS.buscaPorCodSala(validarS) != null){
            raS.addFlashAttribute("sucessmensage", "Sala já cadastrada!");
            return "cadastrosala";
        }else {
            repoS.gravarSala(sala);
            raS.addFlashAttribute("sucessmensage", "Sala cadastrada com sucesso!");
            return "redirect:/cadastrosala";
        }
    }

    @RequestMapping (value = "/mantersala", method = RequestMethod.GET)
	public String buscaDadosSala(@RequestParam(value = "identidadeSala", required = false) String identidadeSala, Model model) {
        Sala sala = new Sala();
        if(identidadeSala == null || identidadeSala.isEmpty()){
            model.addAttribute("sala", sala);
            return "manterSala";
        }
        identidadeSala = identidadeSala.replace(".", "").replace("-", "");

        if (Utils.isLong(identidadeSala)) {
            //Sala s = repoS.buscaPorCodSala(Integer.parseInt(identidadeSala));
            //if(s != null){
                //sala = s;
            //}
        model.addAttribute("sala", sala);
		return "manterSala";
        }
        return "manterSala";
	}

    @RequestMapping(value = "/mantersala", method = RequestMethod.POST)
	public String atualizarSala(@RequestParam(value = "cod_sala", required = true) Integer cod_sala, Model model) {
            model.addAttribute("sucessmensage", "Sala atualizado com sucesso!");
            model.addAttribute("salaModel", repoS.buscaPorCodSala(cod_sala));
		return "mantersala";
	}

	@RequestMapping(value = "/mantersala", method = RequestMethod.DELETE)
	public String excluirSala(@RequestParam(value = "cod_sala", required = true) Integer cod_sala, RedirectAttributes raS) {
            repoS.excluirSala(cod_sala);
            raS.addFlashAttribute("sucessmensage", "Sala excluida com sucesso!");
		return "redirect:/mantersala";
	}
}
