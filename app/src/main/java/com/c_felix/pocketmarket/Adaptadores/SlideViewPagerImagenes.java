package com.c_felix.pocketmarket.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.c_felix.pocketmarket.Clases.Multimedia;
import com.c_felix.pocketmarket.R;

import java.util.List;

public class SlideViewPagerImagenes extends PagerAdapter {

    private List<Multimedia> listaMultimedia;
    private Context context;

    public SlideViewPagerImagenes(Context context, List<Multimedia> lista) {
        this.listaMultimedia = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaMultimedia.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == (view);
    }

    private Multimedia getMedia(int position) {
        return listaMultimedia.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.visualizador_media, null);
        try {
            ImageView imageView = view.findViewById(R.id.image);
            {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(getMedia(position).getImagen());
            }
            container.addView(view);
        }catch (Exception e){}
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }
}
