# Getting started #

  1. Download the gwtplanning-XX.jar
  1. Add it in the classpath of your project
  1. Modify YourApplication.gwt.xml to include these lines:
```
<inherits name="org.gwtplanning.GwtPlanning"/>
```
  1. Here is an example of module that use the planning:
```
    public void onModuleLoad() {
        final PlanningParameters paramsPanel = new PlanningParameters();

        final PlanningPanel planningPanel = new PlanningPanel(new RandomPlanningDataProvider(5), paramsPanel.getStartDate(),
                paramsPanel.getEndDate(), paramsPanel.getScaleMode());

        planningPanel.draw();

        paramsPanel.addChangeListener(new ParamChangeListener() {

            public void onChange() {
                planningPanel.setStartDate(paramsPanel.getStartDate());
                planningPanel.setEndDate(paramsPanel.getEndDate());
                planningPanel.setScaleMode(paramsPanel.getScaleMode());
                planningPanel.draw();
            }
        });

        Panel toReturn = new VerticalPanel();

        RootPanel.get().add(toReturn);
    }
```

Now you can replace RandomPlanningDataProvider(5) by RemotePlanningDataProvider to get data from a RPC call in classic GWT way.