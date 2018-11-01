/*
 * Copyright (c) 2018, Finn Tegeler.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the Sun Microsystems, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.finn_tegeler.developing.school;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author TnTGamesTV Project: Compiler Date: 30-10-2018
 */
public class Input {

	public static String			AD					= "AD";
	public static List<Character>	CONST_COMMAND_ID	= Arrays.asList(' ', '(', '=');
	public static Character			CONST_EQUAL			= '=';
	public static List<String>		CONST_KNOWN_TYPES	= Arrays.asList("void", "int");
	public static Character			CONST_SPACE			= ' ';

	/**
	 * Transforms a list of characters into a string
	 *
	 * @param characters
	 *            the characters
	 * @return a string
	 */
	public static String getString(final List<Character> characters) {
		String content = "";
		for (final Character character : characters) {
			content += character.toString();
		}
		return content;
	}

	private List<Character>	_chars;
	private final int		_line		= 0;
	private int				_position	= 0;
	private final String	input;

	public Input(final String input) {
		this.input = input;
	}

	private Character _get() {
		if (this._position > -1 && this._position < this._chars.size()) {
			return this._chars.get(this._position);
		}
		return null;
	}

	private void _skipChars(final int amount) {
		this._position += amount;
	}

	private List<Character> _skipCharsAndSave(int amount) {
		final List<Character> skippedCharacters = new ArrayList<>();
		while (amount != 0) {
			skippedCharacters.add(this._get());
			amount--;
			this._position++;
		}
		return skippedCharacters;
	}

	private void _skipSpaces() {
		while (this._get() == Input.CONST_SPACE) {
			this._position++;
		}
	}

	private void _skipTillChar(final Character character) {
		while (this._get() != character) {
			this._position++;
		}
	}

	/**
	 * Skips all characters until it finds endCharacter and returns the skipped
	 * characters
	 *
	 * @param endCharacter
	 *            the character to end skipping at
	 * @return list of skipped characters
	 */
	private List<Character> _skipTillCharAndSave(final Character endCharacter) {
		final List<Character> skippedCharacters = new ArrayList<>();
		while (this._get() != endCharacter) {
			skippedCharacters.add(this._get());
			this._position++;
		}
		return skippedCharacters;
	}

	private List<Character> _skipTillCharsAndSave(final List<Character> endCharacters) {
		final List<Character> skippedCharacters = new ArrayList<>();
		while (!endCharacters.contains(this._get())) {
			skippedCharacters.add(this._get());
			this._position++;
		}
		return skippedCharacters;
	}

	public void handle() {
		this._chars = new ArrayList<>();
		for (final char c : this.input.toCharArray()) {
			this._chars.add(c);
		}
		// read four chars
		this._skipSpaces();
		final List<Character> functionType = this._skipCharsAndSave(4);
		this._skipSpaces();
		final List<Character> functionName = this._skipTillCharAndSave('(');
		// read chars till "("
		// read chars till ")"
		this._skipChars(1);
		// read spaces
		this._skipSpaces();
		// read '{'
		this._skipChars(1);
		// read first command
		final List<Character> firstCommandID = this._skipTillCharsAndSave(Input.CONST_COMMAND_ID);
		if (Input.CONST_KNOWN_TYPES.contains(Input.getString(firstCommandID))) {
			//
		}
		System.out.println("FunctionType: " + Input.getString(functionType));
		System.out.println("FunctionName: " + Input.getString(functionName));
	}
}
