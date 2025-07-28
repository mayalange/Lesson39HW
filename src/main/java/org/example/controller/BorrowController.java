package org.example.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.BorrowDTO;
import org.example.service.BorrowService;

@Path("/borrows")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BorrowController {
    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @POST
    public Response borrowBook(BorrowDTO borrowDTO) {
        borrowService.borrowBook(borrowDTO);
        return Response.status(Response.Status.CREATED).build();
    }
}