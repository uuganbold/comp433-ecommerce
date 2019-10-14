package edu.luc.comp433.api.cxf;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Response.Status status;

        status = Response.Status.BAD_REQUEST;

        return Response.status(status).header("exception", e.getMessage()).build();
    }
}
