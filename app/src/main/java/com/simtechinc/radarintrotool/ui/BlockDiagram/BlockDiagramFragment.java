package com.simtechinc.radarintrotool.ui.BlockDiagram;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.simtechinc.radarintrotool.R;

public class BlockDiagramFragment extends Fragment
{

  private BlockDiagramViewModel homeViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState)
  {
    homeViewModel =
        ViewModelProviders.of(this).get(BlockDiagramViewModel.class);
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    final TextView textView = root.findViewById(R.id.text_home);

    homeViewModel.getText().observe(this, new Observer<String>()
    {
      @Override
      public void onChanged(@Nullable String s)
      {
        String text = s + "\n\n" + getString(R.string.homeDefaultText);
        textView.setText(text);
      }
    });

    root.findViewById(R.id.btnTransmitter).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        String text = getString(R.string.transmitterText) + "\n\n" + getString(R.string.transmitterSarcasmText);
        homeViewModel.setText(text);
      }
    });

    root.findViewById(R.id.btnWaveformGenerator).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.waveformText));
      }
    });

    root.findViewById(R.id.btnTurnover).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.turnoverText));
      }
    });

    root.findViewById(R.id.btnReceiver).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.receiverText));
      }
    });

    root.findViewById(R.id.btnAD).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.analogDigitalString));
      }
    });

    root.findViewById(R.id.btnPulseCompression).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.pulseCompressionText));
      }
    });

    root.findViewById(R.id.btnDopplerProcessing).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.dopplerProcessingText));
      }
    });

    root.findViewById(R.id.btnDetection).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.detectionText));
      }
    });

    root.findViewById(R.id.btnTracking).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.trackingText));
      }
    });

    root.findViewById(R.id.btnConsoleDisplay).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.consoleShamelessPlug));
      }
    });

    root.findViewById(R.id.btnRecording).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.recordingText));
      }
    });

    root.findViewById(R.id.btnTargetCrossSection).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.targetCrossSectionText));
      }
    });

    root.findViewById(R.id.btnAntenna).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.antenna));
      }
    });

    root.findViewById(R.id.btnPropagationMedium).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        homeViewModel.setText(getString(R.string.propagationMedium));
      }
    });

    return root;
  }
}