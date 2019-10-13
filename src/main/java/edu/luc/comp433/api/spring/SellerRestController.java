package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;
import edu.luc.comp433.api.workflow.SellerActivity;
import edu.luc.comp433.api.ws.SellerWebService;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        SellerRepresentation seller = sellerActivity.getSeller(id);
        if (seller == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller Not Found");
        return seller;
    }

    @Override
    @PostMapping(value = "/sellers", consumes = {"text/xml", "application/json"})
    public SellerRepresentation createSeller(@Validated @RequestBody SellerRequest sellerRequest) {
        try {
            return sellerActivity.createSeller(sellerRequest);
        } catch (DuplicatedEntryException dive) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, dive.getMessage());
        }
    }

    @Override
    @GetMapping(value = "/sellers", produces = {"text/xml", "application/json"})
    public List<SellerRepresentation> allSellers() {
        return sellerActivity.listSeller();
    }

    @Override
    @PutMapping(value = "/seller/{id}", consumes = {"text/xml", "application/json"})
    public SellerRepresentation updateCategory(@PathVariable("id") long id, @Validated @RequestBody SellerRequest sellerRequest) {
        try {
            return sellerActivity.update(id, sellerRequest);
        } catch (EntryNotFoundException enf) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, enf.getMessage());
        } catch (DuplicatedEntryException dee) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, dee.getMessage());
        }
    }

    @Override
    @DeleteMapping("/seller/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeller(@PathVariable("id") long id) {
        try {
            sellerActivity.delete(id);
        } catch (EntryNotFoundException enf) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, nre.getMessage());
        }
    }

    @Override
    @PostMapping(value = "/seller/{id}/addresses", consumes = {"text/xml", "application/json"})
    public AddressRepresentation addAddress(@PathVariable("id") long id, @RequestBody AddressRequest addressRequest) {
        try {
            return sellerActivity.addAddress(id, addressRequest);
        } catch (EntryNotFoundException dive) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, dive.getMessage());
        }
    }

    @Override
    @DeleteMapping("/seller/{id}/address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddress(@PathVariable("id") long id, @PathVariable("addressId") long addressId) {
        try {
            sellerActivity.removeAddress(id, addressId);
        } catch (EntryNotFoundException enf) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, nre.getMessage());
        }
    }

    @Override
    @GetMapping(value = "/seller/{id}/addresses", produces = {"text/xml", "application/json"})
    public List<AddressRepresentation> getAddresses(@PathVariable("id") long id) {
        try {
            return sellerActivity.getAddresses(id);
        } catch (EntryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}








