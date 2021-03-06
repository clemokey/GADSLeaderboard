package dev.bamideleoke.gadsleaderboard.userhome.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import dev.bamideleoke.gadsleaderboard.R;
import dev.bamideleoke.gadsleaderboard.network.GetDataService;
import dev.bamideleoke.gadsleaderboard.network.RetrofitClientInstance;
import dev.bamideleoke.gadsleaderboard.userhome.adapters.LearningAdapter;
import dev.bamideleoke.gadsleaderboard.userhome.objects.LearningEntry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLearning extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final Context context;
    private final TabLayout tabLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    private LearningAdapter adapter;
    private RecyclerView recyclerView;

    public FragmentLearning(Context mContext, TabLayout mTabLayout) {
        this.context = mContext;
        this.tabLayout = mTabLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learning, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.post(() -> {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
            }
            // Fetch data from server
            getLearningLeaders();
        });
        return view;
    }

    private void getLearningLeaders() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<LearningEntry>> call = service.getLearningLeaders();
        call.enqueue(new Callback<List<LearningEntry>>() {
            @Override
            public void onResponse(Call<List<LearningEntry>> call, Response<List<LearningEntry>> response) {
                swipeRefreshLayout.setRefreshing(false);
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<LearningEntry>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void generateDataList(List<LearningEntry> learnersList) {
        List<LearningEntry> sortedList = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sortedList = learnersList.stream()
                    .sorted(Comparator.comparing(LearningEntry::getHours).reversed())
                    .collect(Collectors.toList());
            adapter = new LearningAdapter(context, sortedList);
        } else {
            adapter = new LearningAdapter(context, learnersList);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onRefresh() {
        getLearningLeaders();
    }

}