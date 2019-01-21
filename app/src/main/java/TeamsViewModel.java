import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.mercury.hockey_data.Team;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.mercury.hockey_data.GlobalConstants.BASE_API_URL;
import static com.example.mercury.hockey_data.GlobalConstants.TEAMS_ENDPOINT;

public class TeamsViewModel extends ViewModel {

    private MutableLiveData<List<Team>> teams;
    public LiveData<List<Team>> getTeams() {
        if (teams == null) {
            teams = new MutableLiveData<List<Team>>();
            try {
                loadTeams();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return teams;
    }

    private void loadTeams() throws IOException {
        teams = parseData(fetchData());
    }

    private MutableLiveData<List<Team>> parseData(String rawJson) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(rawJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //todo actually parse the json
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
}
