
//package in.ac.iitb.cfilt.jhwnl.examples;

import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Pointer;
import in.ac.iitb.cfilt.jhwnl.data.PointerType;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.data.POS;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class GetSynonymsHindi {
	
	static void demonstration() {

		BufferedReader inputWordsFile = null;
		try {
			inputWordsFile = new BufferedReader(new InputStreamReader (new FileInputStream ("inputwords.txt"), "UTF8"));
		} catch( FileNotFoundException e){
			System.err.println("Error opening input words file.");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.err.println("UTF-8 encoding is not supported.");
			System.exit(-1);
		}
		JHWNL.initialize();
		String inputLine;
		long[] synsetOffsets;
		inputLine = "और";
		try {
			//while((inputLine = inputWordsFile.readLine()) != null){
			while((inputLine != null)){
					System.out.println("\n" + inputLine);
				//	 Look up the word for all POS tags
				//System.out.println("Line is printed ");
				IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(inputLine.trim());
				//	 Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
				//System.out.println("demoIWSet is "+demoIWSet.size());
				IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
				demoIndexWord  = demoIWSet.getIndexWordArray();
				for ( int i = 0;i < demoIndexWord.length;i++ ) {
					int size = demoIndexWord[i].getSenseCount();
					//System.out.println("Sense Count is " + size);
					synsetOffsets = demoIndexWord[i].getSynsetOffsets();
					/*for ( int k = 0 ;k < size; k++ ) {
						//System.out.println("Offsets[" + k +"] " + synsetOffsets[k]);
					}*/

					Synset[] synsetArray = demoIndexWord[i].getSenses();

					for ( int k = 0;k < size;k++ ) {
						System.out.println("Synset [" + k +"] array is "+ synsetArray[k].getWords());
						//System.out.println("Synset POS: " + synsetArray[k].getPOS());
						Pointer[] pointers = synsetArray[k].getPointers();
						//System.out.println("Synset Num Pointers:" + pointers.length);
						/*for (int j = 0; j < pointers.length; j++) {
							if(pointers[j].getType().equals(PointerType.ONTO_NODES)) {	// For ontology relation
								System.out.println("onto nodes");
								System.out.println(pointers[j].getType() + " : "  + Dictionary.getInstance().getOntoSynset(pointers[j].getOntoPointer()).getOntoNodes());
							} else {
								if (pointers[j].getType().equals(PointerType.TROPONYM)){
									System.out.println("Synonym set is ");
									System.out.println(pointers[j].getType() + " : "  + Dictionary.getInstance().getSynsetAt(POS.NOUN, pointers[j].getOntoPointer()).getWords().toString());
								}
								else {
									//System.out.println(pointers[j].getType() + " : " + pointers[j].getTargetSynset());
								}
							}							
						}*/
						
					}
				}
				inputLine = null;
			}
		}
		catch (Exception e)
		{
			System.err.println("Error in input/output.");			
			e.printStackTrace();
		}
		/*catch (JHWNLException e) {
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		}*/
	}
	
	public static void main(String args[]) throws Exception {
		demonstration();
	}
}
