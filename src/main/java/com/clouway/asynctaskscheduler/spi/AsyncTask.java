package com.clouway.asynctaskscheduler.spi;

/**
 * @author Mihail Lesikov (mlesikov@gmail.com)
 */
public interface AsyncTask {

  public void execute(AsyncTaskParams params);

}
