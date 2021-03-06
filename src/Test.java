import com.github.sarxos.webcam.WebcamResolution;
import gui.TestWindow;
import qr_proto.QRProto;

import static qr_proto.util.Config.QR_CODE_SIZE;


public class Test {

  private QRProto qrProto;
  private TestWindow window;

  private void startTest() {
    qrProto = new QRProto(QR_CODE_SIZE, WebcamResolution.FHD.getSize());
    window = new TestWindow(qrProto);
  }

  public static void main(String[] args) {
    System.setProperty("sun.java2d.opengl","True");
    Test test = new Test();
    test.startTest();
  }
}
