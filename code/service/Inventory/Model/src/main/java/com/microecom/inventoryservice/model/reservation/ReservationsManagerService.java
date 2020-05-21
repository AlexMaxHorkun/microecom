package com.microecom.inventoryservice.model.reservation;

import com.microecom.inventoryservice.model.ReservationsManager;
import com.microecom.inventoryservice.model.StockManager;
import com.microecom.inventoryservice.model.data.ActiveReservation;
import com.microecom.inventoryservice.model.data.NewReservation;
import com.microecom.inventoryservice.model.data.reservation.ReservedProduct;
import com.microecom.inventoryservice.model.exception.InvalidReservationDataException;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.inventoryservice.model.exception.ReservationNotFoundException;
import com.microecom.inventoryservice.model.reservation.data.StockSubUpdate;
import com.microecom.inventoryservice.model.storage.ReservationRepository;
import com.microecom.inventoryservice.model.storage.StockRepository;
import com.microecom.inventoryservice.model.storage.reservation.data.ReservationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;

@Service
public class ReservationsManagerService implements ReservationsManager {
    private final ReservationRepository repo;

    private final StockManager stocks;

    public ReservationsManagerService(@Autowired ReservationRepository repo, @Autowired StockManager stocks) {
        this.repo = repo;
        this.stocks = stocks;
    }

    @Transactional
    @Override
    public Set<ActiveReservation> place(NewReservation reservation) throws NoStockFoundException, InvalidReservationDataException {
        if (reservation.getReserved().size() == 0) {
            throw new InvalidReservationDataException();
        }
        var productStocks = stocks.findByProductIds(reservation.getReserved().stream().map(ReservedProduct::getProductId).collect(Collectors.toSet()));
        if (productStocks.size() != reservation.getReserved().size()) {
            throw new NoStockFoundException();
        }

        try {
            return repo.create(reservation);
        } catch (Exception exception) {
            throw new InvalidReservationDataException(exception);
        }
    }

    @Override
    public Optional<Integer> calculateReservedFor(String productId) {
        var calculated = repo.calculateUnfulfilledForProductId(productId);
        if (calculated == 0) {
            return Optional.empty();
        }

        return Optional.of(calculated);
    }

    @Transactional
    @Override
    public Set<String> fulfillByOrderId(String orderId) throws ReservationNotFoundException {
        var reservations = repo.findUnfulfilledByOrderId(orderId);
        if (reservations.size() == 0) {
            throw new ReservationNotFoundException();
        }
        var affected = new HashSet<String>();
        for (ActiveReservation r : reservations) {
            stocks.update(new StockSubUpdate(r.getProductId(), r.getNumber()));
            repo.update(new ReservationUpdate(r.getId(), true));
            affected.add(r.getProductId());
        }

        return affected;
    }

    @Transactional
    @Override
    public Set<String> cancelByOrderId(String orderId) throws ReservationNotFoundException {
        var reservations = repo.findUnfulfilledByOrderId(orderId);
        if (reservations.size() == 0) {
            throw new ReservationNotFoundException();
        }

        var affected = new HashSet<String>();
        for (ActiveReservation r : reservations) {
            repo.delete(r.getId());
            affected.add(r.getProductId());
        }

        return affected;
    }
}
