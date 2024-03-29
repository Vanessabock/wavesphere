package de.vanessabock.backend.radiostation;

import de.vanessabock.backend.exception.NoSuchStationException;
import de.vanessabock.backend.exception.StationAlreadyInDatabaseException;
import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import de.vanessabock.backend.radiostation.service.RadioStationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class RadioStationServiceTest {

    RadioStationRepo radioStationRepo = Mockito.mock(RadioStationRepo.class);

    @Test
    void getRadioStationsTest_WhenLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "", "", "");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }
    @Test
    void getRadioStationsTest_WhenSearchNameBayAndLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "bay", "", "");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsTest_WhenCountrySomewhereAndLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "", "somewhere", "");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute", "somewhere"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsTest_WhenTagMusicAndLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music,pop", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "", "", "pop");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music,pop", "country"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsTest_WhenCountrySomewhereAndSearchNameMuAndLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music,pop", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "mu", "somewhere", "");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute", "somewhere"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsTest_WhenCountrySomewhereAndTagHipAndLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music,pop", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute,hip", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "", "somewhere", "hip");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute,hip", "somewhere"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsTest_WhenSearchNameMuAndTagHipAndLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music,pop", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute,hip", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "mu", "", "hip");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute,hip", "somewhere"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsTest_WhenSearchNameMuAndCountrySomewhereAndTagHipAndLimit1_ReturnListWith1Object() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music,pop", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute,hip", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<RadioStation> actual = radioStationService.getStations(1, "mu", "somewhere", "hip");

        //THEN
        assertThat(actual).containsExactlyInAnyOrder(new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute,hip", "somewhere"));
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getRadioStationsTest_WhenNoStationInDB_throwException() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(new RadioStation("1234", "Bayern", "www.radio.mp3", "www.radio.com", "icon", "music,pop", "country"),
                new RadioStation("5678", "Music", "www.music.mp3", "www.music.com", "pic", "mute,hip", "somewhere")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN & THEN
        Exception exception = assertThrows(NoSuchStationException.class, () -> radioStationService.getStations(1, "NotExisting", "NotExisting", "NotExisting"));

        String expectedMessage = "No stations found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getStationByUuidTest_whenStationInRepo_returnStation(){
        //GIVEN
        when(radioStationRepo.findByStationuuid(Mockito.any(String.class))).thenReturn(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        RadioStation actual = radioStationService.getStationByUuid("1234");

        //THEN
        assertThat(actual).isEqualTo(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        verify(radioStationRepo, times(1)).findByStationuuid(Mockito.any(String.class));
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getStationByUuidTest_whenStationNotInRepo_returnNull(){
        //GIVEN
        when(radioStationRepo.findByStationuuid(Mockito.any(String.class))).thenReturn(null);
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        RadioStation actual = radioStationService.getStationByUuid("1234");

        //THEN
        assertThat(actual).isNull();
        verify(radioStationRepo, times(1)).findByStationuuid(Mockito.any(String.class));
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getAllCountriesForFilterTest_WhenStationsWith2DiffCountriesInDB_ThenReturnABCSortedListWith2Countries() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(
                new RadioStation("1234", "RadioEs", "www.radio.mp3", "www.radio.com", "icon", "music", "Spain"),
                new RadioStation("1234", "RadioGer", "www.radio.mp3", "www.radio.com", "icon", "music", "Germany")));
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<String> actual = radioStationService.getAllCountriesForFilter();

        //THEN
        assertThat(actual).containsExactlyInAnyOrder("Show all", "Germany", "Spain");
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void getAllCountriesForFilterTest_WhenCountryIsNullOrEmptyOrAlreadyListed_ThenReturnListWith1Country() {
        //GIVEN
        Mockito.when(radioStationRepo.findAll()).thenReturn(List.of(
                new RadioStation("1234", "RadioGer", "www.radio.mp3", "www.radio.com", "icon", "music", "Germany"),
                new RadioStation("1234", "RadioEs", "www.radio.mp3", "www.radio.com", "icon", "music", null),
                new RadioStation("1234", "RadioEs", "www.radio.mp3", "www.radio.com", "icon", "music", ""),
                new RadioStation("1234", "RadioEs", "www.radio.mp3", "www.radio.com", "icon", "music", "Germany")));

        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        List<String> actual = radioStationService.getAllCountriesForFilter();

        //THEN
        assertThat(actual).containsExactlyInAnyOrder("Show all", "Germany");
        verify(radioStationRepo, times(1)).findAll();
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void addStationTest_ifStationUuidIsEmpty_GenerateNewUuid() throws StationAlreadyInDatabaseException {
        //GIVEN
        when(radioStationRepo.save(Mockito.any(RadioStation.class))).thenReturn(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        RadioStation radioStation = new RadioStation("", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country");
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        RadioStation actual = radioStationService.addRadioStation(radioStation);

        //THEN
        assertThat(actual).isEqualTo(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        verify(radioStationRepo, times(1)).save(Mockito.any());
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void addStationTest_ifStationUuidIsNotEmpty_SaveStation() throws StationAlreadyInDatabaseException {
        //GIVEN
        Mockito.when(radioStationRepo.save(Mockito.any(RadioStation.class))).thenReturn(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        RadioStation radioStation = new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country");
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN
        RadioStation actual = radioStationService.addRadioStation(radioStation);

        //THEN
        assertThat(actual).isEqualTo(new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country"));
        verify(radioStationRepo, times(1)).save(Mockito.any(RadioStation.class));
        verify(radioStationRepo, times(1)).existsRadioStationByStationuuid(Mockito.any(String.class));
        verifyNoMoreInteractions(radioStationRepo);
    }

    @Test
    void addStationTest_ifStationUuidIsNotEmptyButStationAlreadyInDatabase_ThrowException() {
        //GIVEN
        when(radioStationRepo.existsRadioStationByStationuuid(Mockito.any(String.class))).thenReturn(true);
        RadioStation radioStation = new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country");
        RadioStationService radioStationService = new RadioStationService(radioStationRepo);

        //WHEN & THEN
        Exception exception = assertThrows(StationAlreadyInDatabaseException.class, () -> radioStationService.addRadioStation(radioStation));

        String expectedMessage = "Station " + radioStation.getName() + " already exists in database.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
