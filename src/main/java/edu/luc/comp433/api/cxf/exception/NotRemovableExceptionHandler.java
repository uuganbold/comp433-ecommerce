package edu.luc.comp433.api.cxf.exception;

import edu.luc.comp433.exceptions.NotRemovableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class NotRemovableExceptionHandler implements ExceptionMapper<NotRemovableException> {

    @Override
    public Response toResponse(NotRemovableException e) {

        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());

        ApiError apiError =
                new ApiError(Response.Status.BAD_REQUEST, e.getLocalizedMessage(), errors);

        return Response.status(apiError.getStatus()).header("exception", e.getMessage()).entity(apiError).build();
    }
}
