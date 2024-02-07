package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_loggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901", 1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKonti()).thenReturn(konti);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentAlleKonti_IkkeloggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_loggetInn() {
        // arrange
        Konto nyKonto = new Konto("105010123456", "01010110523", 1000, "Sparekonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKonto(nyKonto)).thenReturn("Konto registrert");

        // act
        String resultat = adminKontoController.registrerKonto(nyKonto);

        // assert
        assertEquals("Konto registrert", resultat);
    }

    @Test
    public void registrerKonto_IkkeloggetInn() {
        // arrange
        Konto nyKonto = new Konto("105010123456", "01010110523", 1000, "Sparekonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.registrerKonto(nyKonto);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }

    // Similar tests for endreKonto and slettKonto methods can be added.
}
