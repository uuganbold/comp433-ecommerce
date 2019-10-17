package edu.luc.comp433.api.cxf.exception;

import edu.luc.comp433.exceptions.EntryNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class EntryNotFoundExceptionHandler implements ExceptionMapper<EntryNotFoundException> {

    @Override
    public Response toResponse(EntryNotFoundException e) {

        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());

        ApiError apiError =
                new ApiError(Response.Status.NOT_FOUND, e.getLocalizedMessage(), errors);

        return Response.status(apiError.getStatus()).header("exception", e.getMessage()).entity(apiError).build();
    }
}
