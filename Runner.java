//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.io.File;
import javax.swing.JFrame;
public class Runner 
{
    public static void main(String args[])// throws IOException, URISyntaxException
    {
        /*String currentPath = Runner.class
            .getProtectionDomain()
            .getCodeSource().getLocation()
            .toURI().getPath()
            .replace('/', File.separator.charAt(0)).substring(1);
        if(args.length==0 && Runtime.getRuntime().maxMemory()/1024/1024<980)
        {
            Runtime.getRuntime().exec("java -jar -Xmx1024m "+currentPath+" restart");
            return;
        }*/
        
        
        JFrame frame = new JFrame("Crossing Code");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen s = new Screen();
        frame.add(s);
        frame.pack();
        frame.setVisible(true);
        s.animate();
    }
}