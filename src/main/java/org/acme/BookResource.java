package org.acme;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import org.jboss.resteasy.reactive.NoCache;
import jakarta.annotation.security.RolesAllowed;

@Path("/api/books")
@Tag(name = "books")
@Produces(APPLICATION_JSON)
public class BookResource {

    private static final Logger logger = Logger.getLogger(BookResource.class);
    @GET
    @RolesAllowed("user")
    @NoCache
    @Operation(summary = "Get all books")
    @APIResponse(
            responseCode = "200",
            description = "Gets all books",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY))
    )
    public Response getAll() {
        return Response.ok(Book.listAll()).build();

    }

    @GET
    @Path("/{isbn}")
    @Operation(summary = "Get one book by searching {isbn}")
    @Parameter(
            name = "isbn",
            description = "Unique ISBN code of a book",
            required = true,
            example = "978-0-321-76753-0",
            schema = @Schema(type = SchemaType.STRING))
    @APIResponse(
            responseCode = "200",
            description = "Get one book",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class))
    )
    @APIResponse(
            responseCode = "404",
            description = "No Book matching {isbn} found in the backend"
    )
    public Response getOne(@PathParam("isbn") String isbn) {
        Book entity = Book.findById(isbn);
        if (entity == null) {
            logger.debug("Book with id of " + isbn + " does not exist.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @POST
    @Operation(summary = "Create a new book")
    @Transactional
    @Consumes(APPLICATION_JSON)
    @Parameter(
            name = "book",
            description = "Book Object",
            required = true,
            example = "{\"title\":\"Don't Make Me Think\",\"genre\":\"Non-fiction\",\"isbn\":\"078-0-321-96551-6\",\"summary\":\"Since Don’t Make Me Think was first published in 2000....\"}",
            schema = @Schema(type = SchemaType.OBJECT))

    @APIResponse(
            responseCode = "201",
            description = "Book created",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Unable to honor a bad request, possibly invalid Book object"
    )
    public Response create(@Valid Book book) {
        try {
            book.persist();
            return Response.status(Response.Status.CREATED).entity(book).build();
        }
        catch (PersistenceException pe){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{isbn}")
    @Operation(summary = "Update the entire book taking {isbn} as a parameter")
    @Parameter(
            name = "book",
            description = "Book Object",
            required = true,
            example = "{\"title\":\"Don't Make Me Think\",\"genre\":\"Non-fiction\",\"isbn\":\"078-0-321-96551-6\",\"summary\":\"Since Don’t Make Me Think was first published in 2000....\"}",
            schema = @Schema(type = SchemaType.OBJECT))
    @Parameter(
            name = "isbn",
            description = "Unique ISBN code of a book",
            required = true,
            example = "978-0-321-76753-0",
            schema = @Schema(type = SchemaType.STRING))
    @APIResponse(
            responseCode = "204",
            description = "Book updated",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Unable to honor a bad request, possibly invalid Book object"
    )
    @APIResponse(
            responseCode = "404",
            description = "No Book matching {isbn} found in the backend"
    )
    @Transactional
    public Response update(@Valid Book book, @PathParam("isbn") String isbn) {
        Book entity = Book.findById(isbn);
        if (entity == null) {
            logger.debug("Book with id of " + isbn + " does not exist.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            entity.title = book.title;
            entity.genre = book.genre;
            entity.summary = book.summary;
            try {
                entity.persist();
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            catch (PersistenceException pe){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
    }

    @DELETE
    @Path("/{isbn}")
    @Operation(summary = "Delete a book based on its {isbn}")
    @APIResponse(
            responseCode = "204",
            description = "Deletes a book"
    )
    @Parameter(
            name = "isbn",
            description = "Unique ISBN code of a book",
            required = true,
            example = "978-0-321-76753-0",
            schema = @Schema(type = SchemaType.STRING))
    @Transactional
    public Response deleteOne(@PathParam("isbn") String isbn) {
        Book entity = Book.findById(isbn);
        if (entity == null) {
            throw new WebApplicationException("Book with isbn of " + isbn + " does not exist.", Response.Status.NOT_FOUND);
        }
        entity.delete();
        return Response.noContent().build();
    }
}
