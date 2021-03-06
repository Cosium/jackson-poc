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
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 15/11/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageMixInTest {

    private final String json = "{\n" +
            "  \"content\" : [ \"test1\", \"test2\" ],\n" +
            "  \"pageNumber\" : 2,\n" +
            "  \"pageSize\" : 2,\n" +
            "  \"sort\" : {\n" +
            "    \"orders\" : [ {\n" +
            "      \"direction\" : \"ASC\",\n" +
            "      \"property\" : \"property\",\n" +
            "      \"nullHandling\" : \"NATIVE\",\n" +
            "      \"ignoreCase\" : false,\n" +
            "      \"ascending\" : true,\n" +
            "      \"descending\" : false\n" +
            "    } ],\n" +
            "    \"sorted\" : true,\n" +
            "    \"unsorted\" : false\n" +
            "  },\n" +
            "  \"offset\" : 4,\n" +
            "  \"paged\" : true,\n" +
            "  \"unpaged\" : false,\n" +
            "  \"totalElements\" : 10,\n" +
            "  \"totalPages\" : 5,\n" +
            "  \"last\" : false,\n" +
            "  \"numberOfElements\" : 2,\n" +
            "  \"sort\" : {\n" +
            "    \"orders\" : [ {\n" +
            "      \"direction\" : \"ASC\",\n" +
            "      \"property\" : \"property\",\n" +
            "      \"nullHandling\" : \"NATIVE\",\n" +
            "      \"ignoreCase\" : false,\n" +
            "      \"ascending\" : true,\n" +
            "      \"descending\" : false\n" +
            "    } ],\n" +
            "    \"sorted\" : true,\n" +
            "    \"unsorted\" : false\n" +
            "  },\n" +
            "  \"first\" : false,\n" +
            "  \"size\" : 2,\n" +
            "  \"number\" : 2\n" +
            "}";

    @Test
    public void test_deserialization_with_pageable_mixin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

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
        )
                .forEach(mixIn -> objectMapper.addMixIn(mixIn.getMixedClass(), mixIn.getClass()));

        expect(objectMapper.readValue(json, Page.class));
    }

    @Test
    public void test_deserialization_with_pageable_deserializer() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

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
//                new PageableMixIn(),
                new PageDeserializationMixIn<>(),
                new PageImplMixIn(),
                new PageMixIn(),
                new PageRequestMixIn(),
                new SortMixIn()
        )
                .forEach(mixIn -> objectMapper.addMixIn(mixIn.getMixedClass(), mixIn.getClass()));

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Pageable.class, new PageableDeserializer());
        objectMapper.registerModule(module);

        expect(objectMapper.readValue(json, Page.class));
    }

    @Test
    public void test_deserialization_with_pageable_deserializer_modifier() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

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
        )
                .forEach(mixIn -> objectMapper.addMixIn(mixIn.getMixedClass(), mixIn.getClass()));

        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new PageableDeserializerModifier());
        objectMapper.registerModule(module);

        expect(objectMapper.readValue(json, Page.class));
    }


    private void expect(Page<String> after) {
        assertThat(after.getTotalPages()).isEqualTo(5);
        assertThat(after.getNumber()).isEqualTo(2);
        assertThat(after.getSort()).isEqualTo(Sort.by(Sort.Order.by("property")));

        assertThat(after.getContent()).containsExactlyElementsOf(Lists.newArrayList("test1", "test2"));
        assertThat(after.getTotalElements()).isEqualTo(10);
        assertThat(after.getNumberOfElements()).isEqualTo(2);
        assertThat(after.getSize()).isEqualTo(2);
    }

}
