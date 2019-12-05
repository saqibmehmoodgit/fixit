package com.fixit.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Utility {

	public static Map<String, Object> getSuccessResponse(Object... responseContent) {

		if (responseContent.length % 2 != 0) {
			throw new IllegalArgumentException();
		}
		Map<String, Object> responseObject = new HashMap<String, Object>();
		responseObject.put("status", "success");
		Map<String, Object> responseList = new HashMap<String, Object>();

		for (int i = 0; i < responseContent.length; i = i + 2) {
			responseList.put(responseContent[i].toString(), responseContent[i + 1]);
		}
		responseObject.put("result", responseList);
		return responseObject;
	}

	public static Map<String, Object> getFailureResponse(Object... responseContent) {
		if (responseContent.length % 2 != 0) {
			throw new IllegalArgumentException();
		}
		Map<String, Object> responseObject = new HashMap<String, Object>();
		responseObject.put("status", "failed");
		if (responseContent.length > 0)
			responseObject.put("error", responseContent[0]);
		return responseObject;
	}

	public static byte[] loadFile(String file) throws IOException {
		try {
			URL url = new URL(file);
			InputStream is = url.openStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			int n;
			while ((n = is.read(buf)) >= 0)
				os.write(buf, 0, n);
			os.close();
			is.close();
			byte[] data = os.toByteArray();
			return data;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;

		}

	}


}
