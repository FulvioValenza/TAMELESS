package it.polito.dp2.TAMELESS.sol.client;

import java.net.URI;
import java.net.URISyntaxException;

import it.polito.dp2.TAMELESS.ass.Client;
import it.polito.dp2.TAMELESS.ass.ClientException;


public class ClientFactory extends it.polito.dp2.TAMELESS.ass.ClientFactory{

	@Override
	public Client newClient() throws ClientException {
		URI  uri = null;
		try {
			//String uriProp = System.getProperty("it.polito.dp2.BIB.ass3.URL");
			String uriProp = System.getProperty("it.polito.dp2.TAMELESS.ass.URL");
			System.out.println("property: "+uriProp);
			if(uriProp!=null) {
				uri= new URI(uriProp);
			}else {
				//uri= new URI("http://localhost:8080/BibSystem/rest/");
				uri= new URI("http://localhost:8080/TAMELESS/rest/");
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new ClientFactoryImpl(uri);
	}

}

