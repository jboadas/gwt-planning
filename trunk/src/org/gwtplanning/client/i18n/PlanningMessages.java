package org.gwtplanning.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/stephane/java/mywork/GwtPlanning/src/org/gwtplanning/client/i18n/PlanningMessages.properties'.
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
   * Translated "Scale".
   * 
   * @return translated "Scale"
   */
  @DefaultMessage("Scale")
  @Key("parameters.scale")
  String parameters_scale();

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
