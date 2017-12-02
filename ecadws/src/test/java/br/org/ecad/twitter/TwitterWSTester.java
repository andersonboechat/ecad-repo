package br.org.ecad.twitter;

import br.org.ecad.ws.TwitterWS;

public class TwitterWSTester {

	public void testBuscar() throws Exception {
		System.out.println(TwitterWS.buscar("brasil"));
		System.out.println(TwitterWS.buscar("brasil", "http://localhost:8089/1.1/search/tweets.json"));
	}
}
