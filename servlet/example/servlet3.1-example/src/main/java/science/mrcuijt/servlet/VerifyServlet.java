package science.mrcuijt.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("RequestURL:" + request.getRequestURL());
    System.out.println("QueryString:" + request.getQueryString());
    String message = request.getParameter("message");
    System.out.println("UTF-8 -> ISO-8859-1");
    System.out.println("message:" + message);
    try {
      System.out.println("ISO-8859-1 -> UTF-8");
      message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
      System.out.println("message:" + message);
      byte[] bytes = new byte[]{(byte)0x6D,(byte)0x65,(byte)0x73,(byte)0x73,(byte)0x61,(byte)0x67,(byte)0x65,(byte)0x3D,(byte)0xD5,(byte)0xC5,(byte)0xC8,(byte)0xFD};
      message = new String(bytes, "GBK");
      System.out.println("message:" + message);
      request.setAttribute("message", message);
    } catch(Exception e){
      e.printStackTrace();
    }
    FileOutputStream fos = new FileOutputStream(new File("querystring"));
    fos.write(request.getQueryString().getBytes("ISO-8859-1"));
    fos.close();
    request.getRequestDispatcher("/WEB-INF/html/login.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String userName = request.getParameter("userName");
    System.out.println(userName);
    String message = "消息：";
    response.sendRedirect("/verify.do?message=" + message);
  }
}