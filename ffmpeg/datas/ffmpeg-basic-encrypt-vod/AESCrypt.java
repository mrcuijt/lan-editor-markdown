import javax.crypto.*;
import javax.crypto.spec.*;

import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.*;

public class AESCrypt {

  private static final String algorithm = "AES/CBC/PKCS5Padding"; // 算法

  public static void main(String[] args) {

    String input = ".黑猫";
    byte[] datas = input.getBytes();
    String password = "1234567812345678";

    byte[] encrypt = encrypt(datas, password);
    String encryptbase64 = Base64.encodeBase64String(encrypt);
    byte[] decrypt = decrypt(encrypt, password);
    System.out.println("Source:" + input);
    System.out.println("AES Encrypt:" + encryptbase64);
    System.out.println("AES Decrypt:" + new String(decrypt));
  }

  public static byte[] encrypt(byte[] datas, String password){
    try {
      Cipher cipher = Cipher.getInstance(algorithm);
      // iv 16byte (128bit)
      // iv hexstring 30303030303030303030303030303030
      String siv = "0000000000000000";
      Key key = new SecretKeySpec(password.getBytes(), "AES");
      IvParameterSpec iv = new IvParameterSpec(siv.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, key, iv);
      byte[] encrypt = cipher.doFinal(datas);
      return encrypt;
    } catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] decrypt(byte[] encrypt, String password){
    try {
      Cipher cipher = Cipher.getInstance(algorithm);
      Key key = new SecretKeySpec(password.getBytes(), "AES");
      // iv 16byte (128bit)
      // iv hexstring 30303030303030303030303030303030
      String siv = "0000000000000000";
      IvParameterSpec iv = new IvParameterSpec(siv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, key, iv);
      byte[] decrypt = cipher.doFinal(encrypt);
      return decrypt;
    } catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }
}