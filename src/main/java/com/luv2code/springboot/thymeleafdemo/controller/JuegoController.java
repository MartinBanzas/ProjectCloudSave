package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Juego;
import com.luv2code.springboot.thymeleafdemo.entity.Partida;
import com.luv2code.springboot.thymeleafdemo.service.FileStorageService;
import com.luv2code.springboot.thymeleafdemo.service.JuegoService;
import com.luv2code.springboot.thymeleafdemo.service.PartidaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/juegos")
public class JuegoController {


	@Autowired
	private JuegoService juegoService;
	@Autowired
	private PartidaService partidaService;
	@Autowired
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
	public String listaPartidas(@PathVariable("id") int id, Model theModel) {

		List<Partida> listaPartidas = partidaService.findAll();

		//Partida partida = partidaService.findById(id);
		theModel.addAttribute("partida", listaPartidas);

		return "juegos/partidas";
	}

	@PostMapping("/save")
	public String saveJuego(@ModelAttribute("juegos") Juego theGame) {
		juegoService.save(theGame);

		return "redirect:/juegos/lista";
	}

	@PostMapping("/delete/{id}")
	@Transactional
	public String deleteJuego(@PathVariable("id") int id) {

		Juego juego = juegoService.findById(id);

		if (juego != null) {
			List<Partida> partidas = juego.getListaPartidas();

			for (Partida partida : partidas) {
				// Eliminar el archivo asociado a la partida usando tu servicio de archivos
				storageService.delete(partida.getRutaarchivo());
			}
			String rutaImg = juego.getImg().getName();
			storageService.deleteImg(rutaImg);

			juegoService.deleteById(id);


		}
		return "redirect:/juegos/lista";

	}
}










