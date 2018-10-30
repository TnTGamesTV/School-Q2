/**
 * 
 */
package de.finn_tegeler.developing.school;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TnTGamesTV
 * Project: Compiler
 * Date: 30-10-2018
 */
public class Input {

	public static Character CONST_SPACE = ' ';
	public static Character CONST_EQUAL = '=';
	
	public Input(String input) {
		this.input = input;
	}
	
	private String input;
	
	private int _position = 0;
	
	private List<Character> _chars;
	
	public void handle(String input) {
		_chars = new ArrayList<Character>();
		for(char c : input.toCharArray()) {
			_chars.add(c);
		}
		
		//read four chars
		
		//read space(s)
		
		//read chars till "("

		//read chars till ")"
		
		//read spaces
	}
	
	private void _skipSpaces() throws IOException {
		while(_chars.get(_position) == CONST_SPACE) {
			_position++;
		}
		
		
	}
}
