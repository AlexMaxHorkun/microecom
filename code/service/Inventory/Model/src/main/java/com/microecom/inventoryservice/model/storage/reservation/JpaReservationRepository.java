package com.microecom.inventoryservice.model.storage.reservation;

import com.microecom.inventoryservice.model.data.ActiveReservation;
import com.microecom.inventoryservice.model.data.NewReservation;
import com.microecom.inventoryservice.model.data.reservation.ReservedProduct;
import com.microecom.inventoryservice.model.exception.ReservationNotFoundException;
import com.microecom.inventoryservice.model.storage.ReservationRepository;
import com.microecom.inventoryservice.model.storage.data.ReservationUpdate;
import com.microecom.inventoryservice.model.storage.reservation.data.Active;
import com.microecom.inventoryservice.model.storage.reservation.data.ReservationRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaReservationRepository implements ReservationRepository {
    private final CrudReservationRepository repo;

    public JpaReservationRepository(@Autowired CrudReservationRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public Set<ActiveReservation> create(NewReservation reservation) {
        var reservations = new HashSet<ReservationRow>();
        for (ReservedProduct r : reservation.getReserved()) {
            reservations.add(repo.save(new ReservationRow(UUID.fromString(reservation.getOrderId()), UUID.fromString(r.getProductId()), r.getNumber())));
        }

        return reservations.stream().map(this::convert).collect(Collectors.toSet());
    }

    @Override
    public Integer calculateUnfulfilledForProductId(String id) {
        var calculated = repo.calculateSumForUnfulfilledByProductId(UUID.fromString(id));
        if (calculated == null) {
            calculated = 0;
        }

        return calculated;
    }

    @Transactional
    @Override
    public ActiveReservation update(ReservationUpdate update) throws ReservationNotFoundException {
        var found = repo.findById(UUID.fromString(update.getForReservationId()));
        if (found.isEmpty()) {
            throw new ReservationNotFoundException();
        }

        var row = found.get();
        row.setFulfilled(update.getFulfilled().get());

        return convert(repo.save(row));
    }

    @Transactional
    @Override
    public void delete(String id) throws ReservationNotFoundException {
        try {
            repo.deleteById(UUID.fromString(id));
        } catch (Exception exception) {
            throw new ReservationNotFoundException();
        }
    }

    @Override
    public Set<ActiveReservation> findUnfulfilledByOrderId(String id) {
        return repo.findByFulfilledAndOrderId(false, UUID.fromString(id)).stream().map(this::convert).collect(Collectors.toSet());
    }

    private ActiveReservation convert(ReservationRow row) {
        return new Active(row.getFulfilled(), row.getCreated(), row.getId().toString(), row.getOrderId().toString(), row.getProductId().toString(), row.getNumber());
    }
}
