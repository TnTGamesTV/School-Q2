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
package de.finn_tegeler.developing.school.compiler;

import de.finn_tegeler.developing.school.compiler.util.RawToken;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Finn Tegeler
 */
public class Tokenizer {
	
	public static final Character	CONST_NEWLINE	= _c('\n');
	public static final Character	CONST_SPACE		= _c(' ');
	
	private static Character _c(char c) {
		return new Character(c);
	}
	
	private int								_character		= 0;
	private List<Character>					_characters;
	/**
	 * 0 => [a-zA-Z0-9]
	 * 1 => CONST_ATTR_GROUP
	 * 2 => CONST_BODY_GROUP
	 */
	private int								_currentGroup	= -1;
	private List<Character>					_currentToken;
	private Character						_lastCharacter;
	private int								_line			= 1;
	private Map<Integer, List<Character>>	_mapping		= new HashMap<>();
	private List<RawToken>					_rawTokens;
	private List<Character>					_specialCharacters;
	
	public Tokenizer(String input) {
		this._characters = new ArrayList<>();
		input.chars().forEach((c) -> {
			this._characters.add(new Character((char) c));
		});
		this._rawTokens = new ArrayList<>();
		this._mapping.put(0, Arrays.asList(_c('(')));
		this._mapping.put(1, Arrays.asList(_c(')')));
		this._mapping.put(2, Arrays.asList(_c('{')));
		this._mapping.put(3, Arrays.asList(_c('}')));
		this._mapping.put(4, Arrays.asList(_c(';')));
		this._specialCharacters = new ArrayList<>();
		this._mapping.values().forEach((list) -> {
			this._specialCharacters.addAll(list);
		});
	}
	
	private void _addToCurrentToken(Character c) {
		_currentToken.add(c);
	}
	
	private void _bakeToken() {
		StringBuilder builder = new StringBuilder();
		_currentToken.forEach((c) -> builder.append(c.toString()));
		RawToken rawToken = new RawToken(builder.toString(), _line, _character);
		_rawTokens.add(rawToken);
	}
	
	private void _resetAfterNewToken() {
		_currentToken.clear();
	}
	
	private void _switchCurrentGroup(Character c) {
		if (_specialCharacters.contains(c)) {
			_mapping.entrySet().stream().filter((entry) -> entry.getValue().contains(c)).forEach((entry) -> {
				int index = entry.getKey();
				_currentGroup = index;
			});
		} else {
			_currentGroup = -1;
		}
	}
	
	private void _tokenize() {
		_currentToken = new ArrayList<>();
		_characters.forEach((Character c) -> {
			if (c.equals(CONST_NEWLINE)) {
				_bakeToken();
				_resetAfterNewToken();
				_line++;
				_character = 0;
			} else if (c.equals(CONST_SPACE)) {
				if (_currentToken.size() > 0) { // If this is the first space
					_bakeToken(); // Create token
					_resetAfterNewToken();
				}
			} else {
				if (_specialCharacters.contains(c)) { // If the character matches any special characters
					if (_mapping.containsKey(_currentGroup)) { // And the current group is special
						List<Character> currentGroup = _mapping.get(_currentGroup); // get current group
						if (currentGroup.contains(c)) { // if the special char is in that special group
							// Same token
							_addToCurrentToken(c); // just add it
						} else {
							_bakeToken(); // otherwise start a new token
							_switchCurrentGroup(c); // switch group
							_resetAfterNewToken(); // reset
							_addToCurrentToken(c); // and add special char to new token
						}
					} else {
						// If the current group is default (anything) and the current char is special
						_switchCurrentGroup(c); // Switch to detected group
						_bakeToken(); // Create token
						_resetAfterNewToken(); // Clear list
						_addToCurrentToken(c); // Add current token to empty list
					}
					if (!_specialCharacters.contains(_lastCharacter)) {
						_bakeToken();
						_resetAfterNewToken();
					}
				} else {
					// Anything that is not special
					_addToCurrentToken(c);
				}
			}
			_character++;
			_lastCharacter = c;
		});
	}
	
	public List<RawToken> getTokens() {
		if (_rawTokens.size() == 0) {
			_tokenize();
			_rawTokens = _rawTokens.stream().filter((rawToken) -> rawToken.getContent().length() > 0)
			        .collect(Collectors.toList());
		}
		return _rawTokens;
	}
}
