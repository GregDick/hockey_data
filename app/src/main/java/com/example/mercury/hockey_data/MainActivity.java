package com.example.mercury.hockey_data;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.mercury.hockey_data.GlobalConstants.BASE_API_URL;
import static com.example.mercury.hockey_data.GlobalConstants.TEAMS_ENDPOINT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.teams)
    TextView teamsView;

    @BindView(R.id.team_list)
    RecyclerView teamRecyclerView;

    private Unbinder unbinder;
    private Disposable subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAndDisplayData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscription != null){
            subscription.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void fetchAndDisplayData() {
        Flowable<ArrayList<Team>> dataStream = Flowable.create(subscriber -> {
            ArrayList<Team> parsedData = parseData(fetchData());
            subscriber.onNext(parsedData);

            subscriber.onComplete();

        }, BackpressureStrategy.BUFFER);

        subscription = dataStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        MainActivity.this::displayData,
                        err -> Log.e("fetchAndDisplayData", "Subscriber received error: " + err.getLocalizedMessage()),
                        () -> Log.d("fetchAndDisplayData", "Subscriber got Completed event")
                );
    }

    private ArrayList<Team> parseData(String rawJson) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(rawJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Team> teamList = new ArrayList<Team>();
        return null;
    }

    private String fetchData() throws IOException {
        Log.d("fetchData", "fetching data");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_API_URL + TEAMS_ENDPOINT)
                .build();

        Response response = client.newCall(request).execute();
        return response.body() != null ? response.body().string() : "";
    }

    private void displayData(ArrayList<Team> data) {
        Log.d("displayData", "data : " + data);
        teamsView.setText("data");
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TeamsAdapter adapter = new TeamsAdapter(this, data);
        teamRecyclerView.setAdapter(adapter);
    }
}

