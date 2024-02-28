package de.vanessabock.backend.audio.controller;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private final UserService userService;

    public AudioController(RadioStationService radioStationService, UserService userService) {
        this.radioStationService = radioStationService;
        this.userService = userService;
    }

    @GetMapping("/play")
    public void playAudio(HttpServletResponse response, @RequestParam String stationuuid, @AuthenticationPrincipal OAuth2User principal) {
        RadioStation radioStation = radioStationService.getStationByUuid(stationuuid);

        // get current user if existing
        User user = userService.getLoggedInUser(principal);

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

            // read stream and send in chunks
            byte[] buffer = new byte[1024]; // size of the chunk
            int length;
            while ((length = stream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, length);
                response.flushBuffer();
            }
        } catch (IOException | URISyntaxException e) {
            double listenedTime = ((System.currentTimeMillis() - startTime) * 0.001);
            userService.addListenedTimeToUser(user, radioStation.getName(), listenedTime);
        }
        finally {
            // close connection
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
