package com.fixit.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fixit.domain.vo.User;

public class MailChimpApi {

	public static String listUrl = "https://us12.api.mailchimp.com/3.0/lists";

	public static String getListid() {
		String id = null;

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(listUrl);

		try {
			String encodedDuke = DatatypeConverter
					.printBase64Binary(mailChimpAuthorization().getBytes("UTF-8"));
			request.setHeader("Authorization", "Basic " + encodedDuke);

			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			org.json.simple.JSONObject object = null;

			JSONParser parser = new JSONParser();
			object = (org.json.simple.JSONObject) parser.parse(result.toString());

			org.json.simple.JSONArray array = (org.json.simple.JSONArray) object.get("lists");
			String listName;
			if (array.size() > 0) {
				for(int i=0;i<array.size();i++){

					org.json.simple.JSONObject ob1 = (org.json.simple.JSONObject) array.get(i);
					listName = (String)ob1.get("name");
					if(listName!= null  && listName.equals(FixitVariables.MAILCHIMP_LIST)){
						id = (String) ob1.get("id");
						break;
					}
				}
			}

		} catch (Exception e) {

		}

		return id;
	}

	@SuppressWarnings("unchecked")
	public static void saveUserToMailChimp(User user, String id) {
		String addingUserUrl = listUrl + "/" + id + "/" + "members";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(addingUserUrl);
		try {
			String encodedDuke = DatatypeConverter
					.printBase64Binary(mailChimpAuthorization().getBytes("UTF-8"));
			post.setHeader("Authorization", "Basic " + encodedDuke);
			post.setHeader("Content-Type", "application/json");
			JSONObject obj = new JSONObject();
			obj.put("email_address", user.getEmail());
			obj.put("status", "subscribed");
			JSONObject mergeVal = new JSONObject();
			mergeVal.put("FNAME",user.getFirstName());
			mergeVal.put("LNAME",user.getLastName());
			obj.put("merge_fields", mergeVal);
			
			StringEntity postingString = new StringEntity(obj.toJSONString());
			post.setEntity(postingString);
			HttpResponse response = client.execute(post);
		} catch (Exception e) {

		}
	}
	
	public static String mailChimpAuthorization(){
		StringBuilder builder = new StringBuilder("user:");
		builder.append(FixitVariables.MAILCHIMP_KEY);
		return builder.toString();
		
	}

}
