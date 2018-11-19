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
package de.finn_tegeler.developing.school.compiler.parser;

import de.finn_tegeler.developing.school.compiler.util.IdentifiedToken;

import java.util.List;

/**
 * @author Finn Tegeler
 */
public class ParsingData {
	
	public static final int CONST_MAXIMUM_DEPTH = 100;
	
	public static ParsingData down(ParsingData oldParserData) {
		return new ParsingData(oldParserData._identifiedTokens, oldParserData._currentIndex, oldParserData._depth + 1);
	}
	
	public static ParsingData fromRoot(List<IdentifiedToken> identifiedTokens) {
		return new ParsingData(identifiedTokens, 0, 0);
	}
	
	private int						_currentIndex;
	private int						_depth;
	private List<IdentifiedToken>	_identifiedTokens;
	private int						_startIndex;
	
	public ParsingData(List<IdentifiedToken> _identifiedTokens, int startIndex, int depth) {
		this._identifiedTokens = _identifiedTokens;
		this._startIndex = startIndex;
		this._currentIndex = startIndex;
		this._depth = depth;
	}
	
	public boolean checkDepth() {
		return _depth < CONST_MAXIMUM_DEPTH;
	}
	
	public int getCurrentIndex() {
		return _currentIndex;
	}
	
	public IdentifiedToken getCurrentToken() {
		return _identifiedTokens.get(_currentIndex);
	}
	
	public boolean isTokenEqual(String requiredGroup) {
		return requiredGroup.equals(this.getCurrentToken().getGroup());
	}
	
	public void nextToken() {
		if (_identifiedTokens.size() == ++_currentIndex) {
			_currentIndex--;
		}
	}
	
	public void resetIndex() {
		this._currentIndex = _startIndex;
	}
}
