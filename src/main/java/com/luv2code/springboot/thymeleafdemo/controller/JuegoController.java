package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Juego;
import com.luv2code.springboot.thymeleafdemo.entity.Partida;
import com.luv2code.springboot.thymeleafdemo.service.FileStorageService;
import com.luv2code.springboot.thymeleafdemo.service.JuegoService;
import com.luv2code.springboot.thymeleafdemo.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/juegos")
public class JuegoController {

	@Autowired
	public JuegoController (JuegoService juegoService, PartidaService partidaService, FileStorageService storageService) {
		this.juegoService=juegoService;
		this.partidaService=partidaService;
		this.storageService=storageService;
	}


	private JuegoService juegoService;
	private PartidaService partidaService;
	private FileStorageService storageService;


	@GetMapping("/lista")
	public String listJuegos(Model theModel) {

 List<Juego> theGames = juegoService.findAll();

		// add to the spring model
		theModel.addAttribute("juegos", theGames);

		return "juegos/lista-juegos";
	}

	@GetMapping("/formAdd")
	public String formAdd(Model theModel) {


		Juego theGame = new Juego();
		theModel.addAttribute("juegos", theGame);
		return "juegos/formAdd";
	}

	@GetMapping("/partidas/{id}")
	public String listaPartidas (@PathVariable("id") int id, Model theModel) {

		List <Partida> listaPartidas = partidaService.findAll();

		//Partida partida = partidaService.findById(id);
		theModel.addAttribute("partida", listaPartidas);

		return "juegos/partidas";
	}

	@PostMapping("/save")
	public String saveJuego(@ModelAttribute("juegos") Juego theGame) {
		juegoService.save(theGame);

	return "redirect:/juegos/lista";
	}



}









