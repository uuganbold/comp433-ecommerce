package edu.luc.comp433.api.cxf.exception;

import lombok.Getter;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Getter
public class ApiError {

    private Response.Status status;
    private String message;
    private List<String> errors;

    public ApiError(Response.Status status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(Response.Status status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public ApiError() {
    }
}
