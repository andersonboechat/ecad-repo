package br.org.ecad.ws;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;

public class TwitterWS {

	private static final String CONSUMER_KEY = "sJiP5hPUPrqh4B7ZSomOyI3zI";

	private static final String CONSUMER_SECRET = "3CVhdqhpy0QyjeVP6tFLYcIbVjSEBAooc6t98a4BXRwzVK0gR4";

	private static final String TOKEN_KEY = "632874713-HivICGe0Xlh3Ni2EAIncIb5JguXNJ2W6nZj0nGBD";

	private static final String TOKEN_SECRET = "qTd4MIo9AnGbbqqbXsRKZRi4YJU8YkEStQJ4efnsBMVS7";

	private static final String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json";

	public static String buscar(String query) {
		return TwitterWS.buscar(query, SEARCH_URL);
	}

	public static String buscar(String query, String url) {
		Client client = Client.create();

		OAuthSecrets secrets = new OAuthSecrets().consumerSecret(CONSUMER_SECRET);
		secrets.setTokenSecret(TOKEN_SECRET);
		
		OAuthParameters params = new OAuthParameters().consumerKey(CONSUMER_KEY).signatureMethod("HMAC-SHA1").version("1.0").token(TOKEN_KEY);
		
		OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);

		WebResource webResource = client.resource(url).queryParam("q", query)
										.queryParam("count", "100").queryParam("tweet_mode", "extended");
        webResource.addFilter(filter);

		return webResource.get(String.class);
		
	}
}
