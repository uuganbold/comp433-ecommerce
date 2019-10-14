package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;
import edu.luc.comp433.api.workflow.SellerActivity;
import edu.luc.comp433.api.ws.SellerWebService;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import org.apache.cxf.jaxrs.ext.ResponseStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class SellerResource implements SellerWebService {

    private SellerActivity sellerActivity;

    @Override
    @GET
    @Path("/seller/{id}")
    public SellerRepresentation getSeller(@PathParam("id") long id) {
        SellerRepresentation seller = sellerActivity.getSeller(id);
        if (seller == null) throw new NotFoundException("Seller Not Found");
        return seller;
    }

    @Override
    @POST
    @Path(value = "/sellers")
    @Consumes({"text/xml", "application/json"})
    public SellerRepresentation createSeller(SellerRequest sellerRequest) {
        try {
            return sellerActivity.createSeller(sellerRequest);
        } catch (DuplicatedEntryException dive) {
            throw new WebApplicationException(dive.getMessage(), Response.Status.CONFLICT);
        }
    }

    @Override
    @GET
    @Path(value = "/sellers")
    @Produces({"text/xml", "application/json"})
    public List<SellerRepresentation> allSellers() {
        return sellerActivity.listSeller();
    }

    @Override
    @PUT
    @Path(value = "/seller/{id}")
    @Consumes({"text/xml", "application/json"})
    public SellerRepresentation updateSeller(@PathParam("id") long id, SellerRequest sellerRequest) {
        try {
            return sellerActivity.update(id, sellerRequest);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf.getMessage());
        } catch (DuplicatedEntryException dee) {
            throw new WebApplicationException(dee.getMessage(), Response.Status.CONFLICT);
        }
    }

    @Override
    @DELETE
    @Path("/seller/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteSeller(@PathParam("id") long id) {
        try {
            sellerActivity.delete(id);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new WebApplicationException(nre.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @Override
    @POST
    @Path(value = "/seller/{id}/addresses")
    @Consumes({"text/xml", "application/json"})
    public AddressRepresentation addAddress(@PathParam("id") long id, AddressRequest addressRequest) {
        try {
            return sellerActivity.addAddress(id, addressRequest);
        } catch (EntryNotFoundException dive) {
            throw new NotFoundException(dive.getMessage());
        }
    }

    @Override
    @DELETE
    @Path("/seller/{id}/address/{addressId}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void removeAddress(@PathParam("id") long id, @PathParam("addressId") long addressId) {
        try {
            sellerActivity.removeAddress(id, addressId);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new BadRequestException(nre.getMessage());
        }
    }

    @Override
    @GET
    @Path(value = "/seller/{id}/addresses")
    @Produces({"text/xml", "application/json"})
    public List<AddressRepresentation> getAddresses(@PathParam("id") long id) {
        try {
            return sellerActivity.getAddresses(id);
        } catch (EntryNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public SellerResource(SellerActivity sellerActivity) {
        this.sellerActivity = sellerActivity;
    }
}
