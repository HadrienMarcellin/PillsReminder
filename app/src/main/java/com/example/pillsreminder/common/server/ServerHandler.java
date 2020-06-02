package com.example.pillsreminder.common.server;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHandler extends RequestHandler {

    public ListView listView;
    private View view;
    public static final Logger LOGGER = Logger.getLogger(ServerHandler.class.getName());

    public ServerHandler(View view, int listview_id, String url_server_) {
        super(url_server_);
        this.listView = view.findViewById(listview_id);
        this.view = view;
    }

    @Override
    public void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            loadIntoListView(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONObject main_obj = new JSONObject(json);

        if(main_obj.getInt("success") == 0) {
            LOGGER.log(Level.WARNING, "No data received from server.");
            return;
        }
        LOGGER.log(Level.INFO, "Data received from server and ready for display.");

        JSONArray jsonArray = main_obj.getJSONArray("data");
        String[] stocks = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            stocks[i] = obj.getString("level") + " " + obj.getString("inflammation");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, stocks);
        listView.setAdapter(arrayAdapter);
    }
}
