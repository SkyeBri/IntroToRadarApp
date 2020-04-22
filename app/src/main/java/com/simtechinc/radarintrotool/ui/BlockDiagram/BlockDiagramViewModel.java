package com.simtechinc.radarintrotool.ui.BlockDiagram;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BlockDiagramViewModel extends ViewModel
{

  private MutableLiveData<String> mText;

  public BlockDiagramViewModel()
  {
    mText = new MutableLiveData<>();
    mText.setValue("");
  }

  public void setText(String s) { mText.setValue(s); }
  public LiveData<String> getText()
  {
    return mText;
  }
}