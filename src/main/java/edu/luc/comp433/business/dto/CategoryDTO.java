package edu.luc.comp433.business.dto;

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
}
