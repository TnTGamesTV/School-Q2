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
package de.finn_tegeler.developing.school.modules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Finn Tegeler
 */
public class TokenManager {
	
	private static Map<String, Token> tokenMapping = new HashMap<>();
	
	private static Token _self(String id) {
		return new SelfToken(id);
	}
	
	private static TokenSequence _sequence(Token... tokens) {
		return new TokenSequence(Arrays.asList(tokens));
	}
	
	public static Token getTokenById(String id) {
		if (tokenMapping.containsKey(id)) {
			return tokenMapping.get(id);
		}
		return null;
	}
	
	public static Map<String, Token> getTokenMapping() {
		return tokenMapping;
	}
	
	public static List<Token> getTokens() {
		return tokenMapping.values().stream().collect(Collectors.toList());
	}
	
	public static void init() {
		Token semicolonToken = new TerminalToken(";");
		/**/
		Token identifierToken = new IdentifierToken();
		Token intTypeToken = new TerminalToken("int");
		/**/
		Token typeToken = new NTerminalToken(_sequence(intTypeToken), _sequence(identifierToken));
		/**/
		Token equalToken = new TerminalToken("=");
		Token addToken = new TerminalToken("+");
		Token subtractToken = new TerminalToken("-");
		Token multiplyToken = new TerminalToken("*");
		Token divideToken = new TerminalToken("/");
		Token integerToken = new IntegerToken();
		/**/
		Token arithmeticOperationToken = new NTerminalToken(_sequence(addToken), _sequence(subtractToken),
		        _sequence(multiplyToken), _sequence(divideToken));
		/**/
		Token expressionToken = new NTerminalToken(
		        _sequence(typeToken, identifierToken, equalToken, _self("expressionToken"), semicolonToken),
		        _sequence(_self("expressionToken"), arithmeticOperationToken, _self("expressionToken")),
		        _sequence(integerToken), _sequence(identifierToken));
		/**/
		Token voidToken = new TerminalToken("void");
		Token openArgumentToken = new TerminalToken("(");
		Token closingArgumentToken = new TerminalToken(")");
		Token openCurlyBracketToken = new TerminalToken("{");
		Token closingCurlyBracketToken = new TerminalToken("}");
		Token methodToken = new NTerminalToken(_sequence(voidToken, identifierToken, openArgumentToken,
		        closingArgumentToken, openCurlyBracketToken, expressionToken, closingCurlyBracketToken));
		/**/
		Token rootToken = new NTerminalToken(_sequence(methodToken));
		/**/
		register("semicolonToken", semicolonToken);
		register("identifierToken", identifierToken);
		register("intTypeToken", intTypeToken);
		register("typeToken", typeToken);
		register("equalToken", equalToken);
		register("addToken", addToken);
		register("subtractToken", subtractToken);
		register("multiplyToken", multiplyToken);
		register("divideToken", divideToken);
		register("integerToken", integerToken);
		register("arithmeticOperationToken", arithmeticOperationToken);
		register("expressionToken", expressionToken);
		register("voidToken", voidToken);
		register("openArgumentToken", openArgumentToken);
		register("closingArgumentToken", closingArgumentToken);
		register("openCurlyBracketToken", openCurlyBracketToken);
		register("closingCurlyBracketToken", closingCurlyBracketToken);
		register("methodToken", methodToken);
		register("rootToken", rootToken);
	}
	
	private static void register(String id, Token token) {
		if (!tokenMapping.containsKey(id)) {
			tokenMapping.put(id, token);
		}
	}
}
