package org.gwtplanning.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/stephane/java/mywork/gwt-planning/src/org/gwtplanning/client/i18n/PlanningMessages.properties'.
 */
public interface PlanningMessages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Day".
   * 
   * @return translated "Day"
   */
  @DefaultMessage("Day")
  @Key("DAY")
  String DAY();

  /**
   * Translated "5 min".
   * 
   * @return translated "5 min"
   */
  @DefaultMessage("5 min")
  @Key("FIVE")
  String FIVE();

  /**
   * Translated "4 hours".
   * 
   * @return translated "4 hours"
   */
  @DefaultMessage("4 hours")
  @Key("FOUR_HOUR")
  String FOUR_HOUR();

  /**
   * Translated "1/2 day".
   * 
   * @return translated "1/2 day"
   */
  @DefaultMessage("1/2 day")
  @Key("HALF_DAY")
  String HALF_DAY();

  /**
   * Translated "Hour".
   * 
   * @return translated "Hour"
   */
  @DefaultMessage("Hour")
  @Key("HOUR")
  String HOUR();

  /**
   * Translated "1 min".
   * 
   * @return translated "1 min"
   */
  @DefaultMessage("1 min")
  @Key("ONE")
  String ONE();

  /**
   * Translated "20 min".
   * 
   * @return translated "20 min"
   */
  @DefaultMessage("20 min")
  @Key("TWENTY")
  String TWENTY();

  /**
   * Translated "Error".
   * 
   * @return translated "Error"
   */
  @DefaultMessage("Error")
  @Key("error")
  String error();

  /**
   * Translated "End date cannot be before start date".
   * 
   * @return translated "End date cannot be before start date"
   */
  @DefaultMessage("End date cannot be before start date")
  @Key("error.endBeforeStart")
  String error_endBeforeStart();

  /**
   * Translated "Elapsed time: {0} min".
   * 
   * @return translated "Elapsed time: {0} min"
   */
  @DefaultMessage("Elapsed time: {0} min")
  @Key("eventDetails.elapsedTime")
  String eventDetails_elapsedTime(String arg0);

  /**
   * Translated "End date: {0}".
   * 
   * @return translated "End date: {0}"
   */
  @DefaultMessage("End date: {0}")
  @Key("eventDetails.endDate")
  String eventDetails_endDate(String arg0);

  /**
   * Translated "Start date: {0}".
   * 
   * @return translated "Start date: {0}"
   */
  @DefaultMessage("Start date: {0}")
  @Key("eventDetails.startDate")
  String eventDetails_startDate(String arg0);

  /**
   * Translated "End date".
   * 
   * @return translated "End date"
   */
  @DefaultMessage("End date")
  @Key("parameters.endDate")
  String parameters_endDate();

  /**
   * Translated "Start date".
   * 
   * @return translated "Start date"
   */
  @DefaultMessage("Start date")
  @Key("parameters.startDate")
  String parameters_startDate();

  /**
   * Translated "Apply".
   * 
   * @return translated "Apply"
   */
  @DefaultMessage("Apply")
  @Key("parameters.validButton")
  String parameters_validButton();
}
