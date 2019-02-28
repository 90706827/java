package com.jangni.http.client;

import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName BasicHttpContext
 * Description
 * Author Mr.Jangni
 * Date 2019/2/28 22:17
 * Version 1.0
 **/
public class BasicHttpContext implements HttpContext {

    private final HttpContext parentContext;
    private final Map<String, Object> map;

    public BasicHttpContext() {
        this(null);
    }

    public BasicHttpContext(final HttpContext parentContext) {
        super();
        this.map = new ConcurrentHashMap<String, Object>();
        this.parentContext = parentContext;
    }

    public Object getAttribute(final String id) {
        Args.notNull(id, "Id");
        Object obj = this.map.get(id);
        if (obj == null && this.parentContext != null) {
            obj = this.parentContext.getAttribute(id);
        }
        return obj;
    }

    public void setAttribute(final String id, final Object obj) {
        Args.notNull(id, "Id");
        if (obj != null) {
            this.map.put(id, obj);
        } else {
            this.map.remove(id);
        }
    }

    public Object removeAttribute(final String id) {
        Args.notNull(id, "Id");
        return this.map.remove(id);
    }

    /**
     * @since 4.2
     */
    public void clear() {
        this.map.clear();
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}