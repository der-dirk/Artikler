package com.derdirk.artikler;

import java.util.List;

public interface WordListReader
{
  public boolean read();
  public List<String> getWordlist();
}
