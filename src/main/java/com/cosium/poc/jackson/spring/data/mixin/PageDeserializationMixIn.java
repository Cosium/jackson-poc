package com.cosium.poc.jackson.spring.data.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageDeserializationMixIn<T> implements MixIn {

  public PageDeserializationMixIn() {}

  @JsonCreator
  public PageDeserializationMixIn(
      @JsonProperty("content") List<T> content, @JsonProperty("totalElements") long total) {}

  @Override
  public Class<?> getMixedClass() {
    return PageDeserializationBean.class;
  }

  @JsonUnwrapped
  @JsonProperty
  public void setPageable(Pageable pageable) {}
}
