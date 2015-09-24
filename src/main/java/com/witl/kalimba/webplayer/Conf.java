package com.witl.kalimba.webplayer;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
//import java.nio.file.Paths;



public class Conf
{
        private File file;

       /* public Conf()
        {
       
        	try {
        		URL resource=Conf.class.getResource("gui.conf");
				file=Paths.get(resource.toURI()).toFile();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        //    file = new File("resources/gui.conf");
        }
        public Conf(String path)
        {
         file = new File(path);
        }

        public String getVal(String param)
        {
                String val="";
                

                  //System.out.println("file path from Conf class====="+file);
                try
                {
                      BufferedReader reader = new BufferedReader(new FileReader(file));
                      String line = "";

                        while((line = reader.readLine()) != null)
                        {
                                if(line.trim().startsWith(param))
	       {
                                        val = line.substring(line.indexOf("=")+1).trim();
                                }

                        }
                        val.trim();
                        if(reader!=null)
                          reader.close();

                }
                catch(Exception e)
                {
                   System.out.println(e);
                }
                return val;
        }*/

}
