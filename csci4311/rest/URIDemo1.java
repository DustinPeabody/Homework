import java.net.*;
class URIDemo1
{
  public static void main (String [] args) throws Exception
  {
   if (args.length != 1)
   {
     System.err.println ("usage: java URIDemo1 uri");
     return;
   }

   URI uri = new URI (args [0]);

   URI uri2 = new URI ("http://localhost:80/user/");

   URI uri1 = uri2.relativize(uri);

   System.out.println(uri1.toString());

   System.out.println ("Authority = " +
             uri.getAuthority ());

   System.out.println ("Fragment = " +
             uri.getFragment ());

   System.out.println ("Host = " +
             uri.getHost ());

   System.out.println ("Path = " +
             uri.getPath ());

   System.out.println ("Path2 =" + uri1.getPath());

   System.out.println("getRequestURI =" + uri.getRequestURI().toString());
   System.out.println ("Port = " +
             uri.getPort ());

   System.out.println ("Query = " +
             uri.getQuery ());

   System.out.println ("Scheme = " +
             uri.getScheme ());

   System.out.println ("Scheme-specific part = " +
             uri.getSchemeSpecificPart ());

   System.out.println ("User Info = " +
             uri.getUserInfo ());

   System.out.println ("URI is absolute: " +
             uri.isAbsolute ());

   System.out.println ("URI is opaque: " +
             uri.isOpaque ());
  }
}