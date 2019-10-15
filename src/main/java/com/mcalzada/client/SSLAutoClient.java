package com.mcalzada.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * {@link SSLAutoClient}
 *
 * @author MarcoCalzada
 * @version 1
 * @since MarcoCalzada
 */
public class SSLAutoClient implements Clients
{

  private String host;
  private int port;

  @Override
  public void init(String[] args)
  {
    host = args[1];
    port = Integer.parseInt(args[2]);
  }

  public void process(String message)
  {
    System.out.println("Trying TLS connection to: " + host + ":" + port);

    SocketFactory factory = SSLSocketFactory.getDefault();
    try (Socket connection = factory.createSocket(host, port))
    {
      System.out.println("Connection succeed");
      ((SSLSocket) connection).setEnabledCipherSuites(new String[]{"TLS_DHE_DSS_WITH_AES_256_CBC_SHA256"});
      ((SSLSocket) connection).setEnabledProtocols(new String[]{"TLSv1.2"});

      SSLParameters sslParams = new SSLParameters();
      sslParams.setEndpointIdentificationAlgorithm("HTTPS");
      ((SSLSocket) connection).setSSLParameters(sslParams);

      System.out.println("Trying to read a message");

      BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      System.out.println(input.readLine());

      System.out.println("Trying to send the message: " + message);

      PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
      out.println(message);

      System.out.println("Everything works fine :)");
    }
    catch (Exception e)
    {
      System.out.println(("Connection failed " + e.getMessage() + " on " + e.getLocalizedMessage()).replace("\n", ""));
      System.out.println("\nException details: \n");
      e.printStackTrace();
    }
  }
}
