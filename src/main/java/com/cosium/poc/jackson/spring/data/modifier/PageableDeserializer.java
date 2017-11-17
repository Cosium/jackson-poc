package com.cosium.poc.jackson.spring.data.modifier;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import org.springframework.data.domain.Pageable;

/**
 * Created on 14/11/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageableDeserializer extends DelegatingDeserializer {

    public PageableDeserializer(JsonDeserializer<?> d){
        super(d);
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
        return new PageableDeserializer(newDelegatee);
    }

//    @Override
//    public Pageable deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        return p.getCodec().readValue(p, PageRequest.class);
//    }


    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
        return (JsonDeserializer<Object>) _delegatee.unwrappingDeserializer(unwrapper);
    }

    @Override
    public Pageable getNullValue(DeserializationContext ctxt) throws JsonMappingException {
        return Pageable.unpaged();
    }

}
