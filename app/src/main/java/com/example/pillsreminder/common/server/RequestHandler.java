package com.example.pillsreminder.common.server;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestHandler extends AsyncTask<String, Void, String> {

    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());
    public String  url_server = "";
    private String response = "";
    private DataReceivedListener listener;

    public RequestHandler(String url_server_) {
        this.url_server = url_server_;
    }

    @Override
    protected String doInBackground(String... strings) {

        assert strings.length == 2;
        String url_actionfile = strings[0];
        String urlParameters = strings[1];

        String url_string = this.url_server + url_actionfile;


        try {
            LOGGER.log(Level.INFO, "Connecting to: " + url_string);
            URL url = new URL(url_string);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST"); // PUT is another valid option
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");

            byte[] postData = urlParameters.getBytes("utf-8");
            try(OutputStream wr = con.getOutputStream()) {
//                LOGGER.log(Level.INFO, "out : " + String.valueOf(urlParameters));
                wr.write(postData, 0, postData.length);
            }

            StringBuilder content;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                String line;
                content = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            con.disconnect();
            return content.toString().trim();

        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Failed to connect to db: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onPostExecute(String s) {
        this.response = s;
        LOGGER.log(Level.INFO, "Received database: " + this.response);
        if (listener != null) {
            listener.onDataReceived(this.response);
        }
    }

    public String getResponse() {
        return response;
    }

    public void setDataReceivedListener(DataReceivedListener listener) {
        this.listener = listener;
    }

    public interface DataReceivedListener   {
        void onDataReceived(String string);
    }
}
