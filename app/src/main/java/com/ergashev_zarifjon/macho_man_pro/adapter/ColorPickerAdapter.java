package com.ergashev_zarifjon.macho_man_pro.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ergashev_zarifjon.macho_man_pro.R;

import java.util.ArrayList;
import java.util.List;


public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Integer> colorPickerColors;
    private OnColorPickerClickListener onColorPickerClickListener;

    ColorPickerAdapter(@NonNull Context context, @NonNull List<Integer> colorPickerColors) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.colorPickerColors = colorPickerColors;
    }

    public ColorPickerAdapter(@NonNull Context context) {
        this(context, getDefaultColors(context));
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.color_picker_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.colorPickerView.setBackgroundColor(colorPickerColors.get(position));
    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    private void buildColorPickerView(View view, int colorCode) {
        view.setVisibility(View.VISIBLE);

        ShapeDrawable biggerCircle = new ShapeDrawable(new OvalShape());
        biggerCircle.setIntrinsicHeight(20);
        biggerCircle.setIntrinsicWidth(20);
        biggerCircle.setBounds(new Rect(0, 0, 20, 20));
        biggerCircle.getPaint().setColor(colorCode);

        ShapeDrawable smallerCircle = new ShapeDrawable(new OvalShape());
        smallerCircle.setIntrinsicHeight(5);
        smallerCircle.setIntrinsicWidth(5);
        smallerCircle.setBounds(new Rect(0, 0, 5, 5));
        smallerCircle.getPaint().setColor(Color.WHITE);
        smallerCircle.setPadding(10, 10, 10, 10);
        Drawable[] drawables = {smallerCircle, biggerCircle};

        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        view.setBackgroundDrawable(layerDrawable);
    }

    public void setOnColorPickerClickListener(OnColorPickerClickListener onColorPickerClickListener) {
        this.onColorPickerClickListener = onColorPickerClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View colorPickerView;

        public ViewHolder(View itemView) {
            super(itemView);
            colorPickerView = itemView.findViewById(R.id.color_picker_view);
            itemView.setOnClickListener(v -> {
                if (onColorPickerClickListener != null)
                    onColorPickerClickListener.onColorPickerClickListener(colorPickerColors.get(getAdapterPosition()));
            });
        }
    }

    public interface OnColorPickerClickListener {
        void onColorPickerClickListener(int colorCode);
    }

    public static List<Integer> getDefaultColors(Context context) {
        ArrayList<Integer> colorPickerColors = new ArrayList<>();
        /*colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.black));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.sky_blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.violet_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.white));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_green_color_picker));*/
        colorPickerColors.add(ContextCompat.getColor(context, R.color.amber_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.amber_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.amber_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.amber_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.amber_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.amber_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_grey_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_grey_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_grey_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_grey_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_grey_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_grey_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.cyan_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.cyan_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.cyan_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.cyan_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.cyan_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.cyan_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_orange_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_orange_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_orange_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_orange_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_orange_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_orange_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_purple_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_purple_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_purple_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_purple_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_purple_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.deep_purple_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.indigo_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.indigo_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.indigo_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.indigo_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.indigo_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.indigo_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.light_blue_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.light_blue_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.light_blue_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.light_blue_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.light_blue_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.light_blue_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.lime_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.lime_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.lime_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.lime_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.lime_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.lime_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.pink_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.pink_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.pink_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.pink_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.pink_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.pink_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.purple_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.purple_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.purple_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.purple_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.purple_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.purple_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.teal_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.teal_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.teal_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.teal_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.teal_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.teal_800));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_100));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_200));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_300));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_400));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_600));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_800));
        return colorPickerColors;
    }
}
