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
package de.finn_tegeler.developing.school.compiler.parser.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Finn Tegeler
 */
public class ExpressionDefinition extends SubFunctionDefinition {
	
	public static ExpressionDefinition fromUp(ExpressionDefinition oldExpressionDefinition,
	        ExpressionPart expressionPart) {
		List<ExpressionPart> parts = new ArrayList<>();
		parts.add(expressionPart);
		parts.addAll(oldExpressionDefinition._expression);
		return new ExpressionDefinition(parts);
	}
	
	private List<ExpressionPart> _expression;
	
	public ExpressionDefinition(ExpressionPart expressionPart) {
		_expression = new ArrayList<>();
		_expression.add(expressionPart);
	}
	
	public ExpressionDefinition(List<ExpressionPart> expressionParts) {
		_expression = expressionParts;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		_expression.forEach((e) -> {
			builder.append("\n" + "<ExpressionPart>" + e.getValue() + "</ExpressionPart>");
			if (e.getOperator() != "EOE")
			    builder.append("\n<ExpressionOperator>" + e.getOperator() + "</ExpressionOperator");
		});
		return builder.toString();
	}
}
