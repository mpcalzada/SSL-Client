package com.mcalzada;

import com.ks.lib.tcp.protocolos.Iso;
import com.mcalzada.client.Clients;
import com.mcalzada.client.SSLAutoClient;
import com.mcalzada.client.SSLManualClient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Hello world!
 */
public class App
{

  public static void main(String[] args) throws Exception
  {

    System.out.println("Starting application");
    Clients clients;
    final SimpleDateFormat fecha = new SimpleDateFormat("MMdd");
    final SimpleDateFormat horaGMT = new SimpleDateFormat("HHmmss");
    horaGMT.setTimeZone(TimeZone.getTimeZone("GMT+0"));

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

    final String logon = "ISO023400070080082220000000100000400000000000000" + fecha.format(new Date()) + horaGMT.format(new Date()) + "%s" + fecha.format(new Date()) + "001";
    clients.init(args);
    clients.process(Iso.obtenerLongitud(logon.length()) + logon);
  }
}
