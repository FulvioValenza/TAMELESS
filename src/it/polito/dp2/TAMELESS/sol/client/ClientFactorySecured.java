package it.polito.dp2.TAMELESS.sol.client;

import java.net.URI;
import java.net.URISyntaxException;

import it.polito.dp2.TAMELESS.ass.Client;
import it.polito.dp2.TAMELESS.ass.ClientException;

public class ClientFactorySecured extends it.polito.dp2.TAMELESS.ass.ClientFactory{

	@Override
	public Client newClient() throws ClientException {
		URI  uri = null;
		try {
			String uriProp = System.getProperty("it.polito.dp2.TAMELESS.ass.URL");
			System.out.println("property: "+uriProp);
			if(uriProp!=null) {
				uri= new URI(uriProp);
			}else {
				uri= new URI("https://localhost:8080/TAMELESS/rest/");
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new ClientFactoryImplSecured(uri);
	}

}

