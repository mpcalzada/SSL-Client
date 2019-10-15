package com.mcalzada;

import com.mcalzada.client.Clients;
import com.mcalzada.client.SSLAutoClient;
import com.mcalzada.client.SSLManualClient;

/**
 * Hello world!
 */
public class App
{

  public static void main(String[] args) throws Exception
  {
    System.out.println("Starting application");
    Clients clients;

    Assert.assertTrue(args.length >= 3, "Program arguments needed.");
    Assert.assertTrue(args[2].matches("\\d+"), "Third argument is invalid. Numbers only.");

    switch (args[0])
    {
      case "-m":
        clients = new SSLManualClient();
        break;
      case "-a":
        clients = new SSLAutoClient();
        break;
      default:
        throw new Exception("Unable to load a client");
    }

    clients.init(args);
    clients.process("Hello World! :)");
  }
}
