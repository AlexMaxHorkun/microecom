package com.microecom.customerservice.http.controller.data;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

/**
 * Input violation response object.
 */
public class InputViolationRead {
    private final String fieldName;

    private final Set<String> violations;

    private final Set<InputViolationRead> subViolations;

    public static InputViolationRead[] fromViolationsMap(Map<String, Set<String>> map) {
        return fromViolationsMaps(map, null);
    }

    public static InputViolationRead[] fromObjectErrors(Iterable<ObjectError> errors) {
        var map = new HashMap<String, Set<String>>();
        var fieldMap = new HashMap<String, Map<String, Set<String>>>();
        for (ObjectError error : errors) {
            if (!map.containsKey(error.getObjectName())) {
                map.put(error.getObjectName(), new HashSet<>());
            }
            if (!(error instanceof FieldError)) {
                map.get(error.getObjectName()).add(error.getDefaultMessage());
            } else {
                if (!fieldMap.containsKey(error.getObjectName())) {
                    fieldMap.put(error.getObjectName(), new HashMap<>());
                }
                if (!fieldMap.get(error.getObjectName()).containsKey(((FieldError) error).getField())) {
                    fieldMap.get(error.getObjectName()).put(((FieldError) error).getField(), new HashSet<>());
                }
                fieldMap.get(error.getObjectName()).get(((FieldError) error).getField()).add(error.getDefaultMessage());
            }
        }

        return fromViolationsMaps(map, fieldMap);
    }

    private static InputViolationRead[] fromViolationsMaps(
            Map<String, Set<String>> map,
            Map<String, Map<String, Set<String>>> sub
    ) {
        var list = new InputViolationRead[map.keySet().size()];
        var  i =0;
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            Set<InputViolationRead> subs = null;
            if (sub != null && sub.containsKey(entry.getKey())) {
                subs = Set.of(fromViolationsMaps(sub.get(entry.getKey()), null));
            }
            list[i] = new InputViolationRead(entry.getKey(), entry.getValue(), subs);
            i++;
        }

        return list;
    }

    protected InputViolationRead(String fieldName, Set<String> violations, Set<InputViolationRead> subViolations) {
        this.fieldName = fieldName;
        this.violations = violations;
        this.subViolations = subViolations;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Set<String> getViolations() {
        return violations;
    }

    public Optional<Set<InputViolationRead>> getSubViolations() {
        return Optional.ofNullable(subViolations);
    }
}
