package application.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationMessages {

    public static final String ISBN_REQUIRED = "ISBN is required";
    public static final String AUTHOR_NAME_REQUIRED = "Author name is required";
    public static final String TITLE_REQUIRED = "Title is required";
    public static final String DESCRIPTION_REQUIRED = "Description is required";
    public static final String DESCRIPTION_STRING_LIMIT = "Description higher than limit (1000)";
    public static final String RELEASE_DATE_REQUIRED = "Rlease date is required";

}
