package com.cosium.poc.jackson.spring.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.domain.Sort;

public class DirectionMixIn implements MixIn {
  @JsonCreator
  public static Sort.Direction fromString(String value) {
    return null;
  }

  @Override
  public Class<?> getMixedClass() {
    return Sort.Direction.class;
  }
}
