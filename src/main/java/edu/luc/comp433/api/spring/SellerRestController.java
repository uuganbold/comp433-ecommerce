package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;
import edu.luc.comp433.api.workflow.SellerActivity;
import edu.luc.comp433.api.ws.SellerWebService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerRestController implements SellerWebService {

    private SellerActivity sellerActivity;

    public SellerRestController(SellerActivity sellerActivity) {
        this.sellerActivity = sellerActivity;
    }

    @Override
    @GetMapping("/seller/{id}")
    public SellerRepresentation getSeller(@PathVariable long id) {
        return sellerActivity.getSeller(id);
    }

    @Override
    @PostMapping(value = "/sellers", consumes = {"application/json"}, produces = {"application/hal+json"})
    public SellerRepresentation createSeller(@Validated @RequestBody SellerRequest sellerRequest) {
            return sellerActivity.createSeller(sellerRequest);
    }

    @Override
    @GetMapping(value = "/sellers", produces = {"application/hal+json"})
    public List<SellerRepresentation> allSellers() {
        return sellerActivity.listSeller();
    }

    @Override
    @PutMapping(value = "/seller/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public SellerRepresentation updateSeller(@PathVariable("id") long id, @Validated @RequestBody SellerRequest sellerRequest) {
            return sellerActivity.update(id, sellerRequest);
    }

    @Override
    @DeleteMapping("/seller/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeller(@PathVariable("id") long id) {
            sellerActivity.delete(id);
    }

    @Override
    @PostMapping(value = "/seller/{id}/addresses", consumes = {"application/json"}, produces = {"application/hal+json"})
    public AddressRepresentation addAddress(@PathVariable("id") long id, @RequestBody @Validated AddressRequest addressRequest) {
            return sellerActivity.addAddress(id, addressRequest);
    }

    @Override
    @DeleteMapping("/seller/{id}/address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddress(@PathVariable("id") long id, @PathVariable("addressId") long addressId) {
            sellerActivity.removeAddress(id, addressId);
    }

    @Override
    @GetMapping(value = "/seller/{id}/addresses", produces = {"application/hal+json"})
    public List<AddressRepresentation> getAddresses(@PathVariable("id") long id) {
            return sellerActivity.getAddresses(id);
    }
}








