package nearby.aravind.nearby;

import nearby.aravind.nearby.model.Places;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlacesAPI {

    String endurl = "maps/api/place/nearbysearch/json";

    @GET(endurl)
    Call<Places> getPlaces(
            @Query("location") String location,
            @Query("radius") Double radius,
            @Query("types") String types,
            @Query("name") String name,
            @Query("key") String key);
}
