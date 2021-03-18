package it.polito.dp2.TAMELESS.ass;

import it.polito.dp2.BIB.FactoryConfigurationError;

/**
 * Defines a factory API that enables applications to access to information
 * about items and bookshelves implementing the {@link Client} interface.
 *
 */
public abstract class ClientFactory {
	private static final String propertyName = "it.polito.dp2.TAMELESS.ass.ClientFactory";

	protected ClientFactory() {
	}

	public static ClientFactory newInstance() throws FactoryConfigurationError {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		if (loader == null) {
			loader = ClientFactory.class.getClassLoader();
		}

		String className = System.getProperty(propertyName);
		if (className == null) {
			throw new FactoryConfigurationError("cannot create a new instance of a ClientFactory"
					+ "since the system property '" + propertyName + "'" + "is not defined");
		}

		try {
			Class<?> c = (loader != null) ? loader.loadClass(className) : Class.forName(className);
			return (ClientFactory) c.newInstance();
		} catch (Exception e) {
			throw new FactoryConfigurationError(e, "error instantiatig class '" + className + "'.");
		}
	}
	
	public abstract Client newClient() throws ClientException;
}
