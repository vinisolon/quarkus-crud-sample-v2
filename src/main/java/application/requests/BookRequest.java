package application.requests;

import application.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = ValidationMessages.ISBN_REQUIRED)
    private String isbn;

    @NotBlank(message = ValidationMessages.AUTHOR_NAME_REQUIRED)
    private String authorName;

    @NotBlank(message = ValidationMessages.TITLE_REQUIRED)
    private String title;

    @NotBlank(message = ValidationMessages.DESCRIPTION_REQUIRED)
    @Size(max = 1000, message = ValidationMessages.DESCRIPTION_STRING_LIMIT)
    private String description;

    @NotBlank(message = ValidationMessages.RELEASE_DATE_REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;
}
