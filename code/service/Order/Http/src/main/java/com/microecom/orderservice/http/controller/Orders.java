package com.microecom.orderservice.http.controller;

import com.microecom.orderservice.http.data.*;
import com.microecom.orderservice.http.model.PrincipalManager;
import com.microecom.orderservice.model.OrderManager;
import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.OrdersCriteria;
import com.microecom.orderservice.model.data.payment.CardPaymentDetails;
import com.microecom.orderservice.model.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(path = "/rest/V1/order")
public class Orders {
    private final PrincipalManager principals;

    private final OrderManager manager;

    public Orders(@Autowired PrincipalManager principalManager, @Autowired OrderManager manager) {
        this.principals = principalManager;
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity<OrderRead> place(@RequestBody @Valid @NotNull NewOrderInput order, Principal principal) {
        try {
            return new ResponseEntity<>(OrderRead.of(manager.place(NewOrder.of(order, principals.extractUserId(principal)))), HttpStatus.CREATED);
        } catch (OutOfStockException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Out of stock", exception);
        } catch (InvalidPaymentDetailsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment provided", exception);
        }
    }

    @PutMapping("/{orderId}/payment-details/card")
    public ResponseEntity<Object> updatePayment(@RequestBody @Valid @NotNull CardPaymentDetailsInput card, @PathVariable String orderId) {
        try {
            manager.update(new OrderPaymentUpdate(orderId, new CardPaymentDetails(card.getCard(), card.getExpiresMonth(), card.getExpiresYear(), card.getCvv())));
        } catch (InvalidPaymentDetailsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment provided", exception);
        } catch (OrderNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found", exception);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRead> findById(@PathVariable @NotNull String id) {
        var found = manager.findById(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        return new ResponseEntity<>(OrderRead.of(found.get()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<OrderRead>> findAll(Principal principal) {
        return new ResponseEntity<>(OrderRead.of(manager.findList(new OrdersCriteria(principals.extractUserId(principal)))), HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderRead> cancel(@PathVariable String id) {
        ExistingOrder order;
        try {
            order = manager.update(new OrderCancellation(id));
        } catch (OrderNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found", e);
        } catch (InvalidOrderStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot cancel this order");
        }

        return new ResponseEntity<>(OrderRead.of(order), HttpStatus.OK);
    }
}
