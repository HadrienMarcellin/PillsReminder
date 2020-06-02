package com.example.pillsreminder.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.pillsreminder.R;
import com.example.pillsreminder.common.server.RequestHandler;
import com.example.pillsreminder.common.server.ServerHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PainOnlineTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final Logger LOGGER = Logger.getLogger( PainOnlineTabFragment.class.getName() );

    public final static CharSequence title = "Online DB";
    private String url = "http://192.168.1.24";
    private ProgressDialog pDialog;
    private EditText nameText;
    private EditText partNumberText;
    ListView listView;

    private RequestHandler requestHandler;
    private RequestHandler fillDB;
    private ServerHandler serverHandler;

    public PainOnlineTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pain_online_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        listView = (ListView) getView().findViewById(R.id.online_db_listview);
        String url = "http://192.168.1.24";
        String getURL = "/api/db_view_test.php";
        String postURL = "/api/fill_table.php";
        Calendar cal = Calendar.getInstance();
        cal.set(1996, 1, 30);
        Timestamp tm = new Timestamp(cal.getTimeInMillis());
        String postRequest = "{\"level\": 13, \"inflammation\": 2, \"date\": \"" +  tm.toString() + "\"}";
        LOGGER.log(Level.INFO, "Send POST request to: " + url);

//        this.retrieveDB = new RetrieveDB(url);
        this.fillDB = new RequestHandler(url);

//        this.retrieveDB.setDataReceivedListener(new RetrieveDB.DataReceivedListener() {
//            @Override
//            public void onDataReceived(String string) {
//                try {
//                    loadIntoListView(string);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//        this.serverHandler = new ServerHandler(getView(), R.id.online_db_listview, getURL);

        this.fillDB.execute(postURL, postRequest);
//        this.retrieveDB.execute(getURL, "");

    }

    @Override
    public void onDestroy() {
        fillDB.setDataReceivedListener(null); // PREVENT LEAK AFTER ACTIVITY DESTROYED
        super.onDestroy();
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, stocks);
        listView.setAdapter(arrayAdapter);
    }
}
