package com.cosium.poc.jackson.spring.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.Page;

@JsonDeserialize(as = PageDeserializationBean.class)
public class PageMixIn implements MixIn {
  @Override
  public Class<?> getMixedClass() {
    return Page.class;
  }
}
