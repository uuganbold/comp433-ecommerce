package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private long id;

    private String name;

    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO(long id) {
        this.id = id;
    }

    public static CategoryDTO of(Category c) {
        return new CategoryDTO(c.getId(), c.getName());
    }
}
