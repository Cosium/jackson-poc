package com.cosium.poc.jackson.spring.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Sort;

import java.util.List;

public class SortMixIn implements MixIn {
  @JsonProperty("orders")
  private final List<Sort.Order> orders = null;

  public SortMixIn() {}

  @JsonCreator
  public SortMixIn(@JsonProperty("orders") List<Sort.Order> orders) {}

  @Override
  public Class<?> getMixedClass() {
    return Sort.class;
  }
}
