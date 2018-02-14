package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
 *JSON����
 * @author Chow
 * @since 0.0.1
 */
public class JsonReader {
	public static JSONObject read(HttpServletRequest request)
			throws IOException, UnsupportedEncodingException {
		// ��ȡ��������
		BufferedReader br = new BufferedReader(
				new InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		// ��json�ַ���ת��Ϊjson����
		JSONObject json = JSONObject.fromObject(sb.toString());
		return json;
	}
}
