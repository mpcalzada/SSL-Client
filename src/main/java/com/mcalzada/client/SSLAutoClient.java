package com.mcalzada.client;

import java.io.BufferedReader;
import java.io.IOException;
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
  private String cipher;
  private final SSLAutoClient self = this;
  private int port;
  private boolean isListening;
  private boolean isSending;

  @Override
  public void init(String[] args)
  {
    host = args[1];
    port = Integer.parseInt(args[2]);
    cipher = args[3];
  }

  public void process(String message)
  {
    System.out.println("Trying TLS connection to: " + host + ":" + port);

    SocketFactory factory = SSLSocketFactory.getDefault();
    try (Socket connection = factory.createSocket(host, port))
    {
      System.out.println("Connection succeed");
      ((SSLSocket) connection).setEnabledCipherSuites(new String[]{cipher});

      SSLParameters sslParams = new SSLParameters();
      sslParams.setEndpointIdentificationAlgorithm("HTTPS");
      ((SSLSocket) connection).setSSLParameters(sslParams);

      System.out.println("Starting message interchange");

      PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
      BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      for (int i = 0; i <= 20; i++)
      {
        self.isSending = true;
        final String chunk = String.format(message,String.format("%05d",i));
        System.out.println("Sending message: " + chunk);
        out.println(chunk);
        Thread.sleep(5000);

        if (!self.isListening)
        {
          new Thread(() -> {
            try
            {
              self.isListening = true;
              while (self.isSending)
              {
                System.out.println("Reading the message.");
                System.out.println("Received: " + input.readLine());
                Thread.sleep(100);
              }
            }
            catch (Exception e)
            {
              System.out.println("Exception during the input reading");
              self.isListening = false;
            }
          }).start();
        }
      }

      System.out.println("Everything works fine :)");
      self.isSending = false;
    }
    catch (Exception e)
    {
      System.out.println(("Connection failed " + e.getMessage() + " on " + e.getLocalizedMessage()).replace("\n", ""));
      System.out.println("\nException details: \n");
      e.printStackTrace();
    }
  }
}
