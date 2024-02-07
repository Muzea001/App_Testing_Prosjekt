package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository repository;

    @Mock
    private HttpSession session;

    @Test
    public void sjekkLoggInn_ValidCredentials() {
        // arrange
        String personnummer = "01010110523";
        String passord = "Pass123";

        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("OK");

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void sjekkLoggInn_InvalidPersonnummer() {
        // arrange
        String personnummer = "invalid_personnummer";
        String passord = "Pass123";

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void sjekkLoggInn_InvalidPassord() {
        // arrange
        String personnummer = "01010110523";
        String passord = "short";

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i passord", resultat);
    }

    // Similar tests for other scenarios can be added.

    @Test
    public void loggInnAdmin_ValidCredentials() {
        // arrange
        String bruker = "Admin";
        String passord = "Admin";

        // act
        String resultat = sikkerhet.loggInnAdmin(bruker, passord);

        // assert
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void loggInnAdmin_InvalidCredentials() {
        // arrange
        String bruker = "InvalidUser";
        String passord = "InvalidPass";

        // act
        String resultat = sikkerhet.loggInnAdmin(bruker, passord);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void loggetInn_UserLoggedIn() {
        // arrange
        when(session.getAttribute("Innlogget")).thenReturn("01010110523");

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertEquals("01010110523", resultat);
    }

    @Test
    public void loggetInn_UserNotLoggedIn() {
        // arrange
        when(session.getAttribute("Innlogget")).thenReturn(null);

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertEquals(null, resultat);
    }

    // Similar tests for other methods can be added.
}

