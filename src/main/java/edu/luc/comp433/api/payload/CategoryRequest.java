package edu.luc.comp433.api.payload;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@XmlRootElement(name = "category")
public class CategoryRequest {

    @NonNull
    private String name;
}
