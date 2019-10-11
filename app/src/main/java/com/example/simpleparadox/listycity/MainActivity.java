package com.example.simpleparadox.listycity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> cityDataList;

    CustomList customList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String TAG = "Sample";

        final Button addCityButton;
        final EditText addCityEditText;
        final EditText addProvinceEditText;
        final EditText deleteCityEditText;
        final Button deleteCityButton;

        FirebaseFirestore db;
        addCityButton = findViewById(R.id.add_city_button);
        addCityEditText = findViewById(R.id.add_city_field);
        addProvinceEditText = findViewById(R.id.add_province_edit_text);
        deleteCityEditText = findViewById(R.id.delete_city_field);
        deleteCityButton = findViewById(R.id.delete_city_button);


        cityList = findViewById(R.id.city_list);



        cityDataList = new ArrayList<>();


        cityAdapter = new CustomList(this, cityDataList);

        cityList.setAdapter(cityAdapter);

        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Cities");

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cityName = addCityEditText.getText().toString();
                final String provinceName = addProvinceEditText.getText().toString();

                HashMap<String, String> data = new HashMap<>();

                if(cityName.length() >0 && provinceName.length() >0){
                    data.put("province_name",provinceName);

                    collectionReference
                            .document(cityName)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"Data addition successful");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Data addition failed" + e.toString());
                                }
                            });

                    addCityEditText.setText("");
                    addProvinceEditText.setText("");

                    collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            cityDataList.clear();
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                Log.d(TAG,String.valueOf(doc.getData().get("province_name")));
                                String city = doc.getId();
                                String province = (String) doc.getData().get("province_name");

                                cityDataList.add(new City(city,province));

                            }
                            cityAdapter.notifyDataSetChanged();
                        }
                    });



                }

            }
        });


        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String deleteCityName = deleteCityEditText.getText().toString();


                    collectionReference
                            .document(deleteCityName)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"Data delete successful");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Data delete failed" + e.toString());
                                }
                            });

                    deleteCityEditText.setText("");

                    collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            cityDataList.clear();
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                Log.d(TAG,String.valueOf(doc.getData().get("province_name")));
                                String city = doc.getId();
                                String province = (String) doc.getData().get("province_name");

                                cityDataList.add(new City(city,province));

                            }
                            cityAdapter.notifyDataSetChanged();
                        }
                    });


                }else{
                    Log.d(TAG,"no such city");

                }





            }


        });

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                cityDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG,String.valueOf(doc.getData().get("province_name")));
                    String city = doc.getId();
                    String province = (String) doc.getData().get("province_name");

                    cityDataList.add(new City(city,province));

                }
                cityAdapter.notifyDataSetChanged();
            }
        });







    }


}
