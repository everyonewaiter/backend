package com.handwoong.everyonewaiter.util;

import java.util.Arrays;
import java.util.List;

enum HttpHeader {
    VARY("Vary"),
    X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options"),
    X_XSS_PROTECTION("X-XSS-Protection"),
    CACHE_CONTROL("Cache-Control"),
    PRAGMA("Pragma"),
    EXPIRES("Expires"),
    X_FRAME_OPTIONS("X-Frame-Options"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    KEEP_ALIVE("Keep-Alive"),
    CONNECTION("Connection"),
    HOST("Host"),
    DATE("Date"),
    CONTENT_LENGTH("Content-Length"),
    ;

    private final String header;

    HttpHeader(final String header) {
        this.header = header;
    }

    public static List<String> getUnusedHeaders() {
        return Arrays.stream(HttpHeader.values())
            .map(HttpHeader::getHeader)
            .toList();
    }

    public String getHeader() {
        return header;
    }
}
