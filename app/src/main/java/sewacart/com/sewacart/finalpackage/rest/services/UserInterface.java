package sewacart.com.sewacart.finalpackage.rest.services;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import sewacart.com.sewacart.finalpackage.activity.CustomerDashboard;
import sewacart.com.sewacart.finalpackage.model.AddReviewModel;
import sewacart.com.sewacart.finalpackage.model.AddToCartModel;
import sewacart.com.sewacart.finalpackage.model.CategoryModel;
import sewacart.com.sewacart.finalpackage.model.CustomerDashboardModel;
import sewacart.com.sewacart.finalpackage.model.DashboardModel;
import sewacart.com.sewacart.finalpackage.model.HomeModel;
import sewacart.com.sewacart.finalpackage.model.OrderModel;
import sewacart.com.sewacart.finalpackage.model.ParentModel;
import sewacart.com.sewacart.finalpackage.model.ProviderModel;
import sewacart.com.sewacart.finalpackage.model.ReviewModel;
import sewacart.com.sewacart.finalpackage.model.UserModel;
import sewacart.com.sewacart.finalpackage.model.ViewCartModel;

public interface UserInterface {

    //POST METHODS
    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> signInUser(@Field("action") String action, @Field("username") String username, @Field("user_password") String user_password);

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> registerUser(@Field("action") String action, @Field("username") String username, @Field("email_address") String email_address, @Field("confirm_pass") String confirm_pass);

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> updateProfile(@Field("action") String action, @Field("user_id") String user_id, @Field("display_name") String display_name, @Field("email_address") String email_address, @Field("phone") String phone, @Field("mobile") String mobile, @Field("state") String state, @Field("city") String city, @Field("zip") String zip, @Field("street") String street, @Field("extra_address") String extra_address, @Field("description") String description, @Field("imageonly") String imageonly);

    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> updateImage(@Body RequestBody file);

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<AddReviewModel> addReview(@Field("action") String action, @Field("title") String title, @Field("content") String content, @Field("author") String author, @Field("testimonial_written_by") String testimonial_written_by, @Field("rating") String rating, @Field("designation") String designation, @Field("service") String service);

    /*  @FormUrlEncoded
      @POST("wp-admin/admin-ajax.php")
      Call<AddReviewModel> addService(@Field("action") String action, @Field("title") String title, @Field("content") String content, @Field("author") String author, @Field("service-category[]") List<String> service_category, @Field("sub_cat[]") List<String> sub_cat, @Field("region[]") List<String> region, @Field("city[]") List<String> city, @Field("full_address_secondary") String full_address_secondary, @Field("email") String email, @Field("phone") String phone, @Field("mobile_1") String mobile_1, @Field("mobile_2") String mobile_2, @Field("organization") String organization, @Field("service_cost") String service_cost, @Field("cost_interval") String cost_interval, @Field("min_service_hour") String min_service_hour, @Field("experience") String experience, @Field("google_map_script") String google_map_script, @Field("show_in_homepage") String show_in_homepage, @Field("featuredimage") String featuredimage);
  */

    @POST("wp-admin/admin-ajax.php")
    Call<Object> addService(@Body RequestBody file);

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<AddToCartModel> addToCart(@Field("action") String action, @Field("product_id") String product_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<AddToCartModel> removeFromCart(@Field("action") String action, @Field("product_id") String product_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<AddToCartModel> orderNow(@Field("action") String action, @Field("user_id") String user_id, @Field("deliver_address") String deliver_address, @Field("deliver_full_name") String deliver_full_name, @Field("deliver_contact") String deliver_contact, @Field("deliver_email") String deliver_email, @Field("deliver_information") String deliver_information);

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<AddToCartModel> purchasePoint(@Field("action") String action, @Field("user_id") String user_id, @Field("purchase_point") String purchase_point, @Field("payment_option") String payment_option);


    // GET METHODS
    @GET("wp-json/api/services")
    Call<List<ProviderModel>> getProviders(@QueryMap Map<String, String> params);

    @GET("wp-json/api/servicedetail")
    Call<ProviderModel> getProvider(@QueryMap Map<String, String> params);

    @GET("wp-json/api/servicesByHomePageCategory")
    Call<HomeModel> loadAllData();

    @GET("wp-json/api/testimonials")
    Call<List<ReviewModel>> getReviews(@QueryMap Map<String, String> params);

    @GET("wp-json/api/userprofile")
    Call<UserModel> getUserDetailsById(@QueryMap Map<String, String> params);

    @GET("wp-json/api/parentCategoryRegion")
    Call<ParentModel> getParents();

    @GET("wp-json/api/getChildRegion")
    Call<List<ParentModel.Region>> getChildRegion(@QueryMap Map<String, String> params);

    @GET("wp-json/api/getChildCategory")
    Call<List<ParentModel.Category>> getChildCategory(@QueryMap Map<String, String> params);

    @GET("wp-json/api/serviceProviderDashboard")
    Call<DashboardModel> getDashboard(@QueryMap Map<String, String> params);

    @GET("wp-json/api/customerDashboard")
    Call<List<CustomerDashboardModel>> getCustomerDashboard(@QueryMap Map<String, String> params);

    @GET("wp-json/api/viewcart")
    Call<ViewCartModel> getViewCart(@QueryMap Map<String, String> params);

    @GET("wp-json/api/userOrdersStatus")
    Call<List<OrderModel>> getOrderlist(@QueryMap Map<String, String> params);

    @GET("wp-json/api/parentChildCategories")
    Call<List<CategoryModel>> getCategories(@QueryMap Map<String, String> params);


}
