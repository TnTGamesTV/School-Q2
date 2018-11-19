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
package de.finn_tegeler.developing.school.compiler;

import de.finn_tegeler.developing.school.compiler.util.IdentifiedToken;
import de.finn_tegeler.developing.school.compiler.util.RawToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Finn Tegeler
 */
public class IdentificationManager {
	
	private static final Map<String, String> CONST_MAP = new HashMap<>();
	
	public static void init() {
		CONST_MAP.put("void", "VOID");
		CONST_MAP.put("int", "INT");
		CONST_MAP.put("return", "RTN");
		CONST_MAP.put("if", "IF");
		CONST_MAP.put("else", "ELSE");
		/* s chars */
		CONST_MAP.put("(", "LPAR");
		CONST_MAP.put(")", "RPAR");
		CONST_MAP.put("{", "LBR");
		CONST_MAP.put("}", "RBR");
		CONST_MAP.put("=", "EQL");
		CONST_MAP.put("+", "PLS");
		CONST_MAP.put(";", "SC");
	}
	
	private List<RawToken> _rawTokens;
	
	public IdentificationManager(List<RawToken> rawTokens) {
		this._rawTokens = rawTokens;
	}
	
	private IdentifiedToken _identify(RawToken rawToken) {
		if (CONST_MAP.containsKey(rawToken.getContent())) {
			return new IdentifiedToken(rawToken, CONST_MAP.get(rawToken.getContent()));
		} else if (_isInteger(rawToken.getContent())) {
			return new IdentifiedToken(rawToken, "NUM");
		} else if (_isIdentifier(rawToken.getContent())) {
			return new IdentifiedToken(rawToken, "ID");
		} else {
			System.err.println("ERROR WITH TOKEN '" + rawToken.getContent() + "'");
			return null;
		}
	}
	
	private boolean _isIdentifier(String input) {
		return input.matches("[a-zA-Z_]+.*");
	}
	
	private boolean _isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public List<IdentifiedToken> getIdentifiedTokens() {
		return _rawTokens.stream().map((e) -> _identify(e)).collect(Collectors.toList());
	}
}
