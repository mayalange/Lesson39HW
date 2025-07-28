package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.BookDTO;
import org.example.service.BookService;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Path("/reader/{readerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByReaderId(@PathParam("readerId") int readerId) {
        List<BookDTO> books = bookService.getBooksByReaderId(readerId);
        return Response.ok(books).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookDTO bookDTO) {
        bookService.addBook(bookDTO);
        return Response.status(Response.Status.CREATED).build();
    }
}