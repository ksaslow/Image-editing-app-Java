import java.io.IOException;

/**
 * Represents a fake appendable to test that IOExceptions are being thrown correctly
 * by the controller.
 */
public class FakeAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
