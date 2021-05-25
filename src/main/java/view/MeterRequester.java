package main.java.view;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class MeterRequester {
	public static List<MetersView> getMeters(String targetURL) {
		HttpURLConnection connection = null;
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			System.out.println(targetURL);

			// This line makes the request
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line).append('\r');
			}
			rd.close();

			return fromJsonAsList(response.toString(), MetersView[].class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.disconnect();
		}

	}

	public static <T> List<T> fromJsonAsList(String json, Class<T[]> clazz) {
		return Arrays.asList(new Gson().fromJson(json, clazz));
	}

}
