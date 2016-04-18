package com.emugabi.github.activitycolorchooser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.emugabi.github.activitycolorblocks.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColorPickerFragment.onColorFragmentListener} interface
 * to handle interaction events.
 * Use the {@link ColorPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorPickerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "colors";
    private static String TAG = ColorPickerFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private int[] colorsParam = {};
    private onColorFragmentListener mListener;

    public ColorPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param colorsParam Parameter 1
     * @return A new instance of fragment ColorBlocksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorPickerFragment newInstance(int[] colorsParam) {
        ColorPickerFragment fragment = new ColorPickerFragment();
        Bundle args = new Bundle();
        args.putIntArray(ARG_PARAM1, colorsParam);
        Log.d(TAG, "newInstance: color parameter has " + colorsParam.length + " colors");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            colorsParam = getArguments().getIntArray(ARG_PARAM1);
            Log.d(TAG, "onCreate: color parameter has " + colorsParam.length + " colors");
        } else {
            Log.d(TAG, "onCreate: color parameter !s empty");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_color_blocks, container, false);
        GridLayout layout = (GridLayout) rootView.findViewById(R.id.color_block_gridlayout);
        generateColorBlocks(colorsParam, layout);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed(finalI);
                }
            });
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int colorPosition) {
        if (mListener != null) {
            mListener.onColorSelected(colorPosition);
        }
    }


    public void generateColorBlocks(int[] colors, GridLayout layout) {
        int column = 3;
        int total = colors.length;
        int rows = total / column;

        layout.setColumnCount(column);
        layout.setRowCount(rows + 1);

        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            if (c == column) {
                c = 0;
                r++;
            }

            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.rowSpec = GridLayout.spec(r);
            layoutParams.columnSpec = GridLayout.spec(c);
            layoutParams.setGravity(Gravity.CENTER);

            ImageView imageView = new ImageView(layout.getContext());
            imageView.setBackgroundColor(colorsParam[i]);
            imageView.setLayoutParams(layoutParams);
            layout.addView(imageView);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onColorFragmentListener) {
            mListener = (onColorFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onColorFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface onColorFragmentListener {
        // TODO: Update argument type and name
        void onColorSelected(int colorPosition);
    }
}
