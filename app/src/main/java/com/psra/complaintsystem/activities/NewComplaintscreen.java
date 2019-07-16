package com.psra.complaintsystem.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.psra.complaintsystem.R;
import com.psra.complaintsystem.SqliteDB.ComplainTypesList;
import com.psra.complaintsystem.SqliteDB.DatabaseRooom;
import com.psra.complaintsystem.SqliteDB.DistList;
import com.psra.complaintsystem.SqliteDB.Districtlist;
import com.psra.complaintsystem.SqliteDB.SchoolLIst;
import com.psra.complaintsystem.SqliteDB.Schoolslist;
import com.psra.complaintsystem.SqliteDB.TecList;
import com.psra.complaintsystem.SqliteDB.TehsilsList;
import com.psra.complaintsystem.SqliteDB.UcList;
import com.psra.complaintsystem.SqliteDB.UcList_;
import com.psra.complaintsystem.Utils.AppConfig;
import com.psra.complaintsystem.Utils.ProgressGenerator;
import com.psra.complaintsystem.modle.ComplainModle;
import com.psra.complaintsystem.restapi.CallsRest;
import com.psra.complaintsystem.restapi.RestApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class NewComplaintscreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks ,ProgressGenerator.OnCompleteListener {

    private static final int REQUEST_READ_EXTERNAL = 1;
    private Spinner spinnerAgainst, spinnerCat, spinnerDist;
    private static final int CAMERA = 121;
    private static final int GALLERY = 111;
    Snackbar snackbar;
    private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/";
    AlertDialog alertDialog;
    ActionProcessButton bt_submitbt;
    ProgressGenerator progressGenerator;
    List<Schoolslist> schoolslists;
    File galleryFile, cameraFile;
    private static final String[] dist = {"Peshawar", "Bunnu", "nowshera"};
    private static final String[] cat = {"No fee concession during vacation", "No Sibling Fee Concession", "More than 3% increse in tuition fee", "Annual Promotion Fee charged", "Corporal Punishment", "Teaching/Non-Teaching Staff Salary", "other"};
    private static final String[] compagainst = {"School", "College", "Academy"};
    Uri cameraUri = null, galleryUri = null;
    Uri imageUri = null;
    private Bitmap bitmap;

    ImageView imageView;
    EditText schoolAddress, complaintDetail,tv_school_address;
    List<MultipartBody.Part> fileParts = new ArrayList<>();
    String s;
    int dist_id, cat_id, complian_typeid;
    String userId;
    SharedPreferences sharedPreferences;
    int success;
    Uri uri=null,uri2=null;
    private SharedPreferences loginSharedPreferences;
    SharedPreferences.Editor loginEditor;
    Snackbar mSnackbar;
    private Toolbar mTopToolbar;
    Schoolslist school_data;
    List<TehsilsList> tehsilsLists;
    private static final String DATABASE_NAME = "psra_db";
    private static final String TAG = "Main";
    private DatabaseRooom mDatabase;
    private EditText complaint_spinner, sub_complaint_spinner, uc_complaint_spinner, school_complaint_spinner, types_complaint_spinner;
    List<Districtlist> mAllCategories;
    List<Districtlist> distlists;
    AllDistCatAdapter allCatAdapter;
    SubCatAdapter mSubCatAdapter;
    UcCatAdapter mucCatAdapter;
    SchoolCatAdapter schoolCatAdapter;
    String distcatId,detailData,schooladdrssData;
    String distcatName, tehsubCatId, subCatName, ucCatId, ucCatName, schoolCatName, schoolCatId, typeCatName, typeCatId,school_address_data;
    HashMap<String, String> map_map = new HashMap<>();
    List<UcList_> ucList_Lists;
    CallsRest callsRest;
    List<ComplainTypesList> compltLists;
    TypeCatAdpater typeCatAdpater;
    ProgressDialog dialog;
   // Button bt_submitbt;
   LinearLayout linerLayout;

   EditText et_complaint_other;
   String et_complaint_other_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_new_complaintscreen);
        linerLayout=findViewById(R.id.base_layout);
        tv_school_address=findViewById(R.id.et_school_address);
        sharedPreferences = getApplication().getSharedPreferences("loginData", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "no data");
        school_data=new Schoolslist();
        school_data.setSchoolName("Other");
      /*  spinnerAgainst = findViewById(R.id.spinneragainst);
        //spinnerCat = findViewById(R.id.spinnerCagt);
        spinnerDist = findViewById(R.id.spinnerdistrict);*/
        imageView = findViewById(R.id.imageView5);
       // bt_submitbt=findViewById(R.id.submit);
        bt_submitbt = (ActionProcessButton) findViewById(R.id.submit);
        progressGenerator = new ProgressGenerator(this);


        schoolAddress = findViewById(R.id.et_institute_address);
        schoolAddress.setFocusableInTouchMode(false);
        schoolAddress.setFocusable(false);
        schoolAddress.setFocusableInTouchMode(true);
        schoolAddress.setFocusable(true);

        et_complaint_other = findViewById(R.id.et_complaint_other);

        complaintDetail = findViewById(R.id.et_complaint_detail);
        complaintDetail.setFocusableInTouchMode(false);
        complaintDetail.setFocusable(false);
        complaintDetail.setFocusableInTouchMode(true);
        complaintDetail.setFocusable(true);

        callsRest = new CallsRest();
        mTopToolbar = findViewById(R.id.my_toolbar);
        mTopToolbar.setTitle("Register Complaint");
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTopToolbar.setNavigationOnClickListener(arrow -> onBackPressed());


