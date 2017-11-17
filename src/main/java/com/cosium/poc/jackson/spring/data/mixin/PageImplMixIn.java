package com.cosium.poc.jackson.spring.data.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Created on 17/11/16.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageImplMixIn implements MixIn {

  @JsonUnwrapped @JsonProperty private Pageable pageable;

  @Override
  public Class<?> getMixedClass() {
    return PageImpl.class;
  }
}
