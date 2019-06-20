package com.lchen.walleapiclient.model;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.lchen.walleapiclient.builders.BuilderDefault.nullToEmptyList;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public abstract class SecurityScheme {
    protected final String name;
    protected final String type;
    private final List<VendorExtension> vendorExtensions = newArrayList();

    protected SecurityScheme(String name, String type) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<VendorExtension> getVendorExtensions() {
        return ImmutableList.copyOf(vendorExtensions);
    }

    protected void addValidVendorExtensions(List<VendorExtension> vendorExtensions) {
        this.vendorExtensions.addAll(FluentIterable.from(nullToEmptyList(vendorExtensions))
                .filter(input -> input.getName().toLowerCase().startsWith("x-"))
                .toList());
    }
}