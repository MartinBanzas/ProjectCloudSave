package com.martin.springboot.cloudsave.controller;

import com.martin.springboot.cloudsave.db_entities.Juego;
import com.martin.springboot.cloudsave.json_schemas.JuegoAddDTO;
import com.martin.springboot.cloudsave.service.FileStorageService;
import com.martin.springboot.cloudsave.service.JuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/juegos")
@Tag(name = "Games", description = "For every interaction related to game listings")
public class JuegoController {

	@Autowired
	private JuegoService juegoService;	@Autowired
	private FileStorageService storageService;

	// Recupera una página de juegos en lugar de una lista, rémora de cuando el proyecto servía plantillas HTML
	@Operation(summary = "Get a paginated  list", description = "Returns a list of games by pages")
	@GetMapping("/lista")
	public String listJuegos(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "12") int size,
			Model theModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Pageable pageable = PageRequest.of(page, size);

		Page<Juego> juegosPage = juegoService.findAll(pageable);
		List<Juego> juegos = juegosPage.getContent();

		Juego tempJuego = new Juego();
		String username = authentication.getName();
		theModel.addAttribute("username", username);
		theModel.addAttribute("rating", "");
		theModel.addAttribute("juegos", juegos);
		theModel.addAttribute("tempJuego", tempJuego);
		int maxPagesToShow = 3; // Número máximo de páginas para mostrar a la vez
		int totalPages = juegosPage.getTotalPages();
		int startPage = Math.max(0, page - maxPagesToShow / 2);
		int endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);
		theModel.addAttribute("currentPage", page);
		theModel.addAttribute("totalPages", totalPages);

		// Genera la lista de números de página
		List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
				.boxed()
				.collect(Collectors.toList());

		theModel.addAttribute("pageNumbers", pageNumbers);

		return "juegos/lista-juegos";
	}



	@Operation(summary = "Get a game's list", description = "Returns the full list of games")
	@GetMapping("/all")
	  public ResponseEntity <List<Juego>> listJuegos() {

		  Authentication authentication =
				  SecurityContextHolder.getContext().getAuthentication();
		  String username = authentication.getName();


		  List<Juego> theGames = juegoService.findAll();
		  Juego tempJuego = new Juego();


		return ResponseEntity.ok().body(theGames);


	  }

	@Operation(summary = "Basic POST to add a Game", description = "Adds a game with name, directory and system")
	@PostMapping("/addGame")
	public ResponseEntity <Juego> addGame(@RequestBody JuegoAddDTO game ) {

		Juego gameToAdd = new Juego();
		gameToAdd.setName(game.getName());
		gameToAdd.setDirectorio(game.getDirectorio());
		gameToAdd.setSistema(game.getSistema());

		juegoService.save(gameToAdd);

		return ResponseEntity.ok(gameToAdd);
	}

	// Obtener la lista de partidas.
	@Operation(summary = "Obtains a savegames list", description = "Returns a list of savegames from a single game")
	@GetMapping("/partidas/{id}")
	public String listaPartidas(@PathVariable("id") int id, Model theModel) {

		Juego theGame = juegoService.findById(id);





		return "juegos/partidas";
	}
	/*

	@PostMapping("/save")
	@Transactional
	public String saveJuego(@ModelAttribute("juego") Juego theGame) {
		theGame.setTerminado(false); // No puede tener nulos en esta columna o casca, o se hace así o se lanza un
										// trigger en SQL que ponga el valor de esa columna a 0.
		juegoService.save(theGame);

		return "redirect:/juegos/lista";
	}*/


	@Operation(summary = "Updates a game's basic parameters", description = "Updates name, system, directory")
	@PatchMapping("update/{id}")
	@Transactional
	public ResponseEntity <String> updateJuego(@PathVariable("id") int id, @RequestParam("name") String name,
			@RequestParam("sistema") String sistema, @RequestParam("directorio") String directorio) {

		Juego theGame = juegoService.findById(id);
		theGame.setName(name);
		theGame.setDirectorio(directorio);
		theGame.setSistema(sistema);
		juegoService.save(theGame);

		return ResponseEntity.ok("Game updated successfully");
	}

	@Operation(summary = "Deletes a game", description = "Deletes a game by ID")
	@PostMapping("/delete/{id}")
	@Transactional
	public ResponseEntity <String> deleteJuego(@PathVariable("id") int id) {

		Juego juego = juegoService.findById(id);

		if (juego != null) {
			//List<Partida> partidas = juego.getListaPartidas();

			//for (Partida partida : partidas) {
			//	storageService.delete(partida.getRutaarchivo());
			//}

			 // Hay que tratar los nulos o romperá al intentar borrar un juego que no tenga
								// imagen asociada, al ser null.
				String rutaImg = juego.getImgPath();
				storageService.deleteImg(rutaImg);

			juegoService.deleteById(id);

		}
		return ResponseEntity.ok("Game deleted successfully");

	}

	@Operation(summary = "Searches for a game", description = "Performs search by title ignoring Case")
	@GetMapping("/buscar")
	public ResponseEntity<List<Juego>> buscarJuegos(@RequestParam String nombre) {
		List<Juego> juegosEncontrados = juegoService.findByNombreContainingIgnoreCase(nombre);

		if (juegosEncontrados.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(juegosEncontrados);
	}

	@Operation(summary = "Marks a game as completed", description = "Marks a game as completed by setting a boolean to true. Keeps track of the date")
	@PostMapping("/completo")
	@Transactional
	public ResponseEntity <String> marcarCompleto(@RequestParam("id") int id, @RequestParam("fechaInicio") String fechaInicio,
			@RequestParam("fechaFin") String fechaFin) {

		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Juego theGame = juegoService.findById(id);

		try {
			Date fInicio = inputDateFormat.parse(fechaInicio);
			Date fFin = inputDateFormat.parse(fechaFin);
			theGame.setFInicio(fInicio);
			theGame.setFFin(fFin);
			theGame.setTerminado(true);
			juegoService.save(theGame);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return ResponseEntity.ok("Game updated successfully");
	}

	@Operation(summary = "Adds reviews", description = "Adds review for the specified game")
	@PostMapping("/review")
	public ResponseEntity <String> guardarReview(@RequestParam("gameId") int gameId,
			@RequestParam("rate") int rating,
			@RequestParam("reviewName") String reviewName,
			@RequestParam("reviewComments") String reviewComments) {

		Juego theGame = juegoService.findById(gameId);
		theGame.setPuntuacion(rating);
		theGame.setReview(reviewComments);
		theGame.setTituloReview(reviewName);
		juegoService.save(theGame);

		return ResponseEntity.ok("Review added successfully");
	}

}
