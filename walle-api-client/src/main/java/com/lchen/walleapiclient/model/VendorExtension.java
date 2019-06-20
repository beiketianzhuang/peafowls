package com.lchen.walleapiclient.model;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public interface VendorExtension<T> {
    String getName();

    T getValue();
}
