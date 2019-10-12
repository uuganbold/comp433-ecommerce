package edu.luc.comp433.api.representation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CategoryRequest {

    @NonNull
    private String name;
}
