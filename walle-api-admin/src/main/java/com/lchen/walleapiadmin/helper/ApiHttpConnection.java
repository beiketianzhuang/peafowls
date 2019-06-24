package com.lchen.walleapiadmin.helper;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.Closeable;
import java.io.IOException;

public interface ApiHttpConnection extends Closeable {

    @Override
    void close();

    <T> T call(HttpRequestBase requestBase) throws IOException;
}
