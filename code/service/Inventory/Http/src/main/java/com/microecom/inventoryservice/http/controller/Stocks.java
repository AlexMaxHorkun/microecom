package com.microecom.inventoryservice.http.controller;

import com.microecom.inventoryservice.http.controller.data.NewStock;
import com.microecom.inventoryservice.http.controller.data.PatchStockUpdate;
import com.microecom.inventoryservice.http.controller.data.StockRead;
import com.microecom.inventoryservice.model.StockCalculator;
import com.microecom.inventoryservice.model.StockManager;
import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(path = "/rest/V1/stock")
public class Stocks {
    private final StockManager stocks;

    private final StockCalculator calculator;

    public Stocks(@Autowired StockManager stocks, @Autowired StockCalculator calculator) {
        this.stocks = stocks;
        this.calculator = calculator;
    }

    @PostMapping
    public ResponseEntity<StockRead> create(@RequestBody @Valid NewStock stock) {
        return new ResponseEntity<>(StockRead.of(stocks.create(stock)), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{productId}")
    public ResponseEntity<StockRead> update(@RequestBody @Valid PatchStockUpdate update, @PathVariable String productId) {
        update.setForProductId(productId);

        return new ResponseEntity<>(StockRead.of(stocks.update(update)), HttpStatus.OK);
    }

    @GetMapping(path = "/calculated")
    public ResponseEntity<Iterable<StockRead>> getCalculated(@RequestParam @Valid @Size(min = 1) Set<String> productIds) {
        var calculated = calculator.calculateAvailableFor(productIds);
        var set = new HashSet<StockRead>();
        for (CalculatedAvailable c : calculated.values()) {
            set.add(StockRead.of(c));
        }

        return new ResponseEntity<>(set, HttpStatus.OK);
    }
}
