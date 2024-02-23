package de.vanessabock.backend.audio.controller;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    private final RadioStationService radioStationService;

    public AudioController(RadioStationService radioStationService) {
        this.radioStationService = radioStationService;
    }

    @GetMapping("/play")
    public void playAudio(HttpServletResponse response, @RequestParam String stationuuid) {
        RadioStation radioStation = radioStationService.getStationByUuid(stationuuid);
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            // URL des Radio-Streams
            URI url = new URI(radioStation.getUrl_resolved());
            HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();
            connection.setRequestProperty("Icy-MetaData", "1");
            // Verbindung öffnen
            InputStream stream = connection.getInputStream();
            // Chunked Transfer Encoding aktivieren
            response.setContentType("audio/mpeg");
            response.setHeader("Transfer-Encoding", "chunked");
            // Stream lesen und in Chunks senden
            byte[] buffer = new byte[1024]; // Größe des Chunks
            int length;
            while ((length = stream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, length);
                response.flushBuffer(); // Wichtig, um den Chunk sofort zu senden
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Verbindung unterbrochen");
        }
        finally {
            // Verbindung schließen
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}