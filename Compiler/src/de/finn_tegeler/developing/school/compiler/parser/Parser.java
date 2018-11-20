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

import de.finn_tegeler.developing.school.compiler.parser.structure.DeclarationDefinition;
import de.finn_tegeler.developing.school.compiler.parser.structure.ExpressionDefinition;
import de.finn_tegeler.developing.school.compiler.parser.structure.ExpressionPart;
import de.finn_tegeler.developing.school.compiler.parser.structure.VariableDefinition;
import de.finn_tegeler.developing.school.compiler.util.IdentifiedToken;

import java.rmi.server.ExportException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Finn Tegeler
 */
public class Parser {
	
	private List<IdentifiedToken> _identifiedTokens;
	
	public Parser(List<IdentifiedToken> identifiedTokens) {
		this._identifiedTokens = identifiedTokens;
	}
	
	private boolean _lookAhead(ParsingData data, String requiredGroup) {
		return data.getCurrentToken().getGroup().equals(requiredGroup);
	}
	
	public void parse() {
		ParsingData rootData = ParsingData.fromRoot(_identifiedTokens);
		DeclarationDefinition def = parseDeclaration(rootData);
		
		if(def!= null) {
			System.out.println(def.toString());
		} else {
			System.err.println("NULL");
		}
	}
	
	public DeclarationDefinition parseDeclaration(ParsingData data) {
		if(!data.checkDepth()) return null;
		
		if(_lookAhead(data, "INT")) {
			String type = "int";
			data.nextToken();
			
			if(_lookAhead(data, "ID")) {
				String id = data.getCurrentToken().getRawToken().getContent();
				data.nextToken();
				
				if(_lookAhead(data, "EQL")) {
					data.nextToken();
					
					ExpressionDefinition expression = parseExpression(data);
					
					if(expression != null) {
						VariableDefinition variable = new VariableDefinition(type, id);
						return new DeclarationDefinition(variable, expression);
					}
				}
			}
		}
		return null;
	}
	
	public ExpressionDefinition parseExpression(ParsingData data) {
		if (!data.checkDepth()) return null;
		
		if(_lookAhead(data, "NUM")) {
			int num = ParserUtil.getNumber(data.getCurrentToken().getRawToken().getContent());
			data.nextToken();
			
			if(_lookAhead(data, "PLS")) {
				data.nextToken();
				return ExpressionDefinition.fromUp(parseExpression(ParsingData.down(data)), new ExpressionPart(num, "+"));
			} else if(_lookAhead(data, "SC")) {
				return new ExpressionDefinition(ExpressionPart.asLast(num));
			}
		}
		return null;
	}
}
