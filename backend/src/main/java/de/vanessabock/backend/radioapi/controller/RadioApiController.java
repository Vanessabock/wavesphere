package de.vanessabock.backend.radioapi.controller;

import de.vanessabock.backend.exception.NoSuchStationException;
import de.vanessabock.backend.radioapi.service.RadioApiService;
import de.vanessabock.backend.radiostation.model.RadioStation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radioStations")
public class RadioApiController {

    private final RadioApiService radioApiService;

    public RadioApiController(RadioApiService radioApiService) {
        this.radioApiService = radioApiService;
    }

    @GetMapping("/{search}")
    public List<RadioStation> getStationsBySearchName(@PathVariable String search, @RequestParam String limit) throws NoSuchStationException {
        return radioApiService.getStationsBySearchName(limit, search);
    }
}
