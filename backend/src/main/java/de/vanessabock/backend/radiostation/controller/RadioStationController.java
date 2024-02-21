package de.vanessabock.backend.radiostation.controller;

import de.vanessabock.backend.exception.NoSuchStationException;
import de.vanessabock.backend.exception.StationAlreadyInDatabaseException;
import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class RadioStationController {

    private final RadioStationService radioStationService;

    public RadioStationController(RadioStationService radioStationService) {
        this.radioStationService = radioStationService;
    }

    @GetMapping("getStations/{limit}")
    public List<RadioStation> getStationsWithLimit(@PathVariable int limit){
        return radioStationService.getRadioStations(limit);
    }

    @GetMapping("getStationsByName/{limit}")
    public List<RadioStation> getStationsBySearchNameWithLimit(@PathVariable int limit, @RequestParam String name) throws NoSuchStationException {
        return radioStationService.getRadioStationsBySearchName(limit, name);
    }

    @PostMapping
    public RadioStation addStation(@RequestBody RadioStation radioStation) throws StationAlreadyInDatabaseException {
        return radioStationService.addRadioStation(radioStation);
    }
}
