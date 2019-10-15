package com.mcalzada.client;

import com.mcalzada.Assert;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSLManualClient
 */
public class SSLManualClient implements Clients
{

  private SSLSocket sslSocket;


  public void init(String[] args) throws Exception
  {
    Assert.assertTrue(args.length >= 5, "Missing program arguments for a manual SSL Client.");

    final String host = args[1];
    final int port = Integer.parseInt(args[2]);
    String keystorePath = args[2];
    String trustKeystorePath = args[3];
    String keystorePassword = args[4];
    String keystoreTrustPassword = args[4];

    SSLContext context = SSLContext.getInstance("SSL");

    KeyStore clientKeystore = KeyStore.getInstance("PKCS12");
    FileInputStream keystoreFis = new FileInputStream(keystorePath);
    clientKeystore.load(keystoreFis, keystorePassword.toCharArray());

    KeyStore trustKeystore = KeyStore.getInstance("jks");
    FileInputStream trustKeystoreFis = new FileInputStream(trustKeystorePath);
    trustKeystore.load(trustKeystoreFis, keystoreTrustPassword.toCharArray());

    KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
    kmf.init(clientKeystore, keystorePassword.toCharArray());

    TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
    tmf.init(trustKeystore);

    context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

    sslSocket = (SSLSocket) context.getSocketFactory().createSocket(host, port);
  }

  public void process(String message) throws Exception
  {
    OutputStream out = sslSocket.getOutputStream();
    out.write(message.getBytes(), 0, message.getBytes().length);
    out.flush();

    InputStream in = sslSocket.getInputStream();
    byte[] buffer = new byte[50];
    in.read(buffer);
    System.out.println(new String(buffer));
  }
}
