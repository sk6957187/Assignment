package com.project.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

public class RequestFilter implements ContainerRequestFilter {
    final static private Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    @Context
    private HttpServletRequestWrapper httpServletRequest;
    public RequestFilter() {
    }
    public void filter(final ContainerRequestContext requestContext) {
    }
}
