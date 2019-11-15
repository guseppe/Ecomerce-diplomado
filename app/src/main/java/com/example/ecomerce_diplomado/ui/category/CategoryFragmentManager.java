package com.example.ecomerce_diplomado.ui.category;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Category;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CategoryFragmentManager extends Fragment {
    @Nullable
    private CategoryViewModel categoryViewModel;
    private ImageView profile;
    private Button save;
    private Button back;
    private EditText name;
    private boolean profileDefault;
    private static final int PICK_IMAGE = 123;
    private Uri uri;
    private ContentResolver contentResolver;
    private final String CATEGORY = "CATEGORY";
    private Category element;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);
        contentResolver = getActivity().getContentResolver();
        Bundle bundle = getArguments();
        if(bundle != null){
            element = bundle.getParcelable(CATEGORY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_manager, container, false);
        profile = view.findViewById(R.id.profile);
        save = view.findViewById(R.id.manager);
        back = view.findViewById(R.id.back);
        name = view.findViewById(R.id.nameCat);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(element != null){
            name.setText(element.getName());
        }
        back.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_categoryFragmentManager_to_nav_category);
        });
        save.setOnClickListener(v->{
            if(element ==null){
                categoryViewModel.addCategory(name.getText().toString(),"ic_menu_camera");
            }
            else{
                element.setName(name.getText().toString());
                categoryViewModel.updateCategory(element);
            }
            Navigation.findNavController(view).navigate(R.id.action_categoryFragmentManager_to_nav_category);
        });
        profile.setOnClickListener(v->{
            profileDefault = true;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagen"),PICK_IMAGE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode== Activity.RESULT_OK){
            try{
                uri=data.getData();
                InputStream inputStream = contentResolver.openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profile.setImageBitmap(bitmap);
                profileDefault = false;
            }catch (FileNotFoundException fnex){
                fnex.printStackTrace();
            }
        }
    }
}
