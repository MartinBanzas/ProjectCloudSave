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
		Juego tempJuego = new Juego();
		// add to the spring model
		theModel.addAttribute("juegos", theGames);
		theModel.addAttribute("tempJuego", tempJuego); // Hay que añadir un juego estándar al Modelo, vacío, para que no rompa con el forms modal de edición
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

	@PostMapping("update/{id}")
	@Transactional
	public String updateJuego(@PathVariable("id") int id, @RequestParam("name") String name, @RequestParam("sistema") String sistema, @RequestParam("directorio") String directorio) {
		System.out.println(id);

		Juego theGame = juegoService.findById(id);

		theGame.setName(name);
		theGame.setDirectorio(directorio);
		theGame.setSistema(sistema);
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

	@GetMapping("/buscar")
	public String buscarJuegos(@RequestParam String nombre, Model model) {
		System.out.println(nombre);
		List<Juego> juegosEncontrados = juegoService.findByNombreContainingIgnoreCase(nombre);
		model.addAttribute("juegos", juegosEncontrados);
		Juego tempJuego = new Juego();
		model.addAttribute(tempJuego);
		return "juegos/lista-juegos"; // Reemplaza con el nombre de tu vista HTML
	}

}












