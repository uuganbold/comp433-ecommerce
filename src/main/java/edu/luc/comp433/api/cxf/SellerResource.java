package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.cxf.linkbuilder.LinkBuilder;
import edu.luc.comp433.api.payload.AddressRepresentation;
import edu.luc.comp433.api.payload.AddressRequest;
import edu.luc.comp433.api.payload.SellerRepresentation;
import edu.luc.comp433.api.payload.SellerRequest;
import edu.luc.comp433.api.workflow.SellerActivity;
import edu.luc.comp433.api.ws.SellerWebService;
import org.apache.cxf.jaxrs.ext.ResponseStatus;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@CrossOriginResourceSharing(
        allowAllOrigins = true,
        maxAge = 200
)
public class SellerResource implements SellerWebService {

    private SellerActivity sellerActivity;

    @Override
    @GET
    @Path("/seller/{id}")
    @Produces("application/hal+json")
    public SellerRepresentation getSeller(@PathParam("id") long id) {
        return withLinks(sellerActivity.getSeller(id));
    }

    @Override
    @POST
    @Path(value = "/sellers")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public SellerRepresentation createSeller(SellerRequest sellerRequest) {
        return withLinks(sellerActivity.createSeller(sellerRequest));
    }

    @Override
    @GET
    @Path(value = "/sellers")
    @Produces("application/hal+json")
    public List<SellerRepresentation> allSellers() {
        return withLinks(sellerActivity.listSeller());
    }

    @Override
    @PUT
    @Path(value = "/seller/{id}")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public SellerRepresentation updateSeller(@PathParam("id") long id, SellerRequest sellerRequest) {
        return withLinks(sellerActivity.update(id, sellerRequest));
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
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public AddressRepresentation addAddress(@PathParam("id") long id, AddressRequest addressRequest) {
        return withLinks(id, sellerActivity.addAddress(id, addressRequest));
    }

    @Override
    @GET
    @Path("/seller/{id}/address/{addressId}")
    @Produces({"application/hal+json"})
    public AddressRepresentation getAddress(@PathParam("id") long id, @PathParam("addressId") long addressId) {
        return withLinks(id, sellerActivity.getAddress(id, addressId));
    }

    @Override
    @DELETE
    @Path("/seller/{id}/address/{addressId}")
    public Response removeAddress(@PathParam("id") long id, @PathParam("addressId") long addressId) {
        sellerActivity.removeAddress(id, addressId);
        return Response.noContent().build();
    }

    @Override
    @GET
    @Path(value = "/seller/{id}/addresses")
    @Produces("application/hal+json")
    public List<AddressRepresentation> getAddresses(@PathParam("id") long id) {
        return withLinks(id, sellerActivity.getAddresses(id));
    }

    public SellerResource(SellerActivity sellerActivity) {
        this.sellerActivity = sellerActivity;
    }

    @Context
    private UriInfo uriInfo;

    protected SellerRepresentation withLinks(SellerRepresentation seller) {
        seller.add(LinkBuilder.get(uriInfo).linkTo(SellerResource.class, "getSeller").withSelfRel().build(seller.getId()));
        seller.add(LinkBuilder.get(uriInfo).linkTo(SellerResource.class, "allSellers").withRel("all").build());
        seller.add(LinkBuilder.get(uriInfo).linkTo(SellerResource.class, "getAddresses").withRel("addresses").build(seller.getId()));
        return seller;
    }

    protected AddressRepresentation withLinks(long sellerId, AddressRepresentation address) {
        address.add(LinkBuilder.get(uriInfo).linkTo(SellerResource.class, "getAddress").withSelfRel().build(sellerId, address.getId()));
        address.add(LinkBuilder.get(uriInfo).linkTo(SellerResource.class, "getSeller").withRel("seller").build(sellerId));
        address.add(LinkBuilder.get(uriInfo).linkTo(SellerResource.class, "getAddresses").withRel("all").build(sellerId));
        address.add(LinkBuilder.get(uriInfo).linkTo(SellerResource.class, "removeAddress").withRel("remove").build(sellerId, address.getId()));
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
