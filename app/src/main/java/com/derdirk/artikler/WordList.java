package com.derdirk.artikler;

import java.util.List;
import java.util.Random;

public class WordList
{
  private WordListReader _wordListReader   = null;
  private int            _currentWordIndex = 0;
  private List<String>   _words            = null;
  private Random         _rnd              = new Random();

  public WordList(WordListReader wordlistReader)
  {
    _wordListReader = wordlistReader;
    _wordListReader.read();
    _words = getAllWords();
  }

  public void next()
  {
    _currentWordIndex = _rnd.nextInt(_words.size());
  }

  protected String getCurrentWordEntry()
  {
    return (String)(_words.toArray()[_currentWordIndex]);
  }

  protected String getCurrentWord()
  {
    return getCurrentWordEntry().split(" ")[1];
  }

  protected String getCurrentWordArticle()
  {
    return getCurrentWordEntry().split(" ")[0];
  }

  public List<String> getAllWords()
  {
    return _wordListReader.getWordlist();
  }
}
