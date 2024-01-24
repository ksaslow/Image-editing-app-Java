package view;

/**
 * Interface that allows the View and Controller to communicate, and the ControllerGUI is set as a
 * listener of the view. This means that whatever event happens in the view, the controller is
 * notified and can act accordingly. The methods laid out below define the contract that a listener
 * of this program's GUI-specific view abide by, and which events must be "handled" by a listener.
 */
public interface ViewListener {

  void handleLoadEvent(String filename);
  void handleSaveEvent();
  void handleBlurEvent();
  void handleSharpenEvent();
  void handleValueEvent();
  void handleIntensityEvent();
  void handleLumaEvent();
  void handleRedChannelEvent();
  void handleGreenChannelEvent();
  void handleBlueChannelEvent();
  void handleBrightenEvent();
}
