package de.vanessabock.backend.audio.controller;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import de.vanessabock.backend.user.model.ListeningStatistic;
import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    private final RadioStationService radioStationService;
    private final UserService userService;

    public AudioController(RadioStationService radioStationService, UserService userService) {
        this.radioStationService = radioStationService;
        this.userService = userService;
    }

    @GetMapping("/play")
    public void playAudio(HttpServletResponse response, @RequestParam String stationuuid, @RequestParam Optional<String> userId) {
        RadioStation radioStation = radioStationService.getStationByUuid(stationuuid);

        // get current user if existing
        User user = null;
        if (userId.isPresent()){
            user = userService.getUserById(userId.get());
        }

        long startTime = 0;
        try {
            response.setStatus(HttpServletResponse.SC_OK);

            // URL of the radio-stream
            URI url = new URI(radioStation.getUrl_resolved());
            HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();
            connection.setRequestProperty("Icy-MetaData", "1");

            // open connection
            InputStream stream = connection.getInputStream();

            // User register start time
            startTime = System.currentTimeMillis();

            // activate chunked transfer encoding
            response.setContentType("audio/mpeg");
            response.setHeader("Transfer-Encoding", "chunked");

            // read stream and send in chunkd
            byte[] buffer = new byte[1024]; // size of the chunk
            int length;
            while ((length = stream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, length);
                response.flushBuffer(); // Wichtig, um den Chunk sofort zu senden
            }
        } catch (IOException | URISyntaxException e) {

            double listenedTime = ((System.currentTimeMillis() - startTime) * 0.001);

            if (user != null){
                if (user.listeningStatistics().stream().anyMatch(s -> s.stationName().equals(radioStation.getName()))){
                    List<ListeningStatistic> ls = user.listeningStatistics().stream().filter(s -> s.stationName().equals(radioStation.getName())).toList();
                    user.listeningStatistics().remove(ls.getFirst());
                    user.listeningStatistics().add(new ListeningStatistic(ls.getFirst().stationName(), ls.getFirst().listenedTime() + listenedTime));
                } else {
                    user.listeningStatistics().add(new ListeningStatistic(radioStation.getName(), listenedTime));
                }

                userService.updateUser(user);
            }
        }
        finally {
            // close connection
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
