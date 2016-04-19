package com.telanganatourism.android.phase2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WebServiceCalls {

	public String result = null;
	String ff = null;

	public String urlPost(String url) {

		Log.e("V", "" + url);
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 5000);
		HttpClient client = new DefaultHttpClient(httpParameters);
		try {

			HttpPost post = new HttpPost(url);

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String re = EntityUtils.toString(entity, HTTP.UTF_8);
				// ff = re;
				// Log.e("result",""+ff);

				result = re.trim();
			}

		} catch (SocketTimeoutException e) {
			client.getConnectionManager().shutdown();
			urlPost(url);

		} catch (Exception e) {
			Log.e("", "" + e);
			client.getConnectionManager().shutdown();
			// urlPost(url);
			return null;
		}

		return result;

	}

	public String urlGet(String url) {

		try {
			Log.e("R", "URL" + url);
			HttpClient client = new DefaultHttpClient();
			HttpGet post = new HttpGet(url);
			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String re = EntityUtils.toString(entity, HTTP.UTF_8);
				Log.e("result", "" + result);

				result = re.trim();
			}

		} catch (Exception e) {
			Log.e("", "" + e);
		}

		return result;

	}

	public String urlPostNew(String url) {
		HttpClient hClient = new DefaultHttpClient();
		HttpPost hPost = new HttpPost(url);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		try {
			hPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = hClient.execute(hPost);
			HttpEntity hEntity = response.getEntity();
			InputStream is = hEntity.getContent();

			// hClient.getConnectionManager().shutdown();

			result = convertStreamToString(is);

		} catch (UnsupportedEncodingException e) {
			hClient.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			hClient.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			hClient.getConnectionManager().shutdown();
			e.printStackTrace();
		}

		return result;

	}

	InputStream is;
	JSONObject jObj = null;
	String json = "";

	public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			// httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.e("Error", "");
		} catch (ClientProtocolException e) {
			Log.e("Error", "");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Error", "");
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String getJSONString(String url) {
		String jsonString = null;

		Log.e("Request", "url" + url);

		// try {
		// URL linkurl = new URL(url);
		// linkConnection = (HttpURLConnection) linkurl.openConnection();
		// int responseCode = linkConnection.getResponseCode();
		// if (responseCode == HttpURLConnection.HTTP_OK) {
		// InputStream linkinStream = linkConnection.getInputStream();
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// int j = 0;
		// while ((j = linkinStream.read()) != -1) {
		// baos.write(j);
		// }
		// byte[] data = baos.toByteArray();
		// jsonString = new String(data);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (linkConnection != null) {
		// linkConnection.disconnect();
		// }
		// }

		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String re = EntityUtils.toString(entity, HTTP.UTF_8);

				jsonString = re.trim();
			}

		} catch (Exception e) {
			Log.e("", "" + e);
		}
		return jsonString;
	}

	public static String post(String endpoint) throws IOException {

		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		// Iterator<Entry<String, String>> iterator =
		// params.entrySet().iterator();
		// constructs the POST body using the parameters
		/*
		 * while (iterator.hasNext()) { Entry<String, String> param =
		 * iterator.next(); bodyBuilder.append(param.getKey()).append('=')
		 * .append(param.getValue()); if (iterator.hasNext()) {
		 * bodyBuilder.append('&'); } }
		 */
		String body = bodyBuilder.toString();
		Log.v("TAG", "Posting '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			Log.e("URL", "> " + url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}

			if (status == 200) {
				InputStream str = conn.getInputStream();

				Log.e("Responce", "" + convertStreamToString(str));
			}

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

}
