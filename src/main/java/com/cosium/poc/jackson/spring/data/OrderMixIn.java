package com.cosium.poc.jackson.spring.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Sort;

public class OrderMixIn implements MixIn {
    public OrderMixIn() {
    }

    @JsonCreator
    public OrderMixIn(
            @JsonProperty("direction") Sort.Direction direction,
            @JsonProperty("property") String property,
            @JsonProperty("nullHandling") Sort.NullHandling nullHandling) {
    }

    @Override
    public Class<?> getMixedClass() {
        return Sort.Order.class;
    }
}
