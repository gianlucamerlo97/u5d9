package gianlucamerlo.u5d9.payloads;

import java.time.LocalDate;

public record NewAuthorsDTO(String name, String surname, String email, LocalDate dateOfBirth) {
}
