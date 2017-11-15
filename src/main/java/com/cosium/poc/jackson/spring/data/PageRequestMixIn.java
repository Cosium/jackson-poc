package com.cosium.poc.jackson.spring.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestMixIn implements MixIn {
  public PageRequestMixIn() {}

  @JsonCreator
  public PageRequestMixIn(
      @JsonProperty("pageNumber") int page,
      @JsonProperty("pageSize") int size,
      @JsonProperty("sort") Sort sort) {}

  @Override
  public Class<?> getMixedClass() {
    return PageRequest.class;
  }
}
