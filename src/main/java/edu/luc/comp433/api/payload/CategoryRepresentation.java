package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.CategoryDTO;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CategoryRepresentation {

    @NonNull
    private long id;

    @NonNull
    private String name;

    public static CategoryRepresentation of(CategoryDTO categoryDTO) {
        if (categoryDTO == null) return null;
        return new CategoryRepresentation(categoryDTO.getId(), categoryDTO.getName());
    }
}
