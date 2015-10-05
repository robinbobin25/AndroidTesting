package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by antonina_mykhailenko on 05.10.2015.
 */
public class PropertiesReader {


    public String getPropertyValue(String property) {

        File configFile = new File("config.properties");
        String result = "";

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            result = props.getProperty(property);

            reader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File was not found because of: \n" + ex);
        } catch (IOException ex) {
            System.out.println("Some problems with I/O because of: \n" + ex);
        }

        return result;
    }
}
