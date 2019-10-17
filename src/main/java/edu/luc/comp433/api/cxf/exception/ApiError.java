package edu.luc.comp433.api.cxf.exception;

import lombok.Getter;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;

@Getter
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiError {

    private Response.Status status;
    private String message;
    @XmlElement(name = "errors")
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
