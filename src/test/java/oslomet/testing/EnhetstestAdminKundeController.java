package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    private AdminKundeController adminKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_loggetInn() {
        // arrange
        List<Kunde> kunder = new ArrayList<>();
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Osloveien 123", "1234", "Oslo", "98765432", "lene@email.com");
        Kunde kunde2 = new Kunde("03030340567", "Per", "Hansen", "Bergensgaten 456", "5678", "Bergen", "87654321", "per@email.com");
        kunder.add(kunde1);
        kunder.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKunder()).thenReturn(kunder);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kunder, resultat);
    }

    @Test
    public void hentAlle_IkkeloggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void lagreKunde_loggetInn() {
        // arrange
        Kunde nyKunde = new Kunde("04040450678", "Anne", "Olsen", "Trondheimsveien 789", "6789", "Trondheim", "76543210", "anne@email.com");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(any(Kunde.class))).thenReturn("Kunde lagret");

        // act
        String resultat = adminKundeController.lagreKunde(nyKunde);

        // assert
        assertEquals("Kunde lagret", resultat);
    }

    @Test
    public void lagreKunde_IkkeloggetInn() {
        // arrange
        Kunde nyKunde = new Kunde("04040450678", "Anne", "Olsen", "Trondheimsveien 789", "6789", "Trondheim", "76543210", "anne@email.com");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.lagreKunde(nyKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endre_loggetInn() {
        // arrange
        Kunde endretKunde = new Kunde("01010110523", "Lene", "Andersen", "Osloveien 123", "1234", "Oslo", "98765432", "lene@email.com");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Kunde endret");

        // act
        String resultat = adminKundeController.endre(endretKunde);

        // assert
        assertEquals("Kunde endret", resultat);
    }

    @Test
    public void endre_IkkeloggetInn() {
        // arrange
        Kunde endretKunde = new Kunde("01010110523", "Lene", "Andersen", "Osloveien 123", "1234", "Oslo", "98765432", "lene@email.com");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.endre(endretKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slett_loggetInn() {
        // arrange
        String personnummer = "03030340567";

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKunde(anyString())).thenReturn("Kunde slettet");

        // act
        String resultat = adminKundeController.slett(personnummer);

        // assert
        assertEquals("Kunde slettet", resultat);
    }

    @Test
    public void slett_IkkeloggetInn() {
        // arrange
        String personnummer = "03030340567";

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.slett(personnummer);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }
}

