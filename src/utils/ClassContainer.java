package utils;

import java.io.File;
import java.util.Hashtable;
import model.plate.PlatePool;
import model.plate.factory.Plate;

public class ClassContainer {

	private static ClassContainer instance;

	private Hashtable<String, Class<?>> supportedClasses;

	private ClassContainer() {
		supportedClasses = new Hashtable<String, Class<?>>();
	}

	public static ClassContainer getInstance() {
		if (instance == null)
			instance = new ClassContainer();

		return instance;
	}

	public Hashtable<String, Class<?>> getSupportedClasses() {
		return supportedClasses;
	}

	public void setSupportedClasses(Hashtable<String, Class<?>> supportedClasses) {
		this.supportedClasses = supportedClasses;
	}

	public void loadClass(File classFile) throws Exception {
		Class<?> c = MyClassLoader.getInstance().loadClass(classFile);
		if (c == null || !c.getSuperclass().equals(Plate.class)) {
			throw new Exception("Not A Valid Class");
		}
		supportedClasses.put(c.getSimpleName(), c);
		PlatePool.getInstance().update();
	}

	public static void setInstance(ClassContainer instance) {
		ClassContainer.instance = instance;
	}

}
