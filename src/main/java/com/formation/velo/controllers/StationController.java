package com.formation.velo.controllers;


import com.formation.velo.model.Station;
import com.formation.velo.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class StationController {
	
	private final StationService stationService;

	public StationController(StationService stationService) {
		this.stationService = stationService;
	}


	@GetMapping("stations")
	public ResponseEntity<List<Station>> getAll(){

        stationService.saveRecords();
		List<Station> stations = stationService.findAll();

		return ResponseEntity.ok(stations);
	}

	@GetMapping("stations/{id}")
	public ResponseEntity<Optional<Station>> getPersoneById(@PathVariable Integer id){
		Optional<Station> station = stationService.findById(id);
		
		return ResponseEntity.ok(station);
	}

	@PostMapping("stations/add")
	public ResponseEntity<Station> add(@RequestParam String name,@RequestParam String address,@RequestParam Double latitude,@RequestParam Double longitude, @RequestParam String status, @RequestParam Integer bikeStands, @RequestParam Integer availableBikeStands, @RequestParam Integer availableBikes, @RequestParam String recordId){

		Station station = stationService.save(Station.builder().name(name).address(address).latitude(latitude).longitude(longitude).status(status).bikeStands(bikeStands).availableBikeStands(availableBikeStands).availableBikes(availableBikes).recordId(recordId).build());
		return ResponseEntity.ok(station);
	}



	@DeleteMapping("stations/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		stationService.deleteById(id);
		return ResponseEntity.ok("deleted");
	}

	@PostMapping("stations/update")
	public ResponseEntity<String> update(@RequestBody Station station){
		stationService.save(station);
		return ResponseEntity.ok("updated");
	}

// FRONT

	// @GetMapping("stations/list")
	// public String showUpdateForm(Model model) {
	// 	model.addAttribute("stations", stationService.findAll());
	// 	return "index";
	// }
	// @PostMapping("stations/addstation")
	// public String addStation(@Valid Station station, BindingResult result, Model model) {
	// 	if (result.hasErrors()) {
	// 		return "add-station";
	// 	}

	// 	stationService.save(station);
	// 	return "redirect:list";
	// }

	// @GetMapping("stations/edit/{id}")
	// public String edit(@PathVariable("id") Integer id, Model model) {
	// 	Station station = stationService.findById(id)
	// 			.orElseThrow(() -> new IllegalArgumentException("Invalid station Id:" + id));
	// 	model.addAttribute("station", station);
	// 	return "update-station";
	// }

	// @GetMapping("stations/view/{id}")
	// public String view(@PathVariable("id") Integer id, Model model) {
	// 	Station station = stationService.findById(id)
	// 			.orElseThrow(() -> new IllegalArgumentException("Invalid station Id:" + id));
	// 	model.addAttribute("station", station);
	// 	return "view-station";
	// }

	// @PostMapping("stations/update/{id}")
	// public String updateStation(@PathVariable("id") Integer id, @Valid Station station, BindingResult result,
	// 							Model model) {
	// 	if (result.hasErrors()) {
	// 		station.setId(id);
	// 		return "index";
	// 	}

	// 	stationService.save(station);
	// 	model.addAttribute("stations", stationService.findAll());
	// 	return "index";
	// }
	// @GetMapping("stations/delete/{id}")
	// public String deleteStation(@PathVariable("id") Integer id, Model model) {
	// 	Station station = stationService.findById(id)
	// 			.orElseThrow(() -> new IllegalArgumentException("Invalid station Id:" + id));
	// 	stationService.delete(station);
	// 	model.addAttribute("stations", stationService.findAll());
	// 	return "index";
	// }

	// @GetMapping("stations/signup")
	// public String showSignUpForm(Station station) {
	// 	return "add-station";
	// }













}
