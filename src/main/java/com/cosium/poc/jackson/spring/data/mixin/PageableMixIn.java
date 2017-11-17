package com.cosium.poc.jackson.spring.data.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@JsonDeserialize(as = PageRequest.class)
public class PageableMixIn implements MixIn {
    @Override
    public Class<?> getMixedClass() {
        return Pageable.class;
    }
}
