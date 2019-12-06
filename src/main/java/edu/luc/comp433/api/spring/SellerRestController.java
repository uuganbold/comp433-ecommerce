package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;
import edu.luc.comp433.api.workflow.SellerActivity;
import edu.luc.comp433.api.ws.SellerWebService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SellerRestController implements SellerWebService {

    private SellerActivity sellerActivity;

    public SellerRestController(SellerActivity sellerActivity) {
        this.sellerActivity = sellerActivity;
    }

    @Override
    @GetMapping("/seller/{id}")
    public SellerRepresentation getSeller(@PathVariable long id) {
        SellerRepresentation seller = sellerActivity.getSeller(id);
        return withLinks(seller);
    }

    @Override
    @PostMapping(value = "/sellers", consumes = {"application/json"}, produces = {"application/hal+json"})
    public SellerRepresentation createSeller(@Validated @RequestBody SellerRequest sellerRequest) {
        SellerRepresentation seller = sellerActivity.createSeller(sellerRequest);
        return withLinks(seller);
    }

    @Override
    @GetMapping(value = "/sellers", produces = {"application/hal+json"})
    public List<SellerRepresentation> allSellers() {
        List<SellerRepresentation> sellers = sellerActivity.listSeller();
        return withLinks(sellers);
    }

    @Override
    @PutMapping(value = "/seller/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public SellerRepresentation updateSeller(@PathVariable("id") long id, @Validated @RequestBody SellerRequest sellerRequest) {
        SellerRepresentation seller = sellerActivity.update(id, sellerRequest);
        return withLinks(seller);
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
        AddressRepresentation address = sellerActivity.addAddress(id, addressRequest);
        return withLinks(id, address);
    }

    @Override
    @GetMapping(value = "/seller/{id}/address/{addressId}", produces = {"application/hal+json"})
    public AddressRepresentation getAddress(@PathVariable("id") long id, @PathVariable("addressId") long addressId) {
        AddressRepresentation address = sellerActivity.getAddress(id, addressId);
        return withLinks(id, address);
    }

    @Override
    @DeleteMapping("/seller/{id}/address/{addressId}")
    public ResponseEntity<Void> removeAddress(@PathVariable("id") long id, @PathVariable("addressId") long addressId) {
            sellerActivity.removeAddress(id, addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping(value = "/seller/{id}/addresses", produces = {"application/hal+json"})
    public List<AddressRepresentation> getAddresses(@PathVariable("id") long id) {
        List<AddressRepresentation> addresses = sellerActivity.getAddresses(id);
        return withLinks(id, addresses);
    }

    protected SellerRepresentation withLinks(SellerRepresentation seller) {
        seller.add(linkTo(methodOn(SellerRestController.class).getSeller(seller.getId())).withSelfRel());
        seller.add(linkTo(methodOn(SellerRestController.class).allSellers()).withRel("all"));
        seller.add(linkTo(methodOn(SellerRestController.class).getAddresses(seller.getId())).withRel("addresses"));
        return seller;
    }

    protected AddressRepresentation withLinks(long sellerId, AddressRepresentation address) {
        address.add(linkTo(methodOn(SellerRestController.class).removeAddress(sellerId, address.getId())).withRel("remove"));
        address.add(linkTo(methodOn(SellerRestController.class).getAddress(sellerId, address.getId())).withSelfRel());
        address.add(linkTo(methodOn(SellerRestController.class).getSeller(sellerId)).withRel("seller"));
        address.add(linkTo(methodOn(SellerRestController.class).getAddresses(sellerId)).withRel("all"));
        return address;
    }

    protected List<AddressRepresentation> withLinks(long id, List<AddressRepresentation> addresses) {
        addresses.forEach(a -> withLinks(id, a));
        return addresses;
    }

    protected List<SellerRepresentation> withLinks(List<SellerRepresentation> sellers) {
        sellers.forEach(this::withLinks);
        return sellers;
    }
}








