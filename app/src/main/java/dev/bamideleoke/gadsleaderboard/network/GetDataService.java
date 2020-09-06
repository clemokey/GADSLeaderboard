package dev.bamideleoke.gadsleaderboard.network;

import java.util.List;

import dev.bamideleoke.gadsleaderboard.userhome.objects.LearningEntry;
import dev.bamideleoke.gadsleaderboard.userhome.objects.SkillEntry;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("/api/hours")
    Call<List<LearningEntry>> getLearningLeaders();

    @GET("/api/skilliq")
    Call<List<SkillEntry>> getSkillIqLeaders();

}
