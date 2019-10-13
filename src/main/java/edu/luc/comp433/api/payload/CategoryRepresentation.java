package edu.luc.comp433.api.payload;

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
}
