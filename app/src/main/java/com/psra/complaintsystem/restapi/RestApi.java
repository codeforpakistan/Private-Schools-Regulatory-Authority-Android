package com.psra.complaintsystem.restapi;

import com.psra.complaintsystem.SqliteDB.DistList;
import com.psra.complaintsystem.SqliteDB.SchoolLIst;
import com.psra.complaintsystem.SqliteDB.TecList;
import com.psra.complaintsystem.SqliteDB.UcList;
import com.psra.complaintsystem.modle.ComplainModle;
import com.psra.complaintsystem.modle.DemoLogin;
import com.psra.complaintsystem.modle.FeedbackModule;
import com.psra.complaintsystem.modle.ForgetPassword;
import com.psra.complaintsystem.modle.GetComplaintDatum;
import com.psra.complaintsystem.modle.Registered;
import com.psra.complaintsystem.modle.UpdataDemo;
import com.psra.complaintsystem.modle.VerificationClas;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by HP on 7/12/2018.
 */

public interface RestApi {

    @FormUrlEncoded
    @POST("login")
    Call<DemoLogin> testLogin(@FieldMap Map<String, String> map);
  ////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("registration")
    Call<List<Registered>> doRegistration(@FieldMap Map<String, String> map);
/////////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("userUpdate")
    Call<List<UpdataDemo>> doUpdata(@FieldMap Map<String, String> map);
/////////////////////////////////////////////////////////////////////////////////////////
    @Multipart
    @POST("file_complain")
 Call<List<ComplainModle>> uploadFile(@Part List<MultipartBody.Part> files,@PartMap Map<String,RequestBody> map);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("get_complain_list/userId")
    Call<List<GetComplaintDatum>>  getAllComplaints(@FieldMap Map<String, String> map);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //http://pakwebdeveloper.net/psra/mobile_apis/get_data_require_for_complain/
    //district_id
    //tehsil_id
    //uc_id

    @POST("get_data_require_for_complain")
    Call<DistList>  getAllDistrict();

    @FormUrlEncoded
    @POST("get_data_require_for_complain")
    Call<TecList>  getAllTihasle(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("get_data_require_for_complain")
    Call<UcList>  getAllUc(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("get_data_require_for_complain")
    Call<SchoolLIst>  getAllSchool(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<List<ForgetPassword>>  getPassword(@FieldMap Map<String, String> map);
    //////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("varify_user_account")
    Call<List<VerificationClas>>  verifieCode(@FieldMap Map<String, String> map);
////////////////////////////////////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST("add_feedback_of_complains")
    Call<List<FeedbackModule>>  submitFeedback(@FieldMap Map<String, String> map);
}
