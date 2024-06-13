package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Img;
import com.luv2code.springboot.thymeleafdemo.entity.Juego;
import com.luv2code.springboot.thymeleafdemo.entity.Partida;
import com.luv2code.springboot.thymeleafdemo.service.FileStorageService;
import com.luv2code.springboot.thymeleafdemo.service.JuegoService;
import com.luv2code.springboot.thymeleafdemo.service.PartidaService;
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

@RestController
@RequestMapping("/juegos")
@Tag(name = "Games", description = "For every interaction related to game listings")
public class JuegoController {

	@Autowired
	private JuegoService juegoService;
	@Autowired
	private PartidaService partidaService;
	@Autowired
	private FileStorageService storageService;

	// Recupera una página de juegos en lugar de una lista
	@Operation(summary = "Get a hello message", description = "Returns a simple hello message")
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


	  @GetMapping("/all")
	  public ResponseEntity <List<Juego>> listJuegos() {

		  Authentication authentication =
				  SecurityContextHolder.getContext().getAuthentication();
		  String username = authentication.getName();


		  List<Juego> theGames = juegoService.findAll();
		  Juego tempJuego = new Juego();


		return ResponseEntity.ok().body(theGames);


	  }

	// Solicitud de formAdd
	@GetMapping("/formAdd")
	public String formAdd(Model theModel) {
		Juego theGame = new Juego();
		theModel.addAttribute("juegos", theGame);
		return "juegos/formAdd";
	}

	// Obtener la lista de partidas.
	@GetMapping("/partidas/{id}")
	public String listaPartidas(@PathVariable("id") int id, Model theModel) {

		Juego theGame = juegoService.findById(id);

		List<Partida> listaPartidas = partidaService.findAll();

		theModel.addAttribute("partida", listaPartidas);
		theModel.addAttribute("juego", theGame);

		return "juegos/partidas";
	}

	// Guardar un juego nuevo
	@PostMapping("/save")
	@Transactional
	public String saveJuego(@ModelAttribute("juego") Juego theGame) {
		theGame.setTerminado(false); // No puede tener nulos en esta columna o casca, o se hace así o se lanza un
										// trigger en SQL que ponga el valor de esa columna a 0.
		juegoService.save(theGame);

		return "redirect:/juegos/lista";
	}

	// Actualizar datos
	@PostMapping("update/{id}")
	@Transactional
	public String updateJuego(@PathVariable("id") int id, @RequestParam("name") String name,
			@RequestParam("sistema") String sistema, @RequestParam("directorio") String directorio) {
		System.out.println(id);

		Juego theGame = juegoService.findById(id);

		theGame.setName(name);
		theGame.setDirectorio(directorio);
		theGame.setSistema(sistema);
		juegoService.save(theGame);

		return "redirect:/juegos/lista";
	}

	// Borrar juego por id
	@PostMapping("/delete/{id}")
	@Transactional
	public String deleteJuego(@PathVariable("id") int id) {

		Juego juego = juegoService.findById(id);

		if (juego != null) {
			List<Partida> partidas = juego.getListaPartidas();

			for (Partida partida : partidas) {
				storageService.delete(partida.getRutaarchivo());
			}

			Img img = juego.getImg();

			if (img != null) { // Hay que tratar los nulos o romperá al intentar borrar un juego que no tenga
								// imagen asociada, al ser null.
				String rutaImg = img.getName();
				System.out.printf(rutaImg);
				storageService.deleteImg(rutaImg);
			}

			juegoService.deleteById(id);

		}
		return "redirect:/juegos/lista";

	}

	// Función de búsqueda por nombre/string
	@GetMapping("/buscar")
	public ResponseEntity<List<Juego>> buscarJuegos(@RequestParam String nombre) {
		List<Juego> juegosEncontrados = juegoService.findByNombreContainingIgnoreCase(nombre);

		if (juegosEncontrados.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(juegosEncontrados);
	}

	// Función de marcar completado
	@PostMapping("/completo")
	@Transactional
	public String marcarCompleto(@RequestParam("id") int id, @RequestParam("fechaInicio") String fechaInicio,
			@RequestParam("fechaFin") String fechaFin) {

		System.out.println(fechaInicio);
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

		return "redirect:/juegos/lista";
	}

	// Guardar las review
	@PostMapping("/review")
	public String guardarReview(@RequestParam("gameId") int gameId,
			@RequestParam("rate") int rating,
			@RequestParam("reviewName") String reviewName,
			@RequestParam("reviewComments") String reviewComments) {

		Juego theGame = juegoService.findById(gameId);
		theGame.setPuntuacion(rating);
		theGame.setReview(reviewComments);
		theGame.setTituloReview(reviewName);
		juegoService.save(theGame);

		return "redirect:/juegos/lista";
	}

}
