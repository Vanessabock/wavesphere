package de.vanessabock.backend.station;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RadioStationServiceTest {

    RadioStationRepo radioStationRepo = Mockito.mock(RadioStationRepo.class);

    @Test
    void getRadioStationsTest_WhenLimit1_ReturnListWithOneObject(){
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getRadioStations(1);

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon"));
    }
}
