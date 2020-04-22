package com.simtechinc.radarintrotool.ui.RadarRangeEquation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.simtechinc.radarintrotool.FractionTextViewFragment;
import com.simtechinc.radarintrotool.R;

import java.text.DecimalFormat;

public class RadarRangeFragment extends Fragment
{
  private FractionTextViewFragment trackEquation, searchEquation;
  private TextView tvKey,tvTrackEquationResult, tvSearchEquationResult, tvTrackEquals, tvSearchEquals;
  private EditText etP_t, etP_av, etG, etA_e, etLambda, ett_s, etSigma, etOmega, etR, etT_s, etB_n, etL;
  private Button btnTrack, btnSearch;
  private View keyView;
  private static final double k = 1.38 * Math.pow(10,-23);
  private DecimalFormat decimalFormat;
  private static final String PREF_FILE = "com.simtechinc.radarintrotool.ui.RadarRangeEquation.prefs";

  @RequiresApi(api = Build.VERSION_CODES.M)
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState)
  {
    View root = inflater.inflate(R.layout.fragment_radar_range, container, false);

    trackEquation = (FractionTextViewFragment)(getChildFragmentManager().findFragmentById(R.id.trackEquation));
    searchEquation = (FractionTextViewFragment)(getChildFragmentManager().findFragmentById(R.id.searchEquation));
    tvKey = root.findViewById(R.id.tvKEY);
    tvTrackEquationResult = root.findViewById(R.id.trackEquationResult);
    tvTrackEquals = root.findViewById(R.id.trackEquationRight);
    tvSearchEquationResult = root.findViewById(R.id.searchEquationResult);
    tvSearchEquals = root.findViewById(R.id.searchEquationRight);
    etP_t = root.findViewById(R.id.etP_t);
    etP_av = root.findViewById(R.id.etP_av);
    etG = root.findViewById(R.id.etG);
    etA_e = root.findViewById(R.id.etA_e);
    etLambda = root.findViewById(R.id.etLambda);
    ett_s = root.findViewById(R.id.ett_s);
    etSigma = root.findViewById(R.id.etSigma);
    etOmega = root.findViewById(R.id.etOmega);
    etR = root.findViewById(R.id.etR);
    etT_s = root.findViewById(R.id.etT_s);
    etB_n = root.findViewById(R.id.etB_n);
    etL = root.findViewById(R.id.etL);
    btnTrack = root.findViewById(R.id.btnTrack);
    btnSearch = root.findViewById(R.id.btnSearch);
    keyView = root.findViewById(R.id.keyView);

    trackEquation.setNumerator("Pₜ G² \u03BB² \u03C3");
    trackEquation.setDenominator("(4\u03C0)³ R⁴ k Tₛ Bₙ L");
    trackEquation.setTextColor(getResources().getColor(R.color.colorPrimaryLight,null));

    searchEquation.setNumerator("Pₐᵥ Aₑ tₛ \u03C3");
    searchEquation.setDenominator("4\u03C0 \u03A9 R⁴ k Tₛ L");
    searchEquation.setTextColor(getResources().getColor(R.color.colorPrimaryLight,null));

    decimalFormat = new DecimalFormat("#.#####");

    //Get Preferences
    SharedPreferences prefs = getActivity().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    etP_t.setText(prefs.getString("Pt",""));
    etP_av.setText(prefs.getString("Pav",""));
    etG.setText(prefs.getString("G",""));
    etA_e.setText(prefs.getString("Ae",""));
    etLambda.setText(prefs.getString("lambda",""));
    ett_s.setText(prefs.getString("ts",""));
    etSigma.setText(prefs.getString("sigma",""));
    etOmega.setText(prefs.getString("omega",""));
    etR.setText(prefs.getString("R",""));
    etT_s.setText(prefs.getString("Ts",""));
    etB_n.setText(prefs.getString("Bn",""));
    etL.setText(prefs.getString("L",""));
    keyView.setVisibility(prefs.getInt("keyVisibility",View.VISIBLE));
    calculateSignalToNoiseSearch();
    calculateSignalToNoiseTrack();

    btnTrack.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        calculateSignalToNoiseTrack();
      }
    });

    btnSearch.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        calculateSignalToNoiseSearch();
      }
    });

    tvKey.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if(keyView.getVisibility() == View.GONE)
        {
          keyView.setVisibility(View.VISIBLE);
        }
        else
        {
          keyView.setVisibility(View.GONE);
        }
      }
    });
    return root;
  }

  @Override
  public void onPause()
  {
    super.onPause();
    savePreferences();
  }

  @Override
  public void onStop()
  {
    super.onStop();
    savePreferences();
  }

  @Override
  public void onDestroy()
  {
    super.onDestroy();
    savePreferences();
  }

  private void calculateSignalToNoiseTrack()
  {
    if(etP_t.getText().toString().isEmpty()    || etG.getText().toString().isEmpty()     ||
       etLambda.getText().toString().isEmpty() || etSigma.getText().toString().isEmpty() ||
       etR.getText().toString().isEmpty()      || etT_s.getText().toString().isEmpty()   ||
       etB_n.getText().toString().isEmpty()    || etL.getText().toString().isEmpty())
    {
      tvTrackEquals.setVisibility(View.GONE);
      tvTrackEquationResult.setText("");
      Toast toast = Toast.makeText(this.getContext(), getString(R.string.fill_fields), Toast.LENGTH_SHORT);
      toast.show();
      return;
    }

    tvTrackEquals.setVisibility(View.VISIBLE);
    double Pt = Double.valueOf(etP_t.getText().toString());
    double G = Double.valueOf(etG.getText().toString());
    double lambda = Double.valueOf(etLambda.getText().toString());
    double sigma = Double.valueOf(etSigma.getText().toString());
    double R = Double.valueOf(etR.getText().toString());
    double Ts = Double.valueOf(etT_s.getText().toString());
    double Bn = Double.valueOf(etB_n.getText().toString());
    double L = Double.valueOf(etL.getText().toString());

    double Result = Pt * Math.pow(G,2) * Math.pow(lambda,2) * sigma / (Math.pow(4 * Math.PI,3) * Math.pow(R,4) * k * Ts * Bn * L);
    tvTrackEquationResult.setText(decimalFormat.format(Result));
  }

  private void calculateSignalToNoiseSearch()
  {
    if(etP_av.getText().toString().isEmpty() || etA_e.getText().toString().isEmpty()   ||
       ett_s.getText().toString().isEmpty()  || etSigma.getText().toString().isEmpty() ||
       etR.getText().toString().isEmpty()    || etT_s.getText().toString().isEmpty()   ||
       etL.getText().toString().isEmpty())
    {
      tvSearchEquals.setVisibility(View.GONE);
      tvSearchEquationResult.setText("");
      Toast toast = Toast.makeText(this.getContext(), getString(R.string.fill_fields), Toast.LENGTH_SHORT);
      toast.show();
      return;
    }

    tvSearchEquals.setVisibility(View.VISIBLE);
    double Pav = Double.valueOf(etP_av.getText().toString());
    double Ae = Double.valueOf(etA_e.getText().toString());
    double ts = Double.valueOf(ett_s.getText().toString());
    double sigma = Double.valueOf(etSigma.getText().toString());
    double R = Double.valueOf(etR.getText().toString());
    double Ts = Double.valueOf(etT_s.getText().toString());
    double L = Double.valueOf(etL.getText().toString());

    double Result = Pav * Ae * Math.pow(ts,2) * sigma / (4 * Math.PI * Math.pow(R,4) * k * Ts * L);
    tvSearchEquationResult.setText(decimalFormat.format(Result));
  }

  private void savePreferences()
  {
    SharedPreferences prefs = getActivity().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    prefs.edit().putString("Pt",etP_t.getText().toString()).apply();
    prefs.edit().putString("Pav",etP_av.getText().toString()).apply();
    prefs.edit().putString("G",etG.getText().toString()).apply();
    prefs.edit().putString("Ae",etA_e.getText().toString()).apply();
    prefs.edit().putString("lambda",etLambda.getText().toString()).apply();
    prefs.edit().putString("ts",ett_s.getText().toString()).apply();
    prefs.edit().putString("sigma",etSigma.getText().toString()).apply();
    prefs.edit().putString("omega",etOmega.getText().toString()).apply();
    prefs.edit().putString("R",etR.getText().toString()).apply();
    prefs.edit().putString("Ts",etT_s.getText().toString()).apply();
    prefs.edit().putString("Bn",etB_n.getText().toString()).apply();
    prefs.edit().putString("L",etL.getText().toString()).apply();
    prefs.edit().putInt("keyVisiblity",keyView.getVisibility());
  }
}