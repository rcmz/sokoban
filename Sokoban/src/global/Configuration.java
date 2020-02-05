package global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import structures.*;
import java.util.logging.*;

public class Configuration {
	private static Configuration configuration = null;
	
	private Configuration(){}
	
	public static Configuration instance() {
		if (configuration == null) {
			configuration = new Configuration();
		}
		
		return configuration;
	}
	
	public String lis(String property){
		Properties properties = instanciateProperties();
		
		String value = properties.getProperty(property);
	
		if (value == null) {
			throw new NoSuchElementException("La propriété passée en paramètre n'a pas de correspondance dans le fichier de configuration.");
		}
		
		return value;
	}
	
	public <T> Sequence<T> nouvelleSequence() {
		Properties properties = instanciateProperties();
		
		String value = properties.getProperty("Sequence");
		
		if (value.equals("Tableau")) {
			return new Tableau<T>();
		} else {
			return new ListeChainee<T>();
		}
	}
	
	public Logger logger() {
		Logger logger = Logger.getLogger("Sokoban.Logger");
		Properties properties = instanciateProperties();
		String value = properties.getProperty("LogLevel"); //Voir dans le cas où le champ n'apparait pas ?
		
		logger.setLevel(Level.parse(value));
		
		return logger;
	}
	
	private Properties instanciateProperties() {
		Properties properties = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream("default.cfg");

			properties.load(fis);
		} catch (FileNotFoundException e){
			System.out.println("Fichier de configuration par défaut absent.");
		} catch (IOException e) {
			System.out.println("Impossible de charger les propriétés.");
		}
		
		try {
			String path = System.getProperty("user.home") + "/.sokoban";
			
			FileInputStream fis2 = new FileInputStream(path);

			properties.load(fis2);
		} catch (FileNotFoundException e){
			System.out.println("Aucun sur-fichier de configuration trouvé : configuration par défaut utilisée.");
		} catch (IOException e) {
			System.out.println("Impossible de charger les propriétés.");
		}
		
		return properties;
	}
}
