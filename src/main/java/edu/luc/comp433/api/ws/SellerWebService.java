package edu.luc.comp433.api.ws;

import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface SellerWebService {

    SellerRepresentation getSeller(long id);

    SellerRepresentation createSeller(SellerRequest sellerRequest);

    List<SellerRepresentation> allSellers();

    SellerRepresentation updateSeller(long id, SellerRequest sellerRequest);

    void deleteSeller(long id);

    AddressRepresentation addAddress(long id, AddressRequest addressRequest);

    void removeAddress(long id, long addressId);

    List<AddressRepresentation> getAddresses(long id);


}
