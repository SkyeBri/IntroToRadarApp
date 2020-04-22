package com.simtechinc.radarintrotool;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FractionTextViewFragment extends Fragment
{

  TextView tvNumerator, tvDenominator;
  View line;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_fraction_text_view, container, false);

    tvNumerator = view.findViewById(R.id.tvNumerator);
    tvDenominator = view.findViewById(R.id.tvDenominator);
    line = view.findViewById(R.id.divider);

    return view;
  }

  public void setNumerator(String n)
  {
    tvNumerator.setText(n);
  }

  public void setDenominator(String d)
  {
    tvDenominator.setText(d);
  }

  public void setTextColor(int color) { tvNumerator.setTextColor(color); tvDenominator.setTextColor(color); line.setBackgroundColor(color);}
}
