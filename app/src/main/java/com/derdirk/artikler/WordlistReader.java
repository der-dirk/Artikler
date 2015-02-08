package com.derdirk.artikler;

import android.content.Context;

import java.util.List;

public interface WordlistReader
{
  public boolean read(Context context);
  public List<String> getWordlist();
}
