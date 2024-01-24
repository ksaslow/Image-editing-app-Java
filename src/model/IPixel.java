package model;

/**
 * This Class contains the MUTATING methods. We will generally use the
 * IPixelState throughout the code so that the model can't be mutated or modified
 * in way not intended by us.
 */
public interface IPixel extends IPixelState {
  void setR(int val);

  void setG(int val);

  void setB(int val);
}
