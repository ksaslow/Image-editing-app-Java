package controller;

import view.ViewListener;

/**
 * Represents the interface that specifies the contract that a GUI-specific controller should abide
 * by in order to best utilize this program. All methods laid out below define the events that could
 * happen in the view that must be handled by the GUI-specific controller.
 */
public interface IControllerGUI extends ViewListener {

  void run();
  void handleLoadEvent(String filename);
  void handleBlurEvent();
  void handleSaveEvent();
  void handleSharpenEvent();
  void handleValueEvent();
  void handleIntensityEvent();
  void handleLumaEvent();
  void handleRedChannelEvent();
  void handleGreenChannelEvent();
  void handleBlueChannelEvent();
  void handleBrightenEvent();
}
