package com.microecom.customerservice.model.storage.address;

import com.microecom.customerservice.model.exception.InvalidInputDataException;
import com.microecom.customerservice.model.storage.address.data.AddressRow;
import com.microecom.customerservice.model.storage.data.AddressListCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AddressRow> findByCustomerId(UUID customerId, AddressListCriteria criteria) throws InvalidInputDataException {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(AddressRow.class);
        var root = query.from(AddressRow.class);
        var preds = new ArrayList<Predicate>();
        preds.add(builder.equal(root.get("customer").get("id"), customerId));
        criteria.getAddressLineEq().ifPresent(a -> preds.add(builder.equal(root.get("addressLine"), a)));
        criteria.getZipCodeEq().ifPresent(z -> preds.add(builder.equal(root.get("zipCode"), z)));
        if (criteria.getAddressLine2Eq().isPresent()
                && criteria.getAddressLine2IsNull().isPresent()
                && criteria.getAddressLine2IsNull().get()
        ) {
            throw new InvalidInputDataException("Address line 2 cannot be empty and equal to a value at the same time");
        }
        criteria.getAddressLine2Eq().ifPresent(a -> preds.add(builder.equal(root.get("addressLine2"), a)));
        if (criteria.getAddressLine2IsNull().isPresent()) {
            if (criteria.getAddressLine2IsNull().get()) {
                preds.add(builder.isNull(root.get("addressLine2")));
            } else {
                preds.add(builder.isNotNull(root.get("addressLine2")));
            }
        }

        return entityManager.createQuery(
                query.select(root).where(builder.and(preds.toArray(new Predicate[preds.size()])))
        ).getResultList();
    }
}
