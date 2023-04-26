package application.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookResponse {

    private String isbn;
    private String authorName;
    private String title;
    private String description;
    private LocalDate releaseDate;

}
