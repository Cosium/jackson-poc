package com.cosium.poc.jackson.spring.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Since Page and PageImpl are hardly deserializable, we use this class as the result of PageImpl
 * deserialization.
 *
 * <p>Created on 17/11/16.
 *
 * @author Reda.Housni-Alaoui
 */
public class PageDeserializationBean<T> implements Page<T> {

  private Pageable pageable = Pageable.unpaged();
  private Page<T> page;

  public PageDeserializationBean(List<T> content, long total) {
    this.page = new PageImpl<>(content, Pageable.unpaged(), total);
  }

  public PageDeserializationBean(List<T> content) {
    this.page = new PageImpl<>(content);
  }

  @Override
  public Pageable getPageable() {
    return pageable;
  }

  public void setPageable(Pageable pageable) {
    this.pageable = pageable;
    this.page = new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
  }

  @Override
  public int getTotalPages() {
    return page.getTotalPages();
  }

  @Override
  public long getTotalElements() {
    return page.getTotalElements();
  }

  @Override
  public <U> Page<U> map(Function<? super T, ? extends U> converter) {
    return page.map(converter);
  }

  @Override
  public int getNumber() {
    return page.getNumber();
  }

  @Override
  public int getSize() {
    return page.getSize();
  }

  @Override
  public int getNumberOfElements() {
    return page.getNumberOfElements();
  }

  @Override
  public List<T> getContent() {
    return page.getContent();
  }

  @Override
  public boolean hasContent() {
    return page.hasContent();
  }

  @Override
  public Sort getSort() {
    return page.getSort();
  }

  @Override
  public boolean isFirst() {
    return page.isFirst();
  }

  @Override
  public boolean isLast() {
    return page.isLast();
  }

  @Override
  public boolean hasNext() {
    return page.hasNext();
  }

  @Override
  public boolean hasPrevious() {
    return page.hasPrevious();
  }

  @Override
  public Pageable nextPageable() {
    return page.nextPageable();
  }

  @Override
  public Pageable previousPageable() {
    return page.previousPageable();
  }

  @Override
  public Iterator<T> iterator() {
    return page.iterator();
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    page.forEach(action);
  }

  @Override
  public Spliterator<T> spliterator() {
    return page.spliterator();
  }

  @Override
  public String toString() {
    return page.toString();
  }
}
