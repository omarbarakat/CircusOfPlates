package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MyClassLoader extends ClassLoader {

	private static MyClassLoader instance;

	private MyClassLoader() {
		super();
	}

	public static MyClassLoader getInstance() {
		if (instance == null)
			instance = new MyClassLoader();

		return instance;
	}

	public Class<?> loadClass(File file) {
		try {
			URL myUrl = file.toURI().toURL();
			URLConnection connection = myUrl.openConnection();
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int data = input.read();

			while (data != -1) {
				buffer.write(data);
				data = input.read();
			}
			
			input.close();
			byte[] classData = buffer.toByteArray();

			return defineClass(null, classData, 0, classData.length);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}