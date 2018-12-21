package com.lehow.rightselect;

/**
 * desc:
 * author: luoh17
 * time: 2018/12/15 17:00
 */
public interface OptionConverter<T> {

  String getTitle(T entity);
  String getValue(T entity);
}
