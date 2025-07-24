package gianlucamerlo.u5d9.exceptions;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorsPayload {
    private String message;
    private LocalDateTime timestamp;
}