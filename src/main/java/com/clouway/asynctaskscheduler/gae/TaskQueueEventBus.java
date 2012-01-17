package com.clouway.asynctaskscheduler.gae;

import com.clouway.asynctaskscheduler.spi.AsyncEvent;
import com.clouway.asynctaskscheduler.spi.AsyncEventBus;
import com.clouway.asynctaskscheduler.spi.AsyncTaskOptions;
import com.clouway.asynctaskscheduler.spi.AsyncTaskScheduler;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * @author Mihail Lesikov (mlesikov@gmail.com)
 */
class TaskQueueEventBus implements AsyncEventBus {
  private final Logger log = Logger.getLogger(TaskQueueEventBus.class.getName());

  private final Provider<AsyncTaskScheduler> taskScheduler;
  private final Provider<HttpServletRequest> requestProvider;
  private Provider<String> emailProvider;

  @Inject
  public TaskQueueEventBus(Provider<AsyncTaskScheduler> taskScheduler,
                           Provider<HttpServletRequest> requestProvider,
                           @Named("emailFromRequest")Provider<String> emailProvider) {
    this.taskScheduler = taskScheduler;
    this.requestProvider = requestProvider;
    this.emailProvider = emailProvider;
  }

  @Override
  public void fireEvent(AsyncEvent<?> event) {
    String email = emailProvider.get();

    if(Strings.isNullOrEmpty(email)) {
      email = requestProvider.get().getParameter("user");
    }

    log.info("fired async event : " + event.getClass().getSimpleName());
    taskScheduler.get().add(AsyncTaskOptions.event(event).addUserEmail(email)).now();
  }
}