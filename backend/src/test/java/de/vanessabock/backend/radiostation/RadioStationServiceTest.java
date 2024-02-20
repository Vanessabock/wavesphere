package de.vanessabock.backend.radiostation;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class RadioStationServiceTest {

    RadioStationRepo radioStationRepo = Mockito.mock(RadioStationRepo.class);

    @Test
    void getRadioStationsTest_WhenLimit1_ReturnListWith1Object(){
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getRadioStations(1);

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsBySearchNameTest_WhenSearchNameBayInDatabaseAndLimit1_ReturnListWith1Object(){
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern 3", "www.radio.mp3", "www.radio.com", "icon")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getRadioStationsBySearchName(1, "Bay");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("1234", "Bayern 3", "www.radio.mp3", "www.radio.com", "icon"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsBySearchNameTest_WhenSearchNameBayNotInDatabaseAndLimit1_ReturnEmptyList(){
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getRadioStationsBySearchName(1, "Bay");

        //THEN
        assertThat(actual).isEmpty();
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void addStationTest_ifStationUuidIsEmpty_GenerateNewUuid(){
        //GIVEN
        when(radioStationRepo.save(Mockito.any(RadioStation.class))).thenReturn(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon"));
        RadioStation radioStation = new RadioStation("", "Radio", "www.radio.mp3", "www.radio.com", "icon");
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        RadioStation actual = radioStationService.addRadioStation(radioStation);

        //THEN
        assertThat(actual).isEqualTo(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon"));
        verify(radioStationRepo, times(1)).save(Mockito.any());
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void addStationTest_ifStationUuidIsNotEmpty_SaveStation(){
        //GIVEN
        Mockito.when(radioStationRepo.save(Mockito.any(RadioStation.class))).thenReturn(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon"));
        RadioStation radioStation = new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon");
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        RadioStation actual = radioStationService.addRadioStation(radioStation);

        //THEN
        assertThat(actual).isEqualTo(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon"));
        verify(radioStationRepo, times(1)).save(Mockito.any());
        verifyNoMoreInteractions(radioStationRepo);
    }
}
