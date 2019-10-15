package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {

    private long id;

    @Size(min = 2, max = 100)
    private String name;

    private String website;

    private String email;

    public SellerDTO(@NonNull @NotNull @Size(min = 2, max = 100) String name, String website, String email) {
        this.name = name;
        this.website = website;
        this.email = email;
    }

    public SellerDTO(long id) {
        this.id = id;
    }

    public static SellerDTO of(Seller seller) {
        return new SellerDTO(seller.getId(), seller.getName(), seller.getWebsite(), seller.getEmail());
    }
}
