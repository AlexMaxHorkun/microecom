package com.microecom.customerservice.http.controller.data;

import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Input violation response object.
 */
public class InputViolationRead {
    private final String fieldName;

    private final Set<String> violations;

    protected InputViolationRead(String fieldName, Set<String> violations) {
        this.fieldName = fieldName;
        this.violations = violations;
    }

    public static InputViolationRead[] fromViolationsMap(Map<String, Set<String>> map) {
        var list = new InputViolationRead[map.keySet().size()];
        var  i =0;
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            list[i] = new InputViolationRead(entry.getKey(), entry.getValue());
            i++;
        }

        return list;
    }

    public static InputViolationRead[] fromObjectErrors(Iterable<ObjectError> errors) {
        var map = new HashMap<String, Set<String>>();
        for (ObjectError error : errors) {
            if (!map.containsKey(error.getObjectName())) {
                map.put(error.getObjectName(), new HashSet<String>());
            }
            map.get(error.getObjectName()).add(error.getDefaultMessage());
        }

        return fromViolationsMap(map);
    }

    public String getFieldName() {
        return fieldName;
    }

    public Set<String> getViolations() {
        return violations;
    }
}
