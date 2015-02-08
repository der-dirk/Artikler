package com.derdirk.artikler;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TextFileWordlistReader implements WordlistReader
{
  private String _filename;
  private List<String> _wordlist;

  public TextFileWordlistReader(String filename)
  {
    _filename = filename;
  }

  public boolean read(Context context)
  {
    AssetManager am = context.getAssets();
    try
    {
      _wordlist = new ArrayList<>();
      InputStream in = am.open(_filename);
      InputStreamReader is = new InputStreamReader(in);
      BufferedReader br = new BufferedReader(is);
      String read = br.readLine();

      while(read!=null)
      {
        read = br.readLine();
        if (read!=null)
          _wordlist.add(read);
      }
    }
    catch(IOException e)
    {
      return false;
    }

    return true;
  }

  public List<String> getWordlist()
  {
    return _wordlist;
  }
}
