package edu.luc.comp433.api.workflow;

import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;
import edu.luc.comp433.business.SellerService;
import edu.luc.comp433.business.dto.AddressDto;
import edu.luc.comp433.business.dto.SellerDto;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SellerActivity {

    private SellerService sellerService;

    public SellerActivity(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    public SellerRepresentation getSeller(long id) {
        SellerDto dto = sellerService.getSeller(id);
        if (dto == null) throw new EntryNotFoundException("Seller not found with this id:" + id);
        return new SellerRepresentation(dto.getId(), dto.getName(), dto.getWebsite(), dto.getEmail());
    }

    public SellerRepresentation createSeller(SellerRequest sellerRequest) throws DuplicatedEntryException {
        SellerDto dto = new SellerDto(sellerRequest.getName(), sellerRequest.getWebsite(), sellerRequest.getEmail());
        dto = sellerService.createSeller(dto);
        return new SellerRepresentation(dto.getId(), dto.getName(), dto.getWebsite(), dto.getEmail());
    }

    public List<SellerRepresentation> listSeller() {
        List<SellerDto> dtos = sellerService.listAll();
        List<SellerRepresentation> result = new ArrayList<>();
        dtos.forEach(d -> result.add(new SellerRepresentation(d.getId(), d.getName(), d.getWebsite(), d.getEmail())));
        return result;
    }

    public SellerRepresentation update(long id, SellerRequest sellerRequest) throws EntryNotFoundException, DuplicatedEntryException {
        SellerDto dto = new SellerDto(id, sellerRequest.getName(), sellerRequest.getWebsite(), sellerRequest.getEmail());
        sellerService.save(dto);
        return new SellerRepresentation(dto.getId(), dto.getName(), dto.getWebsite(), dto.getEmail());
    }


    public void delete(long id) throws EntryNotFoundException, NotRemovableException {
        sellerService.delete(id);
    }

    public List<AddressRepresentation> getAddresses(long id) throws EntryNotFoundException {
        List<AddressDto> dtos = sellerService.listAddresses(id);
        List<AddressRepresentation> result = new ArrayList<>();
        dtos.forEach(d -> result.add(new AddressRepresentation(d.getId(), d.getCountry(), d.getStreet(), d.getUnit(), d.getCity(), d.getState(), d.getZipcode(), d.getPhonenumber())));
        return result;
    }

    public AddressRepresentation addAddress(long id, AddressRequest addressRequest) throws EntryNotFoundException {
        AddressDto dto = new AddressDto(addressRequest.getCountry(),
                addressRequest.getStreet(),
                addressRequest.getUnit(),
                addressRequest.getCity(),
                addressRequest.getState(),
                addressRequest.getZipcode(),
                addressRequest.getPhonenumber());
        dto = sellerService.addAddress(id, dto);
        return new AddressRepresentation(dto.getId(), dto.getCountry(), dto.getStreet(),
                dto.getUnit(), dto.getCity(), dto.getState(), dto.getZipcode(), dto.getPhonenumber());
    }

    public void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException {
        sellerService.removeAddress(id, addressId);
    }
}
