package edu.luc.comp433.business.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class SellerDto {

    @NonNull
    @NotNull
    private long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    private String website;

    private String email;

    public SellerDto(@NonNull @NotNull @Size(min = 2, max = 100) String name, String website, String email) {
        this.name = name;
        this.website = website;
        this.email = email;
    }


}
