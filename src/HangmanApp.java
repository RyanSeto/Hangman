import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;
import com.sun.midp.io.j2me.storage.*;

import java.io.*;

public class HangmanApp extends MIDlet implements CommandListener
{
	 private Display display;
	 private Form guessLetterForm;
	 private Form guessWordForm;
	 private Command exit = new Command("Exit", Command.EXIT, 1);
	 private Command start = new Command("Start", Command.SCREEN, 0);
	 private Command back = new Command("Back", Command.BACK, 0);
	 private Command guessLetter = new Command("Guess Letter", Command.SCREEN, 0);
	 private Command solve = new Command("Solve", Command.SCREEN, 1);
	 private Command newGameCommand = new Command("New Game", Command.SCREEN, 2);
	 private Command enterLetter = new Command("Enter", Command.SCREEN, 0);
	 private Command enterWord = new Command("Enter", Command.SCREEN, 0);
	 private myCanvas startScreen;
	 private mainCanvas mainPage;
	 private WordHandler handler;
	 private TextField guessLetterText;
	 private TextField guessWordText;
	 private String[] dictionary = new String[19912];
	 private String answerWord, knownWord;
	 
	 private Image[] hangmanPics = new Image[8];
	 {
		 try {
			hangmanPics[0] = Image.createImage("/Hangman.gif");
			hangmanPics[1] = Image.createImage("/Hangman1.gif");
			hangmanPics[2] = Image.createImage("/Hangman2.gif");
			hangmanPics[3] = Image.createImage("/Hangman3.gif");
			hangmanPics[4] = Image.createImage("/Hangman4.gif");
			hangmanPics[5] = Image.createImage("/Hangman5.gif");
			hangmanPics[6] = Image.createImage("/Hangman6.gif");
			hangmanPics[7] = Image.createImage("/Hangman7.gif");
		} 
		catch (Exception e)
		{
			System.out.println("Could not find the pictures.");
		}
	 }
	 
	 
	public HangmanApp() 
	{
		 display = Display.getDisplay(this);
		 guessLetterForm = new Form("Guess a Letter");
		 guessWordForm = new Form("Type in the answer");
		   
		 startScreen = new myCanvas(hangmanPics);
		 startScreen.addCommand(start);
		 startScreen.addCommand(exit);
		 startScreen.setCommandListener(this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException
	{
		
	}

	protected void pauseApp() 
	{
		
	}

	protected void startApp() throws MIDletStateChangeException 
	{
		readFile();
		handler = new WordHandler();	 
		display.setCurrent(startScreen);
	}
	
	public void commandAction(Command command, Displayable arg1) 
	{
		if (command == exit) 
		{
		    try 
		    {
				destroyApp(true);
			} 
		     
		    catch (MIDletStateChangeException e) 
		    {
				e.printStackTrace();
			}
		    
		    notifyDestroyed();
		}
		
		else if(command == start)
		{
			mainPage = new mainCanvas(hangmanPics);
			display.setCurrent(mainPage);
			newGame();
		}
		
		else if(command == back)
		{
			display.setCurrent(mainPage);
			mainPage.setCommandListener(this);
		}
		
		else if(command == guessLetter)
		{
			display.setCurrent(guessLetterForm);
			guessLetterText = new TextField("The word is: " + mainPage.getDrawWord() +"\nGuess a Letter:", "", 1, TextField.ANY);
			guessLetterForm.deleteAll();
			guessLetterForm.addCommand(back);
			guessLetterForm.addCommand(enterLetter);
			guessLetterForm.append(guessLetterText);
			guessLetterForm.setCommandListener(this);
		}
		
		else if(command == solve)
		{
			display.setCurrent(guessWordForm);
			guessWordText = new TextField("The word is: " + mainPage.getDrawWord() +"\nType in the answer:", "", 50, TextField.ANY);
			guessWordForm.deleteAll();
			guessWordForm.addCommand(back);
			guessWordForm.addCommand(enterWord);
			guessWordForm.append(guessWordText);
			guessWordForm.setCommandListener(this);
		}
		
		else if (command == enterLetter)
		{
			String guessedLetter = guessLetterText.getString().toLowerCase();
			
			if(!mainPage.alreadyGuessed(guessedLetter))
			{
				String tempWord = handler.checkWord(answerWord, guessedLetter, knownWord);

				if(tempWord == knownWord)
				{
					mainPage.setLabel("There is no '" + guessedLetter + "' in this word.");		
					mainPage.addWrong();
					checkLose();
				}

				else
				{
					mainPage.setLabel("Your guess was correct.");
					checkWin(tempWord);
				}

				mainPage.addGuessedLetter(guessedLetter);
				knownWord = tempWord;
				mainPage.setKnownWord(knownWord);
				display.setCurrent(mainPage);
				mainPage.setCommandListener(this);
			}
			
			else
			{
				guessLetterForm.append("You've guessed that letter already.");
			}
		}
		
		else if(command == enterWord)
		{
			String guessWord = guessWordText.getString().toLowerCase();
			
			if(answerWord.equals(guessWord))
			{
				mainPage.setLabel("YOU WIN!!");
				enterEndState();
			}
			
			else
			{
				mainPage.setLabel("Your guess was incorrect.");		
				mainPage.addWrong();
				checkLose();
			}
			
			display.setCurrent(mainPage);
			mainPage.setCommandListener(this);
		}
		
		else if(command == newGameCommand)
		{
			newGame();
		}
	}
	
	 private void readFile()
	 {
		  InputStream is = this.getClass().getResourceAsStream("/words.txt");
		  StringBuffer sb = new StringBuffer();
		  int dicI = 0;
		  
		  try
		  {
			  int chars, i = 0;
			 
			  while ((chars = is.read()) != -1)
			  {
				  sb.append((char) chars);
				  
				  if(chars == '\n')
				  {
					  sb.delete(sb.toString().length() - 2,  sb.toString().length());
					  dictionary[dicI] = sb.toString();
					  dicI++;
					  sb.delete(0, sb.toString().length());
				  }
			  }
		  }
		  catch (Exception e)
		  {
			  
		  }
	 }
	 
	 private void newGame()
	 {
		 answerWord = handler.chooseWord(dictionary);
		 knownWord = "";
		 
		 for(int i = 0; i < answerWord.length(); i++)
		 {
			 knownWord = knownWord + "_";
		 }
		 
		 mainPage.setKnownWord(knownWord);
		 
		 mainPage.addCommand(guessLetter);
		 mainPage.addCommand(solve);
		 mainPage.addCommand(newGameCommand);
		 mainPage.addCommand(exit);
		 mainPage.setCommandListener(this);
		 mainPage.resetLayers();
		 mainPage.resetNumWrong();
		 mainPage.setLabel("To guess a letter, go to menu.");
		 
		 mainPage.repaint();
		 
//		 System.out.println(answerWord);
//		 System.out.println(knownWord);
	 }
	 
	 private void enterEndState()
	 {
		 mainPage.removeCommand(guessLetter);
		 mainPage.removeCommand(solve);
	 }
	 
	 private void checkWin(String word)
	 {
		 if(word.equals(answerWord))
		 {
			 mainPage.setLabel("YOU WIN!!");
			 enterEndState();
		 }
	 }

	 private void checkLose()
	 {
		if(mainPage.getNumWrong() == 7)
		{
			mainPage.setLabel("YOU LOSE!! Answer: " + answerWord);
			enterEndState();
		}
	 }
}
