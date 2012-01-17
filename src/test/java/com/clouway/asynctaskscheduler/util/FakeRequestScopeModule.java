package com.clouway.asynctaskscheduler.util;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;

import javax.servlet.http.HttpServletRequest;

/**
* @author Miroslav Genov (mgenov@gmail.com)
*/
public class FakeRequestScopeModule extends AbstractModule {
  private final SimpleScope fakeRequestScope;

  public FakeRequestScopeModule(SimpleScope fakeRequestScope) {

    this.fakeRequestScope = fakeRequestScope;
  }

  @Override
  protected void configure() {
    bindScope(RequestScoped.class, fakeRequestScope);
    bind(HttpServletRequest.class)
            .to(FakeHttpServletRequest.class)
            .in(RequestScoped.class);
  }

  @Named("emailFromRequest")
  @Provides
  public String getUserEmailRequest() {
    return null;
  }
}
