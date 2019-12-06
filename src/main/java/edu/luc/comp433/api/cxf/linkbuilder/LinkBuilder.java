package edu.luc.comp433.api.cxf.linkbuilder;

import org.springframework.hateoas.Link;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class LinkBuilder {

    private UriInfo uriInfo;
    private UriBuilder uriBuilder;
    private String rel;

    private LinkBuilder(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public static LinkBuilder get(UriInfo uriInfo) {
        return new LinkBuilder(uriInfo);
    }

    public LinkBuilder linkTo(Class service, String method) {
        this.uriBuilder = uriInfo.getBaseUriBuilder().path(service, method);
        return this;
    }

    public LinkBuilder withRel(String rel) {
        this.rel = rel;
        return this;
    }

    public LinkBuilder withSelfRel() {
        this.rel = "self";
        return this;
    }

    public Link build(Object... objects) {
        if (uriBuilder == null) uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = this.uriBuilder.build(objects);
        Link link = new Link(uri.toString(), this.rel);
        return link;
    }


}
