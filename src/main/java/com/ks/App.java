package com.ks;

/**
 * Hello world!
 */
public class App
{

  public static void main(String[] args) throws Exception
  {
    Clients clients;

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
