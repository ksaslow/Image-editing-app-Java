package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents the class implementation of the IImageDataBase model. This class allows the user
 * to add and get IImageState object to and from the database in the model.
 */
public class ImageDataBase implements IImageDataBase {
  private final Map<String, IImageState> images;

  public ImageDataBase() {

    this.images = new HashMap<String, IImageState>();
  }


  @Override
  public void add(String id, IImageState image) {
    if (id == null || image == null) {
      throw new IllegalArgumentException("ID or image is null");
    }
    this.images.put(id, image);
  }

  @Override
  public IImageState get(String id) {
    Objects.requireNonNull(id);
    return this.images.get(id); //should this be a copy??? maybe!
  }

  //getter method to use for testing that IImages are correctly added to DB after transformations
  @Override
  public Map<String, IImageState> getImages() {
    return images;
  }

}
