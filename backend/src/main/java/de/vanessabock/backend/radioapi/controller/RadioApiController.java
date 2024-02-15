package de.vanessabock.backend.radioapi.controller;

import de.vanessabock.backend.radioapi.model.ApiStation;
import de.vanessabock.backend.radioapi.service.RadioApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radioStations")
public class RadioApiController {

    private final RadioApiService radioApiService;

    public RadioApiController(RadioApiService radioApiService) {
        this.radioApiService = radioApiService;
    }

    @GetMapping
    public List<ApiStation> getStationsOrderedByVotes(@RequestParam String count) {
        return radioApiService.getStationsOrderedByVotes(count);
    }

    @GetMapping("/{search}")
    public List<ApiStation> getStationsBySearchName(@PathVariable String search, @RequestParam String count){
        return radioApiService.getStationsBySearchName(count, search);
    }
}
