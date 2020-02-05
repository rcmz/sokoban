import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;

public class Config {
	static Properties properties = new Properties();

	public static void main (String[] args) {
		try {
			loadConfig();
		} catch (FileNotFoundException e){
			System.out.println("Aucun fichier .sokoban trouvé.");
		} catch (IOException e) {
		
		}
	}

	public static void loadConfig() throws FileNotFoundException, IOException{
		FileInputStream fis = new FileInputStream("default.cfg");

		properties.load(fis);
	
		String path = System.getProperty("user.home") + "/.sokoban";
	
		FileInputStream fis2 = new FileInputStream(path);

		properties.load(fis2);

		System.out.println(properties.getProperty("Sequence"));
	}
}

