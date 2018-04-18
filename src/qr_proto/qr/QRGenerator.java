package qr_proto.qr; /**
 * Created by Aeneas on 14.04.18.
 * Code template used from: https://crunchify.com/java-simple-qr-code-generator-example/
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import java.util.Random;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRGenerator {

  public static int checksum(String _message) {
    int checksum = 0;
    int length = _message.length();
    for (int i = 0; i < length; i++) {
      if (i % 2 == 0) {
        checksum += 3 * (int) _message.charAt(i);
      } else {
        checksum += (int) _message.charAt(i);
      }
      //System.out.println((int)_message.charAt(i));
    }
    checksum = checksum % 255;
    checksum = 255 - checksum;
    //System.out.println("Checksum = " + checksum);
    return checksum;
  }

  public static QRCode makeQR(String _message) {
    int size = 250; // TODO: automatic size calculation.

    long time = System.currentTimeMillis();

    String checkMessage = generateMessage(_message, 80);

    QRCode code = new QRCode(checkMessage, size);

    time = System.currentTimeMillis() - time;
    System.out.println("\n\nYou have successfully created QR Code in "+ time +"ms.");

    return code;
  }

  public static String checkMessage(String _message) {
    String message = _message;
    String cutMessage = message.substring(0, message.lastIndexOf(";"));
    int originalChecksum;
    int derivedChecksum;

    if (message != null) {
      originalChecksum = Integer.parseInt(message.substring(message.lastIndexOf(";") + 1));
      derivedChecksum = checksum(cutMessage);
      /*System.out.println(
          "Last index of equals: " + message.substring(message.lastIndexOf(";")) + " togehter with "
              + message.substring(0, message.lastIndexOf(";") - 1));*/
      if (originalChecksum != derivedChecksum) {
        System.out.println("Error: Checksum not identical");
        cutMessage = null;
      }
    }
    return cutMessage;

  }

  public static String generateMessage(String _message, int _id){
    int maxMessageSize = 2953; //QR-Code length = Binary/byte: Max. 2,953 characters (8-bit bytes) (23624 bits)
    int port, id;
    Random r = new Random();
    String header = null;
    String finalMessage = null;

    port = r.nextInt(9999);
    id = _id++;

    /* Checks weather message is long enough to encode in single code */
    if(_message.length() < maxMessageSize){
      finalMessage += header;
      finalMessage += _message;
      finalMessage +=  (";" + Integer.toString(checksum(_message)));
    }
    return finalMessage;
  }

}