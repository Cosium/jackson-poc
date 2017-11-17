package com.cosium.poc.jackson.spring.data.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import org.springframework.data.domain.Pageable;

/**
 * Created on 17/11/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageableDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyDeserializer(
            DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        if (Pageable.class.isAssignableFrom(deserializer.handledType())) {
            return new PageableDeserializer(deserializer);
        }
        return deserializer;
    }

}
