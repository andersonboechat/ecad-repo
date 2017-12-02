package br.org.ecad.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Path("/tweet")
public class EcadWS {

	@Inject
	private EntityManager manager;
	
	@Resource
	UserTransaction tx;

	@POST
	@Path("/captar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCaptar(String jsonParam) {
		try {
			JSONObject jsonObj = new JSONObject(jsonParam);
			
			String jsonString = TwitterWS.buscar(jsonObj.getString("busca"));

			jsonObj = new JSONObject(jsonString);
			JSONArray array = jsonObj.optJSONArray("statuses");

			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					try {
						tx.begin();
						manager.persist(new Tweet(obj.getLong("id"), obj.getString("full_text")));
						manager.flush();
						tx.commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity("success").build();
	}
	
	@POST
	@Path("/procurar")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response postProcurar(String jsonParam) {
		try {
			String word = new JSONObject(jsonParam).getString("busca");
			List<Tweet> tweets = manager.createQuery("FROM Tweet").getResultList();
			List<Tweet> matches = new ArrayList<Tweet>();
			
			for (Tweet tweet : tweets) {
				String[] words = tweet.getTexto().split("\\s+");

				for (String w : words) {
					if (StringUtils.getJaroWinklerDistance(word, w) >= 0.85) {
						matches.add(tweet);
						break;
					}
				}
			}

			JsonElement element = new Gson().toJsonTree(matches);
			JsonObject result = new JsonObject();
			result.add("tweets", element.getAsJsonArray());

			return Response.status(200).entity(result.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{idTweet}/estatisticas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstatisticas(@PathParam("idTweet") String idTweet) {
		try {
			Tweet tweet = manager.find(Tweet.class, new Long(idTweet));

			List<String> words = Arrays.asList(tweet.getTexto().split("\\s+"));

			Collections.sort(words, new LengthComparator());
			
			HashSet<Integer> lengths =  new HashSet<Integer>();
			for (String word : words) {
				lengths.add(word.length());
			}

			int sum = 0;
			for (Integer length : lengths) {
				sum += length;
			}

			JsonObject result = new Gson().toJsonTree(tweet).getAsJsonObject();
			result.addProperty("palavras", words.size());
			result.addProperty("mais_curta", words.get(0));
			result.addProperty("mais_longa", words.get(words.size() - 1));
			result.addProperty("media", sum/lengths.size());

			return Response.status(200).entity(result.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/mais_comum")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response getMaisComum() {
		try {
			List<Tweet> tweets = manager.createQuery("FROM Tweet").getResultList();
			List<String> words = new ArrayList<String>();
			List<String> mostCommonWords = new ArrayList<String>();

			for (Tweet tweet : tweets) {
				String text = tweet.getTexto().toLowerCase();
				words.addAll(Arrays.asList(text.split("\\s+")));
			}

			Collections.sort(words, new FrequencyComparator(words));

			for (int i = words.size() - 1; i > 0; i--) {
				if (Collections.frequency(words, words.get(i)) < Collections.frequency(words, words.get(words.size() - 1))){
					break;
				}

				mostCommonWords.add(words.get(i));
			}

			mostCommonWords = new ArrayList<String>(new HashSet<String>(mostCommonWords));

			JsonObject result = new JsonObject();
			
			if (mostCommonWords.size() == 1) {
				result.addProperty("mais_comum", mostCommonWords.get(0));
			} else {
				result.add("mais_comuns", new Gson().toJsonTree(mostCommonWords).getAsJsonArray());
			}

			return Response.status(200).entity(result.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/mais_longa")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response getMaisLonga() {
		try {
			List<Tweet> tweets = manager.createQuery("FROM Tweet").getResultList();
			List<String> words = new ArrayList<String>();
			List<String> longestWords = new ArrayList<String>();

			for (Tweet tweet : tweets) {
				words.addAll(Arrays.asList(tweet.getTexto().split("\\s+")));
			}

			Collections.sort(words, new LengthComparator());

			for (int i = words.size() - 1; i > 0; i--) {
				if (words.get(i).length() < words.get(words.size() - 1).length()) {
					break;
				}

				longestWords.add(words.get(i));
			}

			JsonObject result = new JsonObject();

			if (longestWords.size() == 1) {
				result.addProperty("mais_longa", longestWords.get(0));
			} else {
				result.add("mais_longas", new Gson().toJsonTree(longestWords).getAsJsonArray());
			}

			return Response.status(200).entity(result.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/exatamente_em/{numeroRepeticoes}")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response getExatamenteEm(@PathParam("numeroRepeticoes") String numeroRepeticoes) {
		try {
			List<Tweet> tweets = manager.createQuery("FROM Tweet").getResultList();
			List<String> words = new ArrayList<String>();
			List<String> theWords = new ArrayList<String>();
			int frequency = new Integer(numeroRepeticoes);

			for (Tweet tweet : tweets) {
				String text = tweet.getTexto().toLowerCase();
				words.addAll(Arrays.asList(text.split("\\s+")));
			}

			for (String word : new HashSet<String>(words)) {
				if (Collections.frequency(words, word) == frequency) {
					theWords.add(word);
				}
			}

			JsonObject result = new JsonObject();

			if (!theWords.isEmpty()) {
				if (theWords.size() == 1) {
					result.addProperty("palavra", theWords.get(0));
				} else {
					result.add("palavras", new Gson().toJsonTree(theWords).getAsJsonArray());
				}
			}

			return Response.status(200).entity(result.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

}