/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, compagainst);
        ArrayAdapter<String> adapterCat = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cat);

        ArrayAdapter<String> adaptedist = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dist);*/

      /*  spinnerAgainst.setAdapter(adapter);
        //spinnerCat.setAdapter(adapterCat);
        spinnerDist.setAdapter(adaptedist);*/
        // loadAllCategories();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Fetching data...");
        setUI();
        loadAllComTypeListDistlist();


     /*   spinnerDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                s = dist[position];
                Log.e("value", "onItemSelected: " + s);
                dist_id = position;

                // Toast.makeText(NewComplaintscreen.this, ""+dist_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/

       /* spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                cat_id = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/

   /*     spinnerAgainst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                complian_typeid = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri2 != null) {
                    Log.e("click", "working gallery: ");
                    showImagePreview(uri2);

                } else if (uri != null) {
                    Log.e("click", "working camera@: ");
                    showImagePreview(uri);
                } else {
                    //do noting
                }
            }
        });
    }


    private void showImagePreview(final Uri urifile) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog previewDilaog = new Dialog(NewComplaintscreen.this, com.psra.complaintsystem.R.style.dialog_theme);
                previewDilaog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                previewDilaog.setCancelable(true);
                previewDilaog.setContentView(com.psra.complaintsystem.R.layout.image_preview_dialog);
                previewDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(0x7f000000));
                ImageView preview_image = previewDilaog.findViewById(com.psra.complaintsystem.R.id.preview_image);
                //Bitmap bmp = getDownsampledBitmap(uri, 0);
                Bitmap bitmap = null;
                bitmap = getDownsampledBitmap(urifile, 4);
                // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                preview_image.setImageBitmap(bitmap);
                previewDilaog.show();
            }
        });

    }

    // method to access Gallary of mobile
    public void choosePhotoFromGallary() {
        openGallery();

    }

    // create file
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir1      /* directory */
        );

        return image;
    }

    // method when select option from dialog box
    private void takePhotoFromCamera() {
        openCamera();
    }

    // run time permission for Camera access
    @AfterPermissionGranted(CAMERA)
    private void openCamera() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
            Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                cameraFile = null;
                try {
                    cameraFile = createImageFile();
                } catch (IOException e) {
                    Log.e("error", "IOException: " + e);
                }
                // Continue only if the File was successfully created
                if (cameraFile != null) {
                    cameraUri = FileProvider.getUriForFile(this, "com.psra.complaintsystem.fileprovider", cameraFile);
                    // by this point we have the camera photo on disk
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                    startActivityForResult(takePictureIntent, CAMERA);
                }
            }

        } else {
            EasyPermissions.requestPermissions(this, "We need permissions because Application will not work with out access",
                    CAMERA, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       /* switch (requestCode) {
            case GALLERY: {

                showPictureDialog();

            }
            break;


            case CAMERA: {

                showPictureDialog();

            }
            break;


        }*/
    }

////////////////////////////////////working here ////////////////////////////////////////////
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            Bitmap bit;
            if (data != null) {

                imageUri = data.getData();
                Log.e("imageUri",imageUri.toString());
                final InputStream imageStream;
                try {
                    bit = getDownsampledBitmap(imageUri, 4);
                    File f = createImageFile();
                    uri2 = FileProvider.getUriForFile(this, "com.psra.complaintsystem.fileprovider", f);
                  /*  final File original_file = FileUtils.getFile(NewComplaintscreen.this, imageUri);
                    Log.e( "original_file",bitmap.toString() );*/
                    f.createNewFile();

//Convert bitmap to byte array
                    Bitmap bitmap = bit;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    imageView.setImageBitmap(bit);
                    fileParts.clear();
                   // fileParts.add(prepareFilePart("files[]", original_file, imageUri));
                    fileParts.add(prepareFilePart("files[]", f, uri2));
                  /*  imageStream = getContentResolver().openInputStream(imageUri);
                    Log.e("imageStream: ",imageStream.toString() );
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);*/

                    //imageView.setImageBitmap(bitmap);
                    //imageView.setImageBitmap(selectedImage);
                    //imageView.setImageURI(FileUtils.getUri(original_file));
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Failed to get image!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {


            if (cameraUri != null) {
                //yaha per array clear ho ge?
                //ha is if tatement mai kare lakin file part.add sy pehle
                //fileParts.clear();
                galleryUri = null;
                uri2= null;
                Log.e("cameraUri",cameraUri.toString());
                Bitmap bmp;
                try {
                    bmp = getDownsampledBitmap(cameraUri, 4);
                    File f = createImageFile();
                    uri = FileProvider.getUriForFile(this, "com.psra.complaintsystem.fileprovider", f);
                    f.createNewFile();


//Convert bitmap to byte array
                    Bitmap bitmap = bmp;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    Log.e("error", "onActivityResult: " + cameraUri);

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    imageView.setImageBitmap(bmp);
//clear list of files             //yaha per toh nai krne na??
                    //ha idar bhi kar sakty hai
                    //idar hi kar ly upar sy hata dy
                    //dialog box image select krne ke bd be show ho rha hota hn jab tk back button
                    //w8
                    Log.e(" uri",uri.toString() );
                    fileParts.clear();
                    fileParts.add(prepareFilePart("files[]", f, uri));
                    Log.e("New Complaint Camera", fileParts.toString());


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }


    }

    // bitmap method
    private Bitmap getDownsampledBitmap(Uri fileUri, int sampleSize) {

        Bitmap bitmap = null;
        try {
            BitmapFactory.Options outDimens = getBitmapDimensions(fileUri);


            bitmap = downsampleBitmap(fileUri, sampleSize);

        } catch (Exception e) {
            //handle the exception(s)
        }

        return bitmap;
    }

    private BitmapFactory.Options getBitmapDimensions(Uri uri) throws FileNotFoundException, IOException {
        BitmapFactory.Options outDimens = new BitmapFactory.Options();
        outDimens.inJustDecodeBounds = true; // the decoder will return null (no bitmap)

        InputStream is = getContentResolver().openInputStream(uri);
        // if Options requested only the size will be returned
        BitmapFactory.decodeStream(is, null, outDimens);
        is.close();

        return outDimens;
    }

    private Bitmap downsampleBitmap(Uri uri, int sampleSize) throws FileNotFoundException, IOException {
        Bitmap resizedBitmap;
        BitmapFactory.Options outBitmap = new BitmapFactory.Options();
        outBitmap.inJustDecodeBounds = false; // the decoder will return a bitmap
        outBitmap.inSampleSize = sampleSize;

        InputStream is = getContentResolver().openInputStream(uri);
        resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
        is.close();

        return resizedBitmap;
    }

    // pick button method when click on it shows dialog box to select mediam for to pick imagge from gallery of camera
    public void pickImage(View view) {

        showPictureDialog();


    }

    public void submit(View view) {
        schooladdrssData=schoolAddress.getText().toString();
         detailData=complaintDetail.getText().toString();

         school_address_data=tv_school_address.getText().toString();
        et_complaint_other_str = et_complaint_other.getText().toString();
        if (TextUtils.isEmpty(distcatId)) {
            complaint_spinner.setError("Select District");
            complaint_spinner.requestFocus();
        } else if (TextUtils.isEmpty(tehsubCatId)) {

            sub_complaint_spinner.setError("Select Tehsil");
            sub_complaint_spinner.requestFocus();
        }

        else if (TextUtils.isEmpty(et_complaint_other_str))
        {

            et_complaint_other.setError("Enter Complaint Type");
            et_complaint_other.requestFocus();
        }
        /*   else if (TextUtils.isEmpty(schoolCatId))
        {

            school_complaint_spinner.setError("Select School");
            school_complaint_spinner.requestFocus();
        }*/

       /* else if(TextUtils.isEmpty(schooladdrssData))
        {
            schoolAddress.setError("Enter School Name");
            schoolAddress.requestFocus();

        }*/
        else if(TextUtils.isEmpty(detailData))
        {
            complaintDetail.setError("Enter Detail");
            complaintDetail.requestFocus();

        }
      /*  else if(tv_school_address.getVisibility() == View.VISIBLE)
        {

            if(TextUtils.isEmpty(school_address_data))
            {
                tv_school_address.setError("Enter School Name");
                tv_school_address.requestFocus();

            }

        }*/

        else {
            if (isNetworkAvailable()){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressGenerator.start(bt_submitbt);
                        bt_submitbt.setEnabled(false);
                        uploadFile();


                    }
                });
            }else {
                snackbar = Snackbar
                        .make(linerLayout, "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }


    }

    private void uploadFile() {

        Map<String, RequestBody> map = new HashMap<>();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();



        RequestBody complaintdetail;
        if (!TextUtils.isEmpty(et_complaint_other_str)){
            complaintdetail = RequestBody.create(MediaType.parse("text/plain"), et_complaint_other_str+": "+detailData);
        }else {
            complaintdetail = RequestBody.create(MediaType.parse("text/plain"), detailData);

        }
        RequestBody complain_type_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(typeCatId));
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody school_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(schoolCatId));
        RequestBody district_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(distcatId));
        RequestBody schooladdress = RequestBody.create(MediaType.parse("text/plain"), schooladdrssData);
       // RequestBody complaintdetail = RequestBody.create(MediaType.parse("text/plain"), detailData);
        RequestBody complaintschooldata = RequestBody.create(MediaType.parse("text/plain"), school_address_data);




        //Log.e("check", "uploadFile: " + schoolCatId + " " + userId + " " + typeCatId + " " + distcatId + " " + schoolAddress.getText().toString() + "" + complaintDetail.getText().toString());

        map.put("complain_type_id", complain_type_id);
        map.put("user_id", user_id);
        map.put("school_id", school_id);
        map.put("district_id", district_id);
        map.put("schoolAddress", schooladdress);
        map.put("complainDetail", complaintdetail);
        map.put("schoolOthers", complaintschooldata);

        Log.e("response", "uploadFile: " + map.toString());
        Log.e("schooladdrssData", schooladdrssData);

        Log.e("check", "uploadFile: " + complain_type_id + " " + user_id + " " + district_id + " " + school_id + " " + complaintdetail);

        RestApi retroApiClient = retrofit.create(RestApi.class);
        Call<List<ComplainModle>> fileUpload = retroApiClient.uploadFile(fileParts, map);
        fileUpload.enqueue(new Callback<List<ComplainModle>>() {
            @Override
            public void onResponse(Call<List<ComplainModle>> call, Response<List<ComplainModle>> response) {

                Log.e("response", "onResponse: " + response.message());
                Log.e("dataresponse", response.body().toString());
                Log.e("onResponse: ",""+success );
                Log.e("onResponse: ",""+response.body().get(0).getMessage() );

                if (response.isSuccessful()) {
                    if (success == 0) {
                        snackbar = Snackbar
                                .make(linerLayout, "Kindly fill the complain form carefully", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                   else if (success == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(NewComplaintscreen.this)
                                        .setTitle("Success")
                                        .setMessage("Complaint Registered")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();                                            }

                                        })
                                        .show();
                                // submitbt.setEnabled(false);

                            }
                        });

                    }
                    else if (success == 2) {
                        snackbar = Snackbar
                                .make(linerLayout, "Server error, please try later", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }



                }
            }

            @Override
            public void onFailure(Call<List<ComplainModle>> call, Throwable t) {
                Log.e("fail", t.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        snackbar = Snackbar
                                .make(linerLayout, "Unable to register complaint", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });


            }
        });


    }


    // Dialog box for selection of image from Gallary or camera
    private void showPictureDialog() {

        Log.e("click", "showPictureDialog: ");
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(com.psra.complaintsystem.R.layout.custom, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file, Uri uri) {
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(uri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }


    // run time permission method for access gallery
    @AfterPermissionGranted(GALLERY)
    private void openGallery() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //Toast.makeText(this, "Opening Gallery", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();

         /*   Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);*/
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY);


        } else {
            EasyPermissions.requestPermissions(this, "We need permissions because Application will not work with out this access",
                    GALLERY, perms);
        }

    }

    public void galrydialogdosomthing(View view) {
        openGallery();
        //alertDialog.dismiss();
    }

    public void cameradialogdosomthing(View view) {
        openCamera();
        //alertDialog.dismiss();
    }


    public void cancleDialog(View view) {
        alertDialog.dismiss();
    }


    private void setUI() {
        complaint_spinner = findViewById(R.id.complaint_spinner);
        sub_complaint_spinner = findViewById(R.id.sub_complaint_spinner);
        uc_complaint_spinner = findViewById(R.id.uc_complaint_spinner);
        school_complaint_spinner = findViewById(R.id.school_complaint_spinner);
        types_complaint_spinner = findViewById(R.id.types_complaint_spinner);

        complaint_spinner.setEnabled(false);

        complaint_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAllCategoriesDialog();
            }
        });
       sub_complaint_spinner.setEnabled(false);
        sub_complaint_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSubCategoriesDialog();

            }
        });

        uc_complaint_spinner.setEnabled(false);
        uc_complaint_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUCSubCategoriesDialog();
            }
        });

         school_complaint_spinner.setEnabled(false);
        school_complaint_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSchoolSubCategoriesDialog();
            }
        });
         types_complaint_spinner.setEnabled(false);
        types_complaint_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTypeCategoriesDialog();
            }
        });
    }

    private void loadAllCategories() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mAllCategories = (List<Districtlist>) mDatabase.daoAccess().fetchDistrictlist();
                    Log.e(TAG, "list size: " + mAllCategories.size());
                    Log.e(TAG, "all dist cat: " + mAllCategories.toString());
                } catch (Exception e) {
                    Log.e(TAG, "exp: " + e.toString());
                }
            }
        }).start();

    }

    private void showAllCategoriesDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog allCategoriesDialog = new Dialog(NewComplaintscreen.this, com.psra.complaintsystem.R.style.dialog_theme);
                allCategoriesDialog.setCancelable(true);
                allCategoriesDialog.setContentView(com.psra.complaintsystem.R.layout.spinner_dialog);
                TextView dialog_title = allCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.dialog_title);
                //add dynamic title to dialog
                dialog_title.setText("Select District");
                SearchView mSearchView = allCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.search_et);
                mSearchView.setVisibility(View.VISIBLE);
                final ListView list = allCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.seize_cat_list);
                allCatAdapter = new AllDistCatAdapter(NewComplaintscreen.this, distlists);
                list.setAdapter(allCatAdapter);
                allCatAdapter.notifyDataSetChanged();
                list.setTextFilterEnabled(false);
                mSearchView.setIconifiedByDefault(false);
                mSearchView.setSubmitButtonEnabled(false);
                mSearchView.setQueryHint("Search...");
                allCategoriesDialog.show();
                //add text watcher on search edit text
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                            loadAllCategories();
                            allCatAdapter = new AllDistCatAdapter(NewComplaintscreen.this, distlists);
                            list.setAdapter(allCatAdapter);
                            allCatAdapter.notifyDataSetChanged();

                        } else {
                            Filter filter = allCatAdapter.getFilter();
                            filter.filter(newText);
                        }
                        return true;
                    }
                });

                //list view item click listener
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        allCategoriesDialog.dismiss();
                        List<Districtlist> arrayList = new ArrayList<>();
                        arrayList = allCatAdapter.getList();
                        complaint_spinner.setText("" + arrayList.get(i).getDistrictTitle());
                        distcatId = arrayList.get(i).getDistrictId();
                        distcatName = arrayList.get(i).getDistrictTitle();

                        if (!distcatId.equals("")) {
                            //create part and put in hasmap
                            loadAllListTahsical(distcatId);
                            sub_complaint_spinner.setText("");
                            //mMap.put("vechicle_make", createPartFromString(catId));
                            Log.e("catId", distcatId);
                            Log.e("catName", distcatName);
                        } else {

                        }


                    }
                });


            }
        });
    }

    private void showSubCategoriesDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog subCategoriesDialog = new Dialog(NewComplaintscreen.this, com.psra.complaintsystem.R.style.dialog_theme);
                subCategoriesDialog.setCancelable(true);
                subCategoriesDialog.setContentView(com.psra.complaintsystem.R.layout.spinner_dialog);
                TextView dialog_title = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.dialog_title);
                //add dynamic title to dialog
                dialog_title.setText("Select Tehsil");
                SearchView mSearchView = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.search_et);
                mSearchView.setVisibility(View.VISIBLE);
                final ListView list = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.seize_cat_list);
                mSubCatAdapter = new SubCatAdapter(NewComplaintscreen.this, tehsilsLists);
                list.setAdapter(mSubCatAdapter);
                mSubCatAdapter.notifyDataSetChanged();
                list.setTextFilterEnabled(false);
                mSearchView.setIconifiedByDefault(false);
                mSearchView.setSubmitButtonEnabled(false);
                mSearchView.setQueryHint("Search...");
                subCategoriesDialog.show();
                //add text watcher on search edit text
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                            loadAllCategories();
                            mSubCatAdapter = new SubCatAdapter(NewComplaintscreen.this, tehsilsLists);
                            list.setAdapter(mSubCatAdapter);
                            mSubCatAdapter.notifyDataSetChanged();

                        } else {
                            Filter filter = mSubCatAdapter.getFilter();
                            filter.filter(newText);
                        }
                        return true;
                    }
                });

                //list view item click listener
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        subCategoriesDialog.dismiss();
                        List<TehsilsList> arrayList = new ArrayList<>();
                        arrayList = mSubCatAdapter.getList();
                        sub_complaint_spinner.setText("" + arrayList.get(i).getTehsilTitle());
                        tehsubCatId = arrayList.get(i).getTehsilId();
                        subCatName = arrayList.get(i).getTehsilTitle();
