package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;
import edu.luc.comp433.api.workflow.SellerActivity;
import edu.luc.comp433.api.ws.SellerWebService;
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
        return sellerActivity.getSeller(id);
    }

    @Override
    @POST
    @Path(value = "/sellers")
    @Consumes({"text/xml", "application/json"})
    public SellerRepresentation createSeller(SellerRequest sellerRequest) {
            return sellerActivity.createSeller(sellerRequest);
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
            return sellerActivity.update(id, sellerRequest);
    }

    @Override
    @DELETE
    @Path("/seller/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteSeller(@PathParam("id") long id) {
            sellerActivity.delete(id);
    }

    @Override
    @POST
    @Path(value = "/seller/{id}/addresses")
    @Consumes({"text/xml", "application/json"})
    public AddressRepresentation addAddress(@PathParam("id") long id, AddressRequest addressRequest) {
            return sellerActivity.addAddress(id, addressRequest);
    }

    @Override
    @DELETE
    @Path("/seller/{id}/address/{addressId}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void removeAddress(@PathParam("id") long id, @PathParam("addressId") long addressId) {
            sellerActivity.removeAddress(id, addressId);
    }

    @Override
    @GET
    @Path(value = "/seller/{id}/addresses")
    @Produces({"text/xml", "application/json"})
    public List<AddressRepresentation> getAddresses(@PathParam("id") long id) {
            return sellerActivity.getAddresses(id);
    }

    public SellerResource(SellerActivity sellerActivity) {
        this.sellerActivity = sellerActivity;
    }
}
