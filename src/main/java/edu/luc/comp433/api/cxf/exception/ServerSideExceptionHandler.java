package edu.luc.comp433.api.cxf.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class ServerSideExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {

        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());

        ApiError apiError =
                new ApiError(Response.Status.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), errors);

        return Response.status(apiError.getStatus()).header("exception", e.getMessage()).entity(apiError).build();
    }
}