///////////////////////////////////////////////////changesssssssssssssssssss////////////////////////////////////////////////////////////////////
                        if (!tehsubCatId.equals("")) {
                            //create part and put in hasmap
                            loadAllListUc(tehsubCatId);
                            uc_complaint_spinner.setText("");
                            //mMap.put("vechicle_make", createPartFromString(subCatId));
                            Log.e("TahCatId", tehsubCatId);
                            Log.e("TahCatName", subCatName);
                        } else {

                        }

                    }
                });


            }
        });
    }

    private void showUCSubCategoriesDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog subCategoriesDialog = new Dialog(NewComplaintscreen.this, com.psra.complaintsystem.R.style.dialog_theme);
                subCategoriesDialog.setCancelable(true);
                subCategoriesDialog.setContentView(com.psra.complaintsystem.R.layout.spinner_dialog);
                TextView dialog_title = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.dialog_title);
                //add dynamic title to dialog
                dialog_title.setText("Select Union Council");
                SearchView mSearchView = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.search_et);
                mSearchView.setVisibility(View.VISIBLE);
                final ListView list = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.seize_cat_list);
                mucCatAdapter = new UcCatAdapter(NewComplaintscreen.this, ucList_Lists);
                list.setAdapter(mucCatAdapter);
                mucCatAdapter.notifyDataSetChanged();
                list.setTextFilterEnabled(false);
                mSearchView.setIconifiedByDefault(false);
                mSearchView.setSubmitButtonEnabled(false);
                mSearchView.setQueryHint("Search...");
                subCategoriesDialog.show();
                //add text watcher on search edit text
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                            loadAllCategories();
                            mucCatAdapter = new UcCatAdapter(NewComplaintscreen.this, ucList_Lists);
                            list.setAdapter(mucCatAdapter);
                            mucCatAdapter.notifyDataSetChanged();

                        } else {
                            Filter filter = mucCatAdapter.getFilter();
                            filter.filter(newText);
                        }
                        return true;
                    }
                });

                //list view item click listener
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        subCategoriesDialog.dismiss();
                        List<UcList_> arrayList = new ArrayList<>();
                        arrayList = mucCatAdapter.getList();
                        uc_complaint_spinner.setText("" + arrayList.get(i).getUcTitle());
                        ucCatId = arrayList.get(i).getUcId();
                        ucCatName = arrayList.get(i).getUcTitle();

                        if (!ucCatId.equals("")) {
                            //create part and put in hasmap
                            loadAllSchools(ucCatId);
                            school_complaint_spinner.setText("");
                            //mMap.put("vechicle_make", createPartFromString(subCatId));
                            Log.e("ucCatId", ucCatId);
                            Log.e("ucCatName", ucCatName);
                        } else {

                        }

                    }
                });


            }
        });
    }

    private void showSchoolSubCategoriesDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog subCategoriesDialog = new Dialog(NewComplaintscreen.this, com.psra.complaintsystem.R.style.dialog_theme);
                subCategoriesDialog.setCancelable(true);
                subCategoriesDialog.setContentView(com.psra.complaintsystem.R.layout.spinner_dialog);
                TextView dialog_title = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.dialog_title);
                //add dynamic title to dialog
                dialog_title.setText("Select School");
                SearchView mSearchView = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.search_et);
                mSearchView.setVisibility(View.VISIBLE);
                final ListView list = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.seize_cat_list);
                schoolCatAdapter = new SchoolCatAdapter(NewComplaintscreen.this, schoolslists);
                list.setAdapter(schoolCatAdapter);
                schoolCatAdapter.notifyDataSetChanged();
                list.setTextFilterEnabled(false);
                mSearchView.setIconifiedByDefault(false);
                mSearchView.setSubmitButtonEnabled(false);
                mSearchView.setQueryHint("Search...");
                subCategoriesDialog.show();
                //add text watcher on search edit text
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                            loadAllCategories();
                            schoolCatAdapter = new SchoolCatAdapter(NewComplaintscreen.this, schoolslists);
                            list.setAdapter(schoolCatAdapter);
                            schoolCatAdapter.notifyDataSetChanged();

                        } else {
                            Filter filter = schoolCatAdapter.getFilter();
                            filter.filter(newText);
                        }
                        return true;
                    }
                });

                //list view item click listener
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        subCategoriesDialog.dismiss();
                        List<Schoolslist> arrayList = new ArrayList<>();
                        arrayList = schoolCatAdapter.getList();
                        school_complaint_spinner.setText("" + arrayList.get(i).getSchoolName());
                        schoolCatId = arrayList.get(i).getSchoolId();
                        schoolCatName = arrayList.get(i).getSchoolName();

                      /*  if (!schoolCatName.equals("")) {
                            //create part and put in hasmap
                            // sub_complaint_spinner.setText("");
                            //mMap.put("vechicle_make", createPartFromString(subCatId));
                            //Log.e("schoolCatId", schoolCatId);
                            Log.e("schoolCatName", schoolCatName);
                        } else*/ if(schoolCatName.equalsIgnoreCase("Other")) {
                           // Toast.makeText(NewComplaintscreen.this, "click", Toast.LENGTH_SHORT).show();
                            Log.e("testing", schoolCatName);
                            tv_school_address.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            tv_school_address.setVisibility(View.GONE);
                        }

                    }
                });


            }
        });
    }


    private void showTypeCategoriesDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog subCategoriesDialog = new Dialog(NewComplaintscreen.this, com.psra.complaintsystem.R.style.dialog_theme);
                subCategoriesDialog.setCancelable(true);
                subCategoriesDialog.setContentView(com.psra.complaintsystem.R.layout.spinner_dialog);
                TextView dialog_title = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.dialog_title);
                //add dynamic title to dialog
                dialog_title.setText("Select Type of Complaint");
                SearchView mSearchView = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.search_et);
                mSearchView.setVisibility(View.VISIBLE);
                final ListView list = subCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.seize_cat_list);
                typeCatAdpater = new TypeCatAdpater(NewComplaintscreen.this, compltLists);
                list.setAdapter(typeCatAdpater);
                typeCatAdpater.notifyDataSetChanged();
                list.setTextFilterEnabled(false);
                mSearchView.setIconifiedByDefault(false);
                mSearchView.setSubmitButtonEnabled(false);
                mSearchView.setQueryHint("Search...");
                subCategoriesDialog.show();
                //add text watcher on search edit text
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                            loadAllCategories();
                            typeCatAdpater = new TypeCatAdpater(NewComplaintscreen.this, compltLists);
                            list.setAdapter(typeCatAdpater);
                            typeCatAdpater.notifyDataSetChanged();

                        } else {
                            Filter filter = typeCatAdpater.getFilter();
                            filter.filter(newText);
                        }
                        return true;
                    }
                });

                //list view item click listener
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        subCategoriesDialog.dismiss();
                        List<ComplainTypesList> arrayList = new ArrayList<>();
                        arrayList = typeCatAdpater.getList();
                        types_complaint_spinner.setText("" + arrayList.get(i).getComplainTypeTitle());
                        typeCatId = arrayList.get(i).getComplainTypeId();
                        typeCatName = arrayList.get(i).getComplainTypeTitle();

                        if(typeCatName.equalsIgnoreCase("Other")) {
                            // Toast.makeText(NewComplaintscreen.this, "click", Toast.LENGTH_SHORT).show();
                            et_complaint_other.setVisibility(View.VISIBLE);
                            et_complaint_other.requestFocus();

                        }
                        else
                        {
                            et_complaint_other.setVisibility(View.GONE);
                        }

                    }
                });


            }
        });
    }

    public void loadAllComTypeListDistlist() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        RestApi rest = retrofit.create(RestApi.class);
        Call<DistList> call = rest.getAllDistrict();
        call.enqueue(new Callback<DistList>() {
            @Override
            public void onResponse(Call<DistList> call, Response<DistList> response) {
                Log.e("AllLIST", "onResponse: " + response.body());
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {
                    //if (success == 1) {
                    DistList tecList = response.body();
                    compltLists = tecList.getComplainTypesList();
                    distlists = tecList.getDistrictlist();
                    Log.e("ALLLIST", "onResponse: " + compltLists + "          " + distlists);
                    complaint_spinner.setEnabled(true);
                    types_complaint_spinner.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<DistList> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadAllListTahsical(final String ids) {

        HashMap<String, String> map = new HashMap<>();
        map.put("district_id", ids);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        dialog.show();
        RestApi rest = retrofit.create(RestApi.class);
        Call<TecList> call = rest.getAllTihasle(map);
        call.enqueue(new Callback<TecList>() {
            @Override
            public void onResponse(Call<TecList> call, Response<TecList> response) {
                Log.e("AllLIST", "onResponse: " + response.body());

                success = response.body().getSuccess();
                Log.e("ALLLIST", "onResponse: " + success);
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {

                        TecList tecList = response.body();
                        tehsilsLists = tecList.getTehsilsList();
                        List<Schoolslist> schoolslists = tecList.getSchoolslist();


                        Log.e("ALLLIST", "onResponse: " + tehsilsLists + "          " + schoolslists);
                        sub_complaint_spinner.setEnabled(true);
                        // Toast.makeText(HomeScreen.this, "data get", Toast.LENGTH_SHORT).show();


                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }


                }
            }

            @Override
            public void onFailure(Call<TecList> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadAllListUc(final String ids) {

        map_map.put("tehsil_id", ids);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        dialog.show();
        RestApi rest = retrofit.create(RestApi.class);
        Call<UcList> call = rest.getAllUc(map_map);
        call.enqueue(new Callback<UcList>() {
            @Override
            public void onResponse(Call<UcList> call, Response<UcList> response) {
                Log.e("AllLIST", "onResponse: " + response.body());

                success = response.body().getSuccess();
                Log.e("ALLLIST", "onResponse: " + success);
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {

                    UcList ucList = response.body();
                    ucList_Lists = ucList.getUcList();
                    List<Schoolslist> schoolslists = ucList.getSchoolslist();
                    Log.e("UCList", "onResponse: " + ucList_Lists + "     value     " + schoolslists);
                     uc_complaint_spinner.setEnabled(true);
                    // Toast.makeText(HomeScreen.this, "data get", Toast.LENGTH_SHORT).show();


                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UcList> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadAllSchools(final String ids) {

        map_map.put("uc_id", ids);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        dialog.show();
        RestApi rest = retrofit.create(RestApi.class);
        Call<SchoolLIst> call = rest.getAllSchool(map_map);
        call.enqueue(new Callback<SchoolLIst>() {
            @Override
            public void onResponse(Call<SchoolLIst> call, Response<SchoolLIst> response) {
                Log.e("AllLIST", "onResponse: " + response.body());

                //success = response.body().getSuccess();
                Log.e("ALLLIST", "onResponse: " + success);
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {
                    //if (success == 1) {
                    SchoolLIst schoolist = response.body();
                    List<UcList> ucList_Lists = schoolist.getUcList();
                    List<TehsilsList> tehsilList = schoolist.getTehsilsList();
                    schoolslists = schoolist.getSchoolslist();
                    schoolslists.add(school_data);
                    Log.e("ALLL SCHOOL", "onResponse:value  of schools    " + schoolslists);

                    // Toast.makeText(HomeScreen.this, "data get", Toast.LENGTH_SHORT).show();

                    // }

                    school_complaint_spinner.setEnabled(true);
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SchoolLIst> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onComplete() {
        bt_submitbt.setEnabled(false);
           //finish();
    }


    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;

        }
        return isAvailable;
    }

}
