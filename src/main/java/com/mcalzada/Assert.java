package com.mcalzada;

/**
 * {@link Assert}
 *
 * @author MarcoCalzada
 * @version 1
 * @since MarcoCalzada
 */
public class Assert
{
  public static void assertTrue(boolean expr, String message) throws Exception
  {
    if (!expr)
    {
      throw new Exception(message);
    }
  }
}
