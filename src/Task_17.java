import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Task_17 {
	public static void main(String[] args) {
		final String outputPath = "OUTPUT.TXT";
		final Sectors test = new Sectors();
		try(final BufferedWriter output = Files.newBufferedWriter(Paths.get(outputPath))) {
			output.write(test.toString());
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//-----------------------------------------------------------------------------
/*public*/ class Sectors
{
	//-----------------------------------------------------------------------------fields
	private final int numbersOfDigits;
	private final String digits;
	//-----------------------------------------------------------------------------constructors
	/*public*/ private Sectors(final String path)
	{
		int mirrorNumbersOfDigits = 0;
		String mirrorDigits = "";
		try(final BufferedReader input = Files.newBufferedReader(Paths.get(path))) {
			//-----------------------------------------------------------------------------
			if(input.ready()) {
				//-----------------------------------------------------------------------------
				String readeredString = input.readLine(); //read data from file
				String regex = "\\d+";
				if(readeredString.matches(String.format("%s", regex))) {
					mirrorNumbersOfDigits = Integer.valueOf(readeredString);
				}
				else {
					throw new NoSuchElementException("Incorrect numbersOfDigits!");
				}
				//-----------------------------------------------------------------------------
				readeredString = input.readLine(); //read data from file
				if(!(this.isCorrectString(readeredString))) {
					throw new NoSuchElementException("Incorrect digits!");
				}
				mirrorDigits = readeredString;
				//-----------------------------------------------------------------------------
			}
			//-----------------------------------------------------------------------------
			else {
				throw new IOException("File is empty!");
			}
			//-----------------------------------------------------------------------------
		}catch (IOException | NoSuchElementException e) {
			e.printStackTrace();
		}
		//-----------------------------------------------------------------------------
		this.numbersOfDigits = mirrorNumbersOfDigits;
		this.digits = mirrorDigits;
		//-----------------------------------------------------------------------------
	}

	/*public*/ Sectors()
	{
		this("INPUT.TXT");
	}
	//-----------------------------------------------------------------------------methods for constructors
	private boolean isCorrectString(final String s) {
		String regex = " ";
		final String[] splittedS = s.split(String.format("%s", regex));
		regex = "\\d+";
		for(String tempS : splittedS) {
			if(!(tempS.matches(String.format("%s", regex)))) {
				return false;
			}
		}
		return true;
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	private int findMinNumbersOfSectors()
	{
		final String splittedDigits[] = this.digits.split(" ");
		ArrayList<Integer> digitsArrayList = new ArrayList<>();
		for(String tempDigit : splittedDigits) {
			digitsArrayList.add(Integer.valueOf(tempDigit));
		}
		//-----------------------------------------------------------------------------
		ArrayList<Integer> prefixArrayList = new ArrayList<>();
		for (int numberOfDigit = 0; numberOfDigit < this.numbersOfDigits; numberOfDigit++) {
			prefixArrayList.add(0);
		}
		//-----------------------------------------------------------------------------
		//-----------------------------------------------------------------------------prefix function from the Internet
		int prefixElement = 0;
		for (int numberOfDigit = 1; numberOfDigit < this.numbersOfDigits; numberOfDigit++)
		{
			while ((prefixElement > 0) &&
					(digitsArrayList.get(prefixElement).compareTo(digitsArrayList.get(numberOfDigit)) != 0)) {
				prefixElement = prefixArrayList.get(prefixElement - 1);
			}
			if (digitsArrayList.get(prefixElement).compareTo(digitsArrayList.get(numberOfDigit)) == 0) {
				prefixElement++;
			}
			prefixArrayList.set(numberOfDigit, prefixElement);
		}
		//-----------------------------------------------------------------------------
		int possibleNumbers = this.numbersOfDigits - prefixElement;
		if(((this.numbersOfDigits - 1) % possibleNumbers) == 0) {
			return possibleNumbers;
		}
		else {
			return (this.numbersOfDigits - 1);
		}
	}

	@Override
	public String toString()
	{
		return String.valueOf(this.findMinNumbersOfSectors());
	}
}