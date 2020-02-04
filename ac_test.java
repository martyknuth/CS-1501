import java.util.*;
import java.io.*;
public class ac_test
{
	public static void main(String args[]) throws Exception
	{
		//creates new DLB with dictionary.txt
		Dlb_Trie dictionary = new Dlb_Trie("dictionary.txt");
		//creates new frequencyTrie with nothing in it
		FrequencyTrie user_history = new FrequencyTrie();
		Scanner scan = new Scanner(System.in);
		String word = "";
		//predictions outputted to the user
		String[] predictions = new String[5];
		//total time searching
		double totalTime = 0;
		FileWriter fileWritter = new FileWriter("user_history.txt",true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		String[] pastWords;
		double numberOfpredictions = 0;
		while(true){
			System.out.print("\nEnter your first character: ");
			word = "";
			predictions = new String[5];
			while(!scan.hasNextInt())
			{
				String character = scan.nextLine();
				if(character.length()>1)
				{
					System.out.print("\nOnly one character, please!");
					System.out.print("\n\nPlease enter the next letter or the number for the word: ");
				}
				else
				{
					if(character.equals("!"))
					{
						//ends program when ! is inputted
						System.out.printf("\n\nAverage Time: %fs\nBye!",totalTime/(double)numberOfpredictions);
						bufferWritter.close();
						return;
					}
					if(character.equals("$"))
					{
						//ends word and puts it into user_history
						System.out.println("The word '" + word + "' has been entered to your history and will be suggested in the future!");
						user_history.put(word);
						System.out.print("\n\nPlease enter the first letter: ");
						word = "";
					}
					else
					{
						word = word + character;
						if(word.length()>0)
						{
							long startTime = System.nanoTime();		
							predictions = dictionary.search(word);
							pastWords = user_history.search(word);
							String[] combine = new String[5];
							int start = 0;
							for(int i = 0; i<5; i++)
							{
								if(pastWords.length > i && pastWords[i] != null)
								{
									combine[i] = pastWords[i];
								}
								else
								{
									combine[i] = predictions[start++];
								}
							}
							predictions = combine;
							long estimatedTime = (System.nanoTime() - startTime);
							double elapsedTimeInSecond = (double) estimatedTime / 1_000_000_000;
							numberOfpredictions++;
							totalTime = totalTime + elapsedTimeInSecond;				
							System.out.printf("\n( %fs )\n", elapsedTimeInSecond);
							System.out.println("Predictions:");
							for(int i = 0; i<predictions.length;i++)
							{
								int j = i + 1;
								if(predictions[i] != null)
								{
									System.out.print("(" + j + ")  " + predictions[i] + "     ");
								}
								else
								{
									System.out.print("(" + j + ")   NOT FOUND     ");
								}
							}
							System.out.print("\n\nPlease enter the next letter or the number for the word: ");
						}
					}
				}
			}
			int predicted = scan.nextInt();
			System.out.println("\nWord Completed: " + predictions[predicted-1] );
			if(predictions[predicted-1] != null)
			{
				bufferWritter.write(predictions[predicted-1]+"\n");
				user_history.put(predictions[predicted-1]);
			}
			System.out.printf("\nAverage Time: : %f\n", totalTime/(double)numberOfpredictions);
		}
	}
}