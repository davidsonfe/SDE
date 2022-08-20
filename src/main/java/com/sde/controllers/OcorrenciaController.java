package com.sde.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sde.models.Ocorrencia;
import com.sde.models.Professor;
import com.sde.repository.OcorrenciaRepository;
import com.sde.repository.ProfessorRepository;

@Controller
public class OcorrenciaController {

	@Autowired
	private OcorrenciaRepository or;
	
	@Autowired
	private ProfessorRepository pr;
	
	
	
	@RequestMapping(value="/cadastrarOcorrencia", method=RequestMethod.GET)
	public String form(){
		return "ocorrencia/formOcorrencia";
	}
	
	@RequestMapping(value="/cadastrarOcorrencia", method=RequestMethod.POST)
	public String form(@Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarOcorrencia";
		}
		
		or.save(ocorrencia);
		attributes.addFlashAttribute("mensagem", "ocorrência cadastrada com sucesso!");
		return "redirect:/cadastrarOcorrencia";
	}
	
	@RequestMapping("/ocorrencias")
	public ModelAndView listaOcorrencias(){
		ModelAndView mv = new ModelAndView("listaOcorrencias");
		Iterable<Ocorrencia> ocorrencias = or.findAll();
		mv.addObject("ocorrencias", ocorrencias);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesOcorrencia(@PathVariable("codigo") long codigo){
		Ocorrencia ocorrencia = or.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("ocorrencia/detalhesOcorrencia");
		mv.addObject("ocorrencia", ocorrencia);
		
		Iterable<Professor> professores = pr.findByOcorrencia(ocorrencia);
		//Iterable<Estudante> estudantes = er.findByOcorrencia(ocorrencia);
		mv.addObject("professores", professores);
		//mv.addObject("professores", estudantes);
		
		return mv;
	}
	
	@RequestMapping("/deletarOcorrencia")
	public String deletarOcorrencia(long codigo){
		Ocorrencia ocorrencia = or.findByCodigo(codigo);
		or.delete(ocorrencia);
		return "redirect:/ocorrencias";
	}
	
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesOcorrenciaPost(@PathVariable("codigo") long codigo, @Valid Professor professor, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		Ocorrencia ocorrencia = or.findByCodigo(codigo);
		professor.setOcorrencia(ocorrencia);
		//estudante.setOcorrencia(ocorrencia);
		pr.save(professor);
		//er.save(estudante);
		attributes.addFlashAttribute("mensagem", "Professor adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletarProfessor")
	public String deletarProfessor(String cod){
		Professor professor = pr.findByCod(cod);
		pr.delete(professor);
		
		Ocorrencia ocorrencia = professor.getOcorrencia();
		long codigoLong = ocorrencia.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}
	
	
}	
