package de.vanessabock.backend.radiostation.controller;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
