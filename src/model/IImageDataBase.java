package model;

import java.util.Map;

/**
 * Represents the IImageDataBase model. This object stores the IImageStates by a unique
 * key in a HashMap, which allows the user to fetch and transform any image stored in the database.
 */
public interface IImageDataBase {

  //note that this also exposes what we are using for Images. Not ideal!
  void add(String id, IImageState image);

  IImageState get(String id);

  Map<String, IImageState> getImages();
}
