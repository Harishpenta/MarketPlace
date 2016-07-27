package com.hashrail.marketplace.ProfileMaking;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.hashrail.marketplace.DB.DBHelper;
import com.hashrail.marketplace.R;
import com.hashrail.marketplace.activity.MainActivity;
import com.hashrail.marketplace.utils.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UpdateProfile extends AppCompatActivity {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_IMAGE1 = 2;
    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_FILE = 1;
    Toolbar toolbarProfileUpdate;
    DBHelper db;
    SQLiteDatabase sd;
    SessionManager session;
    HashMap<String, String> userdetails;
    Button btnDone;
    FloatingActionButton fbEdit;
    BottomSheetBehavior behavior;
    private Uri fileUri, fileUriChoose; // file url to store image/video
    private CircularImageView imgPreview;
    private String filePath, filePathChoose;
    private String userChoosenTask;
    private BottomSheetDialog mBottomSheetDialog;

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    //************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        toolbarProfileUpdate = (Toolbar) findViewById(R.id.toolbarProfileUpdate);
        fbEdit = (FloatingActionButton) findViewById(R.id.fabEditPic);
        setSupportActionBar(toolbarProfileUpdate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        db = new DBHelper(getApplicationContext());
        sd = db.getReadableDatabase();
        sd = db.getWritableDatabase();
        session = new SessionManager(getApplicationContext());
        userdetails = new HashMap<>();
        userdetails = session.getUserDetails();
        imgPreview = (CircularImageView) findViewById(R.id.imgPreview);
        //btnDone = (Button) findViewById(R.id.btnDone);
        session = new SessionManager(getApplicationContext());
       /* View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });*/
        /**
         * Capture image button click event
         */
        fbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                //showBottomSheetDialog();

            }
        });
        setProfileImageFromDB();
        /*btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateProfile.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });*/
    }


    private void showBottomSheetDialog() {
        try {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mBottomSheetDialog = new BottomSheetDialog(this);
            View view = getLayoutInflater().inflate(R.layout.sheet, null);
            TextView txtcameraclick = (TextView) view.findViewById(R.id.txtcameraclick);
            TextView txtgalleryclick = (TextView) view.findViewById(R.id.txtgalleryclick);
            TextView txtcancelclick = (TextView) view.findViewById(R.id.txtcancelclick);
            final boolean res = Utility.checkPermission(UpdateProfile.this);
            mBottomSheetDialog.setContentView(view);
            mBottomSheetDialog.show();
            mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mBottomSheetDialog = null;
                }
            });

            txtcameraclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mBottomSheetDialog.dismiss();
                    userChoosenTask = "Take Photo";
                    if (res) {
                        captureImage();
                    }


                }
            });
            txtgalleryclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                    userChoosenTask = "Choose from Gallery";
                    if (res) {
                        galleryIntent();
                    }
                }
            });

            txtcancelclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mBottomSheetDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setProfileImageFromDB() {

        BitmapFactory.Options options = new BitmapFactory.Options();

        // down sizing image as it throws OutOfMemory Exception for larger

        // images
        String naya = userdetails.get(SessionManager.KEY_IMAGE_PATH);
        if (naya != null) {
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(naya, options);

            imgPreview.setImageBitmap(bitmap);
        }
        /*String qry = "SELECT * FROM encoded";
        Cursor cr = sd.rawQuery(qry, new String[]{});
        encodedImage = cr.getBlob(0);

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);*/
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(UpdateProfile.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result) {
                        captureImage();
                    }

                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result) {
                        galleryIntent();
                    }
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);

    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        filePath = fileUri.getPath();

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);


    }

    /**
     * returning image / video
     */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
        filePath = fileUri.getPath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        captureImage();
                    else if (userChoosenTask.equals("Choose from Gallery"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }

    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image


        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity

                if (filePath != null) {
                    // Displaying the image or video on the screen
                    previewMedia();
                    session.setImagePath(filePath);
                    Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                  /*  Intent i = new Intent(UpdateProfile.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);*/
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == SELECT_FILE) {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
                imgPreview.setVisibility(View.VISIBLE);
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};

                try {
                    Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filePathChoose = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d("Picture Path", filePathChoose);
                    session.setImagePath(filePathChoose);

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    // down sizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 8;

                    final Bitmap bitmap = BitmapFactory.decodeFile(filePathChoose, options);
                    imgPreview.setImageBitmap(bitmap);

                    Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                  /*  Intent i = new Intent(UpdateProfile.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);*/
                } catch (Exception e) {
                    Log.e("Path Error", e.toString());
                }


                /*try {
                    imgPreview.setVisibility(View.VISIBLE);
                    //final Uri imageUri = data.getData();
                    session.setImagePath(filePathChoose);
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgPreview.setImageBitmap(selectedImage);
                    //String file = imageUri.getPath();
                    //String encodedValue = convertToBase64(file);
                    //String encodedValue = bitmapToBase64(selectedImage, imageUri);

                    // String encoded = encodedValue;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/


                /*if (filePathChoose != null) {
                    // Displaying the image or video on the screen
                    // previewMedia();
                    imgPreview.setVisibility(View.VISIBLE);
                    //vidPreview.setVisibility(View.GONE);
                    // bimatp factory
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    // down sizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 8;

                    final Bitmap bitmap = BitmapFactory.decodeFile(filePathChoose, options);

                    imgPreview.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
                }*/

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }


    /**
     * Displaying captured image/video on the screen
     */
    private void previewMedia() {
        // Checking whether captured media is image or video

        imgPreview.setVisibility(View.VISIBLE);
        //vidPreview.setVisibility(View.GONE);
        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // down sizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        imgPreview.setImageBitmap(bitmap);

    }


    private Bitmap decodeFromBase64ToBitmap(String encodedImage)

    {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }

    private String convertToBase64(String imagePath)

    {
        String encodedImage = null;

        try {
            Bitmap bm = BitmapFactory.decodeFile(imagePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] byteArrayImage = baos.toByteArray();

            encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedImage;
    }

    private String bitmapToBase64(Bitmap bitmap, Uri uri) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        String type = getContentResolver().getType(uri);//GET FILE EXTENSION TYPE
        if (type.contains("jpeg") || type.contains("jpg") || type.contains("JPEG") || type.contains("JPG")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        }
        if (type.contains("png") || type.contains("PNG")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        }
        if (type.contains("webp") || type.contains("WEBP")) {
            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream);
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ContentValues values = new ContentValues();

        values.put("encoded", byteArray);
        sd.insert(db.TB_ENCODEDIMAGE, null, values);

        Log.d("see", values.toString());

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
        /* bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream);*/

    }

    //************************************


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profileok, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_profileok:
                Intent i = new Intent(UpdateProfile.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
