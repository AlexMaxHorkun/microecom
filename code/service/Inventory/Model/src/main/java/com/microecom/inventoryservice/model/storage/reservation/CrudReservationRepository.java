package com.microecom.inventoryservice.model.storage.reservation;

import com.microecom.inventoryservice.model.storage.reservation.data.ReservationRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CrudReservationRepository extends CrudRepository<ReservationRow, UUID> {
    @Query(value = "SELECT sum(r.number) from ReservationRow r where r.productId = :product and r.fulfilled = false")
    Integer calculateSumForUnfulfilledByProductId(@Param("product") UUID id);

    List<ReservationRow> findByFulfilledAndOrderId(Boolean fulfilled, UUID orderId);
}
