import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDletStateChangeException;

public class mainCanvas extends Canvas
{
	private Image[] hangmanPics;
	private String knownWord, drawWord;
	private String[] guessedLetters = new String[26];
	private int guessedLettersIndex = 0;
	private String[] drawLetters = new String[3];
	private int layers = 1;
	private String label;
	private int numWrong = 0;
	
	public mainCanvas(Image[] pics)
	{
		hangmanPics = pics;
		drawLetters[0] = "";
		drawLetters[1] = "";
		drawLetters[2] = "";
		label = "To guess a letter, go to menu.";
	}
	
	protected void paint(Graphics g)
	{
		
		int x = this.getWidth() / 4 + 20;
		int y = this.getHeight() / 2;
		
		g.setColor(255, 255, 255);
		g.fillRect(0, 0, 500, 500);
		g.drawImage(hangmanPics[numWrong], x - 80, y - 50, 0);
		
		Font font = Font.getFont(32, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
		g.setFont(font);
		g.setColor(0, 0, 0);
		
		g.drawString(drawWord, x - 70, this.getHeight() - 25, 0);
		
		if(layers == 1)
		{
			g.drawString(drawLetters[0], 5, 5, 0);
		}
		
		if(layers == 2)
		{
			g.drawString(drawLetters[0], 5, 5, 0);
			g.drawString(drawLetters[1], 5, 15, 0);
		}
		
		else
		{
			g.drawString(drawLetters[0], 5, 5, 0);
			g.drawString(drawLetters[1], 5, 15, 0);
			g.drawString(drawLetters[2], 5, 25, 0);
		}
		
		g.drawString(label, 5, y - 65, 0);
	}
	
	public void setKnownWord(String word)
	{
		knownWord = word;
		createDrawWord();
	}
	
	public void createDrawWord()
	{
		drawWord = "";
		for(int i = 0; i < knownWord.length(); i++)
		{
			drawWord = drawWord + knownWord.substring(i, i + 1) + " ";
		}
	}
	
	public String getDrawWord()
	{
		return drawWord;
	}
	
	public void addGuessedLetter(String letter)
	{
		guessedLetters[guessedLettersIndex] = letter;
		guessedLettersIndex++;
		
		drawLetters[0] = "Guessed Letters: ";
		drawLetters[1] = "";
		drawLetters[2] = "";
		
		for(int i = 0; i < guessedLettersIndex; i++)
		{
			if( i <= 5)
			{
				drawLetters[0] += guessedLetters[i] + ", ";
			}
			
			else if(i <= 16)
			{
				layers = 2;
				drawLetters[1] += guessedLetters[i] + ", ";
			}
			
			else
			{
				layers = 3;
				drawLetters[2] += guessedLetters[i] + ", ";
			}
		}
		
		if(layers == 1)
		{
			drawLetters[0] = drawLetters[0].substring(0, drawLetters[0].length() - 2);
		}
		
		else if(layers == 2)
		{
			drawLetters[1] = drawLetters[1].substring(0, drawLetters[1].length() - 2);
		}
		
		else if(layers == 3)
		{
			drawLetters[2] = drawLetters[2].substring(0, drawLetters[2].length() - 2);
		}
	}
	
	public void resetLayers()
	{
		layers = 1;
		drawLetters[0] = "";
		drawLetters[1] = "";
		drawLetters[2] = "";
		guessedLettersIndex = 0;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public void addWrong()
	{
		numWrong++;
	}
	
	public void resetNumWrong()
	{
		numWrong = 0;
	}
	
	public int getNumWrong()
	{
		return numWrong;
	}
	
	public boolean alreadyGuessed(String letter)
	{
		for(int i = 0; i < guessedLettersIndex; i++)
		{
			if(guessedLetters[i].equals(letter))
			{
				return true;
			}
		}
		
		return false;
	}
}