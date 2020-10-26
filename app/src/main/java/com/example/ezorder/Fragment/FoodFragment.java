package com.example.ezorder.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.ezorder.Adapter.FoodFragmentAdapter;
import com.example.ezorder.Model.Food;
import com.example.ezorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FoodFragment extends Fragment {
    private static final String TAG = "Food FRG";

    ListView listView;
    List<Food> list;
    FloatingActionButton floatingActionButton;
    EditText eID, eName, ePrice, eUnit, eNameDetail, ePriceDetail, eUnitDetail;
    TextView txtFoodIDDetail;
    SwitchMaterial swFoodStatus, swFoodStatusDetail;
    ImageView imageFood, imageFoodDetail;
    Button btnshot, btnchoose, btnShotDetail, btnChooseDetail;
    Uri uri;
    FoodFragmentAdapter adapter;
    int foodIDs;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        listView = view.findViewById(R.id.food_fragment_listview);
        floatingActionButton = view.findViewById(R.id.fb_add_food);
        db = FirebaseFirestore.getInstance();
        checkMiss();

        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Food food = snapshot.toObject(Food.class);
                                list.add(food);
                            }
                            adapter = new FoodFragmentAdapter(getActivity(), list);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "onComplete: Load food data fail " + task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Load data fail" + e);
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = (Food) parent.getItemAtPosition(position);
                foodIDs = food.getFoodID();
                LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_food_detail, null);

                txtFoodIDDetail = view2.findViewById(R.id.txtFoodID2);
                eNameDetail = view2.findViewById(R.id.edtFoodName2);
                ePriceDetail = view2.findViewById(R.id.edtFoodPrice2);
                eUnitDetail = view2.findViewById(R.id.edtFoodUnit2);
                swFoodStatusDetail = view2.findViewById(R.id.swFoodStatus2);
                imageFoodDetail = view2.findViewById(R.id.foodImageDetail);
                btnShotDetail = view2.findViewById(R.id.btnShot2);
                btnChooseDetail = view2.findViewById(R.id.btnChoose2);

                int fID = food.getFoodID();
                String fName = food.getFoodName();
                int fPrice = food.getFoodPrice();
                String fUnit = food.getFoodUnit();
                String fImage = food.getFoodImage();
                int fStatus = food.getFoodStatus();

                txtFoodIDDetail.setText(String.valueOf(fID));
                eNameDetail.setText(fName);
                ePriceDetail.setText(String.valueOf(fPrice));
                eUnitDetail.setText(fUnit);
                swFoodStatusDetail.setChecked(fStatus == 1);
                byte[] arrs = Base64.decode(fImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(arrs, 0, arrs.length);
                imageFoodDetail.setImageBitmap(bitmap);

                btnShotDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            startActivityForResult(intent, 111);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                btnChooseDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 112);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.food_detail)
                        .setView(view2)
                        .setPositiveButton(R.string.button_Update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                foodIDs = Integer.parseInt(txtFoodIDDetail.getText().toString().trim());
                                String foodName = eNameDetail.getText().toString().trim();
                                int foodPrice = Integer.parseInt(ePriceDetail.getText().toString().trim());
                                String foodUnit = eUnitDetail.getText().toString().trim();
                                int foodStatus;
                                if (swFoodStatusDetail.isChecked()) {
                                    foodStatus = 1;
                                } else {
                                    foodStatus = 0;
                                }

                                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageFoodDetail.getDrawable();
                                Bitmap bitmap = bitmapDrawable.getBitmap();
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] arays = byteArrayOutputStream.toByteArray();
                                bitmap.recycle();
                                String imageString = Base64.encodeToString(arays, Base64.DEFAULT);
                                Food food = new Food(foodIDs, foodName, foodPrice, imageString, foodUnit, foodStatus);
                                db.collection("Food")
                                        .document(String.valueOf(foodIDs))
                                        .update("foodName", foodName,
                                                "foodPrice", foodPrice,
                                                "foodUnit", foodUnit,
                                                "foodStatus", foodStatus,
                                                "foodImage", imageString)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Update food success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Update food error " + e);
                                            }
                                        });

                            }
                        })
                        .setNegativeButton(R.string.button_Delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Food food = new Food(foodIDs);
                                db.collection("Food")
                                        .document(String.valueOf(foodIDs))
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Delete food success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Delete food error " + e);
                                            }
                                        });
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_add_food, null);

                eID = view1.findViewById(R.id.edtFoodID);
                eName = view1.findViewById(R.id.edtFoodName);
                ePrice = view1.findViewById(R.id.edtFoodPrice);
                eUnit = view1.findViewById(R.id.edtFoodUnit);
                swFoodStatus = view1.findViewById(R.id.swFoodStatus);
                imageFood = view1.findViewById(R.id.FoodImage);
                btnshot = view1.findViewById(R.id.btnShot);
                btnchoose = view1.findViewById(R.id.btnChoose);


                btnshot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            startActivityForResult(intent, 123);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                // Continue
                btnchoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 999);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.add_food)
                        .setView(view1)
                        .setPositiveButton(R.string.button_OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int foodID = Integer.parseInt(eID.getText().toString().trim());
                                String foodName = eName.getText().toString().trim();
                                int foodPrice = Integer.parseInt(ePrice.getText().toString().trim());
                                String foodUnit = eUnit.getText().toString().trim();
                                int foodStatus;
                                if (swFoodStatus.isChecked()) {
                                    foodStatus = 1;
                                } else {
                                    foodStatus = 0;
                                }

                                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageFood.getDrawable();
                                Bitmap bitmap = bitmapDrawable.getBitmap();
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                byte[] arays = byteArrayOutputStream.toByteArray();
                                bitmap.recycle();
                                String imageString = Base64.encodeToString(arays, Base64.DEFAULT);
                                Food food = new Food(foodID, foodName, foodPrice, imageString, foodUnit, foodStatus);
                                db.collection("Food")
                                        .document(String.valueOf(food.getFoodID()))
                                        .set(food)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Add food success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Add food error " + e);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton(R.string.button_Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    void checkMiss() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 666);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 777);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 123 && resultCode == -1) {
            assert data != null;
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap) extra.get("data");
            imageFood.setImageBitmap(bitmap);
        }

        if (requestCode == 999 && resultCode == -1) {
            assert data != null;
            uri = data.getData();
            imageFood.setImageURI(uri);
        }

        if (requestCode == 111 && resultCode == -1) {
            assert data != null;
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap) extra.get("data");
            imageFoodDetail.setImageBitmap(bitmap);
        }

        if (requestCode == 112 && resultCode == -1) {
            assert data != null;
            uri = data.getData();
            imageFoodDetail.setImageURI(uri);
        }
    }
}
