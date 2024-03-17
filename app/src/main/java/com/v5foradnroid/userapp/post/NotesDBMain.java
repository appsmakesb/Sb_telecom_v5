package com.v5foradnroid.userapp.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.v5foradnroid.userapp.R;

public class NotesDBMain extends Fragment implements View.OnClickListener {

    CardView var_upload, var_getAlldata, var_Category, var_post_byCategory, var_sell_list, var_sell_byCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ft_main, container, false);

        var_upload = view.findViewById(R.id.cv_upload);
        var_getAlldata = view.findViewById(R.id.cv_getAlldata);
        var_Category = view.findViewById(R.id.cv_Category);
        var_post_byCategory = view.findViewById(R.id.cv_post_byCategory);
        var_sell_list = view.findViewById(R.id.cv_sell_list);
        var_sell_byCategory = view.findViewById(R.id.cv_sell_byCategory);

        var_upload.setOnClickListener(this);
        var_getAlldata.setOnClickListener(this);
        var_Category.setOnClickListener(this);
        var_post_byCategory.setOnClickListener(this);
        var_sell_list.setOnClickListener(this);
        var_sell_byCategory.setOnClickListener(this);

        String pref = SessionHandler.getPref("phone", requireContext());
        String pref2 = SessionHandler.getPref("pass", requireContext());

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cv_upload) {
            startActivity(new Intent(requireContext(), UploadPost.class));
        } else if (v.getId() == R.id.cv_getAlldata) {
            Intent intent = new Intent(requireContext(), GetPosts.class);
            intent.putExtra("allorSell", "/get_posts.php");
            startActivity(intent);
        } else if (v.getId() == R.id.cv_Category) {
            startActivity(new Intent(requireContext(), CategoryActivity.class));
        } else if (v.getId() == R.id.cv_post_byCategory) {
            Intent intent = new Intent(requireContext(), UserCategory.class);
            intent.putExtra("category", "/displaying_category.php");
            intent.putExtra("allorSell", "/get_posts_two.php");
            intent.putExtra("sellPost", "/get_posts.php");
            startActivity(intent);
        } else if (v.getId() == R.id.cv_sell_list) {
            Intent intent = new Intent(requireContext(), GetPosts.class);
            intent.putExtra("allorSell", "/sell_post.php");
            startActivity(intent);
        } else if (v.getId() == R.id.cv_sell_byCategory) {
            Intent intent = new Intent(requireContext(), UserCategory.class);
            intent.putExtra("category", "/sell_category.php");
            intent.putExtra("allorSell", "/sell_two.php");
            intent.putExtra("sellPost", "/sell_post.php");
            startActivity(intent);
        }
    }
}