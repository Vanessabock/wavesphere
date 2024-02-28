package de.vanessabock.backend.audio.controller;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
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
            System.out.println("46");
            connection.setRequestProperty("Icy-MetaData", "1");
            System.out.println("48");

            // open connection
            InputStream stream = connection.getInputStream();
            System.out.println("52");

            // User register start time
            startTime = System.currentTimeMillis();
            System.out.println("56");

            // activate chunked transfer encoding
            response.setContentType("audio/mpeg");
            response.setHeader("Transfer-Encoding", "chunked");
            System.out.println("61");

            // read stream and send in chunks
            byte[] buffer = new byte[1024]; // size of the chunk
            int length;
            while ((length = stream.read(buffer)) != -1) {
                System.out.println("67");
                response.getOutputStream().write(buffer, 0, length);
                System.out.println("69");
                response.flushBuffer();
                System.out.println("71");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            double listenedTime = ((System.currentTimeMillis() - startTime) * 0.001);
            userService.addListenedTimeToUser(user, radioStation.getName(), listenedTime);
        }
        finally {
            // close connection
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("81");
        }
        System.out.println("83");
    }
}
