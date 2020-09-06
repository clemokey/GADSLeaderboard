package dev.bamideleoke.gadsleaderboard.userhome.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import dev.bamideleoke.gadsleaderboard.userhome.adapters.SkillAdapter;
import dev.bamideleoke.gadsleaderboard.userhome.objects.SkillEntry;
import dev.bamideleoke.gadsleaderboard.userhome.objects.SkillEntry;
import dev.bamideleoke.gadsleaderboard.userhome.objects.SkillEntry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSkill extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final Context context;
    private final TabLayout tabLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    private SkillAdapter adapter;
    private RecyclerView recyclerView;

    public FragmentSkill(Context mContext, TabLayout mTabLayout) {
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
        View view = inflater.inflate(R.layout.fragment_skill, container, false);

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
            getSkillIqLeaders();
        });
        return view;
    }

    private void getSkillIqLeaders() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<SkillEntry>> call = service.getSkillIqLeaders();
        call.enqueue(new Callback<List<SkillEntry>>() {
            @Override
            public void onResponse(Call<List<SkillEntry>> call, Response<List<SkillEntry>> response) {
                swipeRefreshLayout.setRefreshing(false);
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<SkillEntry>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<SkillEntry> skillList) {
        List<SkillEntry> sortedList = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sortedList = skillList.stream()
                    .sorted(Comparator.comparing(SkillEntry::getScore).reversed())
                    .collect(Collectors.toList());
            adapter = new SkillAdapter(context, sortedList);
        } else {
            adapter = new SkillAdapter(context, skillList);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        getSkillIqLeaders();
    }

}
