package edu.luc.comp433.business.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDTO {

    private long id;

    @NonNull
    private String name;
}
