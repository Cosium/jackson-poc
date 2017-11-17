package com.cosium.poc.jackson.spring.data.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Created on 14/11/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageableDeserializer extends JsonDeserializer<Pageable> {
    @Override
    public Pageable deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return p.getCodec().readValue(p, PageRequest.class);
    }

    @Override
    public Pageable getNullValue(DeserializationContext ctxt) throws JsonMappingException {
        return Pageable.unpaged();
    }

}
