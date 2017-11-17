package com.cosium.poc.jackson.spring.data;

import com.cosium.poc.jackson.spring.data.deser.PageableDeserializer;
import com.cosium.poc.jackson.spring.data.mixin.*;
import com.cosium.poc.jackson.spring.data.modifier.PageableDeserializerModifier;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Pageable;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 15/11/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageableMixinTest {

    private ObjectMapper objectMapper;

    @Before
    public void before() {
        objectMapper = new ObjectMapper();

        objectMapper.enable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        objectMapper.disable(MapperFeature.IGNORE_DUPLICATE_MODULE_REGISTRATIONS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
        objectMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);

        Stream.of(
                new DirectionMixIn(),
                new OrderMixIn(),
                new PageableMixIn(),
                new PageDeserializationMixIn<>(),
                new PageImplMixIn(),
                new PageMixIn(),
                new PageRequestMixIn(),
                new SortMixIn()
        ).forEach(mixIn -> objectMapper.addMixIn(mixIn.getMixedClass(), mixIn.getClass()));

        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new PageableDeserializerModifier());
        objectMapper.registerModule(module);
    }

    @Test
    public void testNullDeserialization() throws Exception {
        Wrapper wrapper = objectMapper.readValue("{\"pageable\": null}", Wrapper.class);
        assertThat(wrapper.pageable).isNotNull();
        assertThat(wrapper.pageable.isUnpaged()).isTrue();
    }

    private static class Wrapper {
        private Pageable pageable;

        public Pageable getPageable() {
            return pageable;
        }

        public void setPageable(Pageable pageable) {
            this.pageable = pageable;
        }

    }

}
