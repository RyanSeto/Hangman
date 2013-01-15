import java.util.Random;


public class WordHandler 
{
	Random rand = new Random();
	
	public WordHandler()
	{

	}
	
	public String chooseWord(String[] wordList)
	{
		int x = rand.nextInt(19912);
		return wordList[x];
	}
	
	public String checkWord(String answerWord, String guess, String knownWord)
	{
		String word = knownWord;
		
		for(int i = 0; i < answerWord.length(); i++)
		{
			if(answerWord.substring(i, i + 1).equals(guess))
			{
				String temp = word.substring(0, i);
				temp = temp + guess;
				temp = temp + word.substring(i + 1, word.length());
				word = temp;
			}
		}
		return word;
	}
}
