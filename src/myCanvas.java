import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class myCanvas extends Canvas
{
	private Image[] hangmanPics;
	//boolean  main = false;

	public myCanvas(Image[] pics)
	{
		hangmanPics = pics;
	}

	protected void paint(Graphics g) 
	{
		int x = this.getWidth() / 4 + 20;
		int y = this.getHeight() / 2;

			Font font = Font.getFont(32, Font.STYLE_BOLD, Font.SIZE_LARGE);
			
			g.setFont(font);
			g.drawString("HANGMAN", x, 40, 0);
			g.drawImage(hangmanPics[7], x - 25, y - 25, 0);
	}
}
