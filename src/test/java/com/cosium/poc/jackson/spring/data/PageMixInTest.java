package com.cosium.poc.jackson.spring.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 15/11/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageMixInTest {

    private static final Logger LOG = LoggerFactory.getLogger(PageMixInTest.class);

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

        List<MixIn> mixIns = Lists.newArrayList(
                new DirectionMixIn(),
                new OrderMixIn(),
                new PageableMixIn(),
                new PageDeserializationMixIn<>(),
                new PageImplMixIn(),
                new PageMixIn(),
                new PageRequestMixIn(),
                new SortMixIn()
        );
        mixIns.forEach(mixIn -> objectMapper.addMixIn(mixIn.getMixedClass(), mixIn.getClass()));
    }

    @Test
    public void test() throws Exception {
        Pageable pageable = PageRequest.of(2, 2, Sort.by(Sort.Order.by("property")));
        Page<String> before = new PageImpl<>(Arrays.asList("test1", "test2"), pageable, 10);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(before);
        LOG.info("Serialized object : {}", json);
        Page<String> after = objectMapper.readValue(json, Page.class);

        assertThat(before.getContent()).containsExactlyElementsOf(after.getContent());
        assertThat(before.getTotalElements()).isEqualTo(after.getTotalElements());
        assertThat(before.getTotalPages()).isEqualTo(after.getTotalPages());
        assertThat(before.getNumber()).isEqualTo(after.getNumber());
        assertThat(before.getNumberOfElements()).isEqualTo(after.getNumberOfElements());
        assertThat(before.getSize()).isEqualTo(after.getSize());
        assertThat(before.getSort()).isEqualTo(after.getSort());
    }

}
