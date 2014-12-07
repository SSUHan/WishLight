package com.example.thewishlight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class phpConnect extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... urls) {
		StringBuilder jsonHtml = new StringBuilder();
		try {
			// ���� url ����
			URL url = new URL(urls[0]);
			// Ŀ�ؼ� ��ü ����
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
			// ����Ǿ�����.
			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);
				// ����Ǿ��� �ڵ尡 ���ϵǸ�.
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream(),
									"UTF-8"));
					for (;;) {
						// ���� �������� �ؽ�Ʈ�� ���δ����� �о� ����.
						String line = br.readLine();
						Log.d("line", line);
						if (line == null)
							break;
						// ����� �ؽ�Ʈ ������ jsonHtml�� �ٿ�����
						jsonHtml.append(line + "\n");
					}
					br.close();
				}

				conn.disconnect();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonHtml.toString();

	}

	protected void onPostExecute(String str) {
		

	}
}
