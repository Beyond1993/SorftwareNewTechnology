package com.leaf.sqlquery.util;

import android.annotation.SuppressLint;
import java.io.BufferedReader;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


public class HttpData {

	// private HttpEntity entity;
	//这里传入4个参数，httpurl为服务器端的访问地址，
	//key[],value[]相当于map里的<key,value>,keyNum为键值对个数
	//返回一个httpEntity,使用getContent方法取得返回的内容
	private HttpEntity httpClientPost(String httpUrl, String key[],
			String value[], int keyNum) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpEntity entity;
		HttpPost post = new HttpPost(httpUrl);

		InputStream is = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < keyNum; i++) {
			params.add(new BasicNameValuePair(key[i], value[i]));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			int code = response.getStatusLine().getStatusCode();
			entity = response.getEntity();
			if (entity != null) {
				// long x = entity.getContentLength();
				// is = entity.getContent();
				// x++;
			}
			return entity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;

	}

	//eg:message为发送的信息内容
	//entity.getContent()得到一个InputStream，result为服务器返回内容
	public ArrayList<HashMap<String, String>> searchId(String message, String httpUrl) {
		String key[] = { "id" };
		String value[] = { message };
		HttpEntity entity = httpClientPost(httpUrl, key, value, 1);
		InputStream is;
		InputStreamReader isr;
		BufferedReader br;
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map;
		try {
			is = entity.getContent();
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);
			String line = br.readLine();
			if (line.equals("0")) {
				return null;				
			}
			while(line!=null)
			{
				//农资综合补贴	李功军	xxx2231969xxxx3819	2013-01-15	540.2800
				String[] lineInfo=line.split("\t");
				map=new HashMap<String, String>();
				map.put("subsidyName",lineInfo[0]);
				map.put("userName", lineInfo[1]);
				map.put("userId", lineInfo[2]);
				map.put("subsidyDate",lineInfo[3]);
				map.put("money", lineInfo[4]);
				result.add(map);
				line=br.readLine();
			}
			isr.close();
			is.close();
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;

	}

	public  ArrayList<HashMap<String, String>> ListAllfile(String httpUrl){
		String key[] = {"0"};
		String value[] = {"0"};
		HttpEntity entity = httpClientPost(httpUrl, key, value, 0);
		InputStream is;
		InputStreamReader isr;
		BufferedReader br;
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map;
		try {
			is = entity.getContent();
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);
			String line = br.readLine();
			String[] lineinfo=line.split("\t");
			for (int i = 0; i < lineinfo.length; i++) {
				map = new HashMap<String, String>();
				map.put("fileName", lineinfo[i]);
				result.add(map);
			}
			isr.close();
			is.close();
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String downloadTemp(String httpUrl, String fname) {
		OutputStream outputstream = null;
		java.io.File inputFile = null;
		String key[] = { "name" };
		String keyContent[] = { fname };
		HttpEntity entity = httpClientPost(httpUrl, key, keyContent, 1);

		// 从服务器获取字节流
		inputFile = new java.io.File("/sdcard/download/" + fname);
		try {
			outputstream = new FileOutputStream(inputFile);
			entity.writeTo(outputstream);

			outputstream.flush();
			outputstream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/sdcard/download/" + fname;

	}
	



}
