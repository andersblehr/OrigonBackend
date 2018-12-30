package co.origon.api.exception;

import co.origon.api.common.BasicAuthCredentials;
import co.origon.api.common.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    public Response toResponse(WebApplicationException e) {
        Session.dispose();
        BasicAuthCredentials.dispose();
        if (Status.fromStatusCode(e.getResponse().getStatus()) != Status.NOT_FOUND) {
            Session.log(Level.WARNING, e.getResponse().getStatus() + ": " + e.getMessage());
        }
        return e.getResponse();
    }
}