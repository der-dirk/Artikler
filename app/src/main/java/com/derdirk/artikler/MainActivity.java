package com.derdirk.artikler;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

  /**
   * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
   */
  private NavigationDrawerFragment mNavigationDrawerFragment;

  /**
   * Used to store the last screen title. For use in {@link #restoreActionBar()}.
   */
  private CharSequence mTitle;

  private WordList _wordList = null;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (_wordList == null)
      _wordList = new WordList(new TextFileWordlistReader("wortliste.txt",this));

    // TODO: Don't do twice
    // Restore preferences
    SharedPreferences settings = getSharedPreferences("Artikler", MODE_PRIVATE);
    _wordList.setCurrentWordIndex(settings.getInt("CurrentWordIndex", 0));

    mNavigationDrawerFragment = (NavigationDrawerFragment)
            getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    mTitle = getTitle();

    // Set up the drawer.
    mNavigationDrawerFragment.setUp(
            R.id.navigation_drawer,
            (DrawerLayout) findViewById(R.id.drawer_layout));

  }

  @Override
  protected void onResume()
  {
    super.onResume();

    // TODO: Don't do twice
    // Restore preferences
    SharedPreferences settings = getSharedPreferences("Artikler", MODE_PRIVATE);
    _wordList.setCurrentWordIndex(settings.getInt("CurrentWordIndex", 0));
  }
  @Override
  protected void onPause()
  {
    super.onPause();

    // Save preferences
    SharedPreferences settings = getSharedPreferences("Artikler", MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt("CurrentWordIndex", _wordList.getCurrentWordIndex());
    editor.commit();
  }

  @Override
  public void onNavigationDrawerItemSelected(int position)
  {
    // update the main content by replacing fragments
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction()
            .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
            .commit();
  }

  public void onSectionAttached(int number)
  {
    switch (number)
    {
      case 1:
        mTitle = getString(R.string.title_section1);
        break;
      case 2:
        mTitle = getString(R.string.title_section2);
        break;
      case 3:
        mTitle = getString(R.string.title_section3);
        break;
    }
  }

  public void restoreActionBar()
  {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setTitle(mTitle);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    if (!mNavigationDrawerFragment.isDrawerOpen())
    {
      // Only show items in the action bar relevant to this screen
      // if the drawer is not showing. Otherwise, let the drawer
      // decide what to show in the action bar.
      getMenuInflater().inflate(R.menu.main, menu);
      restoreActionBar();
      return true;
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment implements View.OnClickListener
  {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    private int wordsPlayed = 0;
    private int wordsPlayedCorrectly = 0;
    private WordList _wordList = null;

    TextView currentWordTextView = null;
    TextView lastWordTextView = null;
    TextView lastWrongArticleTextView = null;
    Button button_der = null;
    Button button_die = null;
    Button button_das = null;
    TextView wordsPlayedCorrectlyTextView = null;
    TextView wordsPlayedTextView = null;
    EditText editText = null;

    public static PlaceholderFragment newInstance(int sectionNumber)
    {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    public PlaceholderFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);

      currentWordTextView = (TextView) rootView.findViewById(R.id.currentWordTextView);
      lastWrongArticleTextView = (TextView) rootView.findViewById(R.id.lastwrongArticleTextView);
      lastWordTextView = (TextView) rootView.findViewById(R.id.lastWordTextView);
      button_der = (Button) rootView.findViewById(R.id.button_der);
      button_die = (Button) rootView.findViewById(R.id.button_die);
      button_das = (Button) rootView.findViewById(R.id.button_das);
      wordsPlayedCorrectlyTextView = (TextView) rootView.findViewById(R.id.wordsPlayedCorrectlyTextView);
      wordsPlayedTextView = (TextView) rootView.findViewById(R.id.wordsPlayedTextView);
      editText = (EditText) rootView.findViewById(R.id.editText);

      button_der.setOnClickListener(this);
      button_die.setOnClickListener(this);
      button_das.setOnClickListener(this);

      lastWrongArticleTextView.setPaintFlags(lastWrongArticleTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

      _wordList = ((MainActivity)getActivity())._wordList;
      currentWordTextView.setText(_wordList.getCurrentWord());

//      {
//        List<String> words = _wordList.getAllWords();
//        if (!words.isEmpty())
//        {
//
//          StringBuilder builder = new StringBuilder();
//          for (String s : words)
//          {
//            builder.append(s);
//            builder.append("\n");
//          }
//          editText.setText(builder.toString());
//          showWord();
//        }
//        else
//        {
//          editText.setText("Error reading file");
//        }
//      }

      return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
      super.onAttach(activity);
      ((MainActivity) activity).onSectionAttached(
              getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onClick(View v)
    {
      String chosenArticle = "";
      if (v.getId() == R.id.button_der)
        chosenArticle = "der";
      else if (v.getId() == R.id.button_die)
        chosenArticle = "die";
      else if (v.getId() == R.id.button_das)
        chosenArticle = "das";

      if (!_wordList.getCurrentWordArticle().equals(chosenArticle))
      {
        lastWrongArticleTextView.setText(chosenArticle);
        lastWrongArticleTextView.setVisibility(View.VISIBLE);
      }
      else
      {
        ++wordsPlayedCorrectly;
        lastWrongArticleTextView.setVisibility(View.GONE);
        lastWrongArticleTextView.setText("");
      }

      ++wordsPlayed;
      lastWordTextView.setText(_wordList.getCurrentWordEntry());
      _wordList.next();
      currentWordTextView.setText(_wordList.getCurrentWord());
      showStatistics();
    }

    protected void showStatistics()
    {
      double percentage = (double) wordsPlayedCorrectly /(double)wordsPlayed;
      wordsPlayedCorrectlyTextView.setText(Integer.toString(wordsPlayedCorrectly));
      wordsPlayedTextView.setText("/" + Integer.toString(wordsPlayed) + " (" + String.format("%.0f%%", percentage*100) + ")");
    }

  }

}
