package com.derdirk.artikler;

import android.content.Context;

import java.util.List;

public interface WordListReader
{
  public boolean read();
  public List<String> getWordlist();
}
