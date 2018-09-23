package nearby.aravind.nearby;

import nearby.aravind.nearby.model.Places;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlacesAPI {

    String endurl = "maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&key=AIzaSyDvT2mQtjo-XuAiBT5EuIyXxLtcQYmB59M";

    @GET(endurl)
    Call<Places> getPlaces();
}
