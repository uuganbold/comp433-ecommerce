package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.luc.comp433.business.dto.CategoryDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRepresentation extends RepresentationModel<CategoryRepresentation> {

    @NonNull
    private long id;

    @NonNull
    private String name;

    public static CategoryRepresentation of(CategoryDTO categoryDTO) {
        if (categoryDTO == null) return null;
        return new CategoryRepresentation(categoryDTO.getId(), categoryDTO.getName());
    }
}
