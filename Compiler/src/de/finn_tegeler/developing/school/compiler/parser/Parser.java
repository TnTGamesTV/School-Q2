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

import de.finn_tegeler.developing.school.compiler.parser.structure.ArithmeticExpressionDefinition;
import de.finn_tegeler.developing.school.compiler.util.IdentifiedToken;

import java.util.List;

/**
 * @author Finn Tegeler
 */
public class Parser {
	
	private List<IdentifiedToken> _identifiedTokens;
	
	public Parser(List<IdentifiedToken> identifiedTokens) {
		this._identifiedTokens = identifiedTokens;
	}
	
	public void parse() {
		// List<Definition> parsingStructure = ParsingStructureSupplier.supply();
	}
	
	public ArithmeticExpressionDefinition parseArithmeticExpression(ParsingData data) {
		if (!data.checkDepth()) return null;
		if (!data.isTokenEqual("NUM")) return null;
		int num = ParserUtil.getNumber(data.getCurrentToken().getRawToken().getContent());
		data.nextToken();
		if (!data.isTokenEqual("PLS")) return null;
		String operator = "+";
		return null;
	}
}
