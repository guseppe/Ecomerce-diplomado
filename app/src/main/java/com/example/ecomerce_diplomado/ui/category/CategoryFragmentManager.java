package com.example.ecomerce_diplomado.ui.category;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Category;
import com.example.ecomerce_diplomado.services.FirebaseService;
import com.example.ecomerce_diplomado.services.Response;
import com.example.ecomerce_diplomado.utils.PhotoOptions;
import com.example.ecomerce_diplomado.utils.SystemProperties;

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
    private Uri uri;
    private ContentResolver contentResolver;
    private Category element;
    private Context context;
    private static final int PICK_IMAGE = 1;
    private static final int CHOOSE_GALLERY = 2;
    private static final int TAKE_PHOTO = 3;
    private final String CATEGORY = "CATEGORY";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);
        contentResolver = getActivity().getContentResolver();
        Bundle bundle = getArguments();
        if(bundle != null){
            element = bundle.getParcelable(CATEGORY);
        }
        context = getContext();
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
            String imageUrl = "";
            if(!profileDefault){
                FirebaseService.obtain().upload(uri, String.format("category/%s.jpg", categoryViewModel.getNextid()), (Response) ->{
                    saveOrUpdate(view,(String)Response);
                    profileDefault = true;
                }, (error) -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }else{
                saveOrUpdate(view,null);
            }
        });
        profile.setOnClickListener(v->{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                managePicture();
            }
        });
    }

    private void saveOrUpdate(View view, String imageUrl){
        if(element == null){
            categoryViewModel.addCategory(name.getText().toString(),imageUrl); //actualizar la imagen desde firebase en el fragmento de categorias
        }
        else{
            element.setName(name.getText().toString());
            element.setPhoto(imageUrl);
            categoryViewModel.updateCategoryNotify();
        }
        Navigation.findNavController(view).navigate(R.id.action_categoryFragmentManager_to_nav_category);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void managePicture(){
        profileDefault = true;
        final CharSequence[] options = SystemProperties.getOptions();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Elige la imagen de la categoría");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(PhotoOptions.TAKE_PHOTO.getValue())) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, TAKE_PHOTO);

                } else if (options[item].equals(PhotoOptions.CHOOSE_GALLERY.getValue())) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, CHOOSE_GALLERY);

                } else if (options[item].equals(PhotoOptions.CHOOSE_FOLDER.getValue())) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Selecciona la imagen"), PICK_IMAGE);

                } else if (options[item].equals(PhotoOptions.CANCEL.getValue())) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==PICK_IMAGE || requestCode == CHOOSE_GALLERY )&& resultCode== Activity.RESULT_OK){
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
        else if(requestCode == TAKE_PHOTO && resultCode== Activity.RESULT_OK){
            uri = data.getData();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(bitmap);
            profileDefault = false;
        }
    }
}
