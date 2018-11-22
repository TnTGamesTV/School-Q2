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

import de.finn_tegeler.developing.school.compiler.parser.structure.*;
import de.finn_tegeler.developing.school.compiler.util.IdentifiedToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Finn Tegeler
 */
public class Parser {
	
	private List<IdentifiedToken> _identifiedTokens;
	
	public Parser(List<IdentifiedToken> identifiedTokens) {
		this._identifiedTokens = identifiedTokens;
	}
	
	private void _error(ParsingData data, String error) {
		ErrorManager.addError(data, error);
	}
	
	private boolean _lookAhead(ParsingData data, String requiredGroup) {
		return data.getCurrentToken().getGroup().equals(requiredGroup);
	}
	
	public void outputError(ParsingData data) {
		String error = ErrorManager.getError(data);
		if (error.length() > 0) {
			System.out.println(ErrorManager.getFormattedError(data));
		}
	}
	
	public Definition parse() {
		ParsingData rootData = ParsingData.fromRoot(_identifiedTokens);
		FunctionDefinition functionDefinition = parseFunction(rootData);
		if (functionDefinition == null) {
			outputError(rootData);
		}
		return functionDefinition;
	}
	
	public ArgumentDefinition parseArgument(ParsingData data) {
		if (!data.checkDepth()) return null;
		if (_lookAhead(data, "TYPE")) {
			TypeDefinition type = new TypeDefinition(data.getCurrentToken().getRawToken().getContent());
			data.nextToken();
			if (_lookAhead(data, "ID")) {
				String name = data.getCurrentToken().getRawToken().getContent();
				data.nextToken();
				data.updateParent();
				return new ArgumentDefinition(type, name);
			} else {
				_error(data, "Missing identifier of argument");
			}
		} else {
			_error(data, "Missing type of argument");
		}
		return null;
	}
	
	public List<ArgumentDefinition> parseArguments(ParsingData data) {
		if (!data.checkDepth()) return null;
		List<ArgumentDefinition> arguments = new ArrayList<>();
		while (!_lookAhead(data, "RPAR")) {
			ArgumentDefinition argument = parseArgument(ParsingData.down(data, 0));
			if (argument != null) {
				arguments.add(argument);
			}
		}
		data.updateParent();
		return arguments;
	}
	
	private BooleanExpressionDefinition parseBooleanExpression(ParsingData data) {
		if (!data.checkDepth()) return null;
		if (_lookAhead(data, "TRUE")) {
			data.nextToken();
			data.updateParent();
			return new BooleanExpressionDefinition(true);
		} else if (_lookAhead(data, "FALSE")) {
			data.nextToken();
			data.updateParent();
			return new BooleanExpressionDefinition(false);
		} else if (_lookAhead(data, "ID")) {
			String identifier = data.getCurrentToken().getRawToken().getContent();
			if (TableManager.isIdentifierKnown(data.getScopeLevel(),
			        identifier) == TableManager.CONST_RESULT_GET_SUCCESS_TRUE) {
				data.nextToken();
				if (_lookAhead(data, "BEQL")) {
					data.nextToken();
					if (_lookAhead(data, "TRUE")) {
						data.nextToken();
						data.updateParent();
						return new BooleanExpressionDefinition(true,
						        TableManager.getIdentifier(data.getScopeLevel(), identifier));
					} else if (_lookAhead(data, "FALSE")) {
						data.nextToken();
						data.updateParent();
						return new BooleanExpressionDefinition(false,
						        TableManager.getIdentifier(data.getScopeLevel(), identifier));
					} else {
						_error(data, "Wrong value assigned. Either 'true' or 'false'");
						data.updateParentError();
					}
				} else {
					_error(data, "Missing boolean equal sign");
					data.updateParentError();
				}
			} else {
				_error(data, "Variable was never declared");
				data.updateParentError();
			}
		} else {
			_error(data, "Wrong format of boolean expression");
			data.updateParentError();
		}
		return null;
	}
	
	public DeclarationDefinition parseDeclaration(ParsingData data) {
		if (!data.checkDepth()) return null;
		if (_lookAhead(data, "INT")) {
			String type = "int";
			data.nextToken();
			if (_lookAhead(data, "ID")) {
				String id = data.getCurrentToken().getRawToken().getContent();
				if (TableManager.isIdentifierKnown(data.getScopeLevel(),
				        id) == TableManager.CONST_RESULT_GET_SUCCESS_FALSE) {
					data.nextToken();
					if (_lookAhead(data, "EQL")) {
						data.nextToken();
						ExpressionDefinition expression = parseExpression(ParsingData.down(data, 0));
						if (expression != null) {
							data.nextToken();
							data.updateParent();
							VariableDefinition variable = new VariableDefinition(type, id, expression);
							return new DeclarationDefinition(variable, expression);
						} else {
							_error(data, "Missing expression");
							data.updateParentError();
						}
					} else {
						_error(data, "Missing equal sign");
						data.updateParentError();
					}
				} else {
					_error(data, "This variable has already been declared");
					data.updateParentError();
				}
			} else {
				_error(data, "Missing name of declaration");
				data.updateParentError();
			}
		} else {
			_error(data, "Missing type of declaration");
			data.updateParentError();
		}
		return null;
	}
	
	public ExpressionDefinition parseExpression(ParsingData data) {
		if (!data.checkDepth()) return null;
		if (_lookAhead(data, "NUM")) {
			int num = ParserUtil.getNumber(data.getCurrentToken().getRawToken().getContent());
			data.nextToken();
			if (_lookAhead(data, "PLS") || _lookAhead(data, "MNS") || _lookAhead(data, "MLT")
			        || _lookAhead(data, "DIV")) {
				data.nextToken();
				String operator = _lookAhead(data, "PLS") ? "+"
				        : _lookAhead(data, "MNS") ? "-" : _lookAhead(data, "MLT") ? "*" : "/";
				ExpressionDefinition expressionDefinition = ExpressionDefinition
				        .fromUp(parseExpression(ParsingData.down(data, 0)), new ExpressionPart(num, operator));
				if (expressionDefinition != null) {
					data.updateParent();
					return expressionDefinition;
				}
			} else if (_lookAhead(data, "SC")) {
				data.updateParent();
				return new ExpressionDefinition(ExpressionPart.asLast(num));
			} else {
				_error(data, "Unexpected end of expression (missing semicolon)");
			}
		} else {
			_error(data, "Missing int for expression");
		}
		return null;
	}
	
	public FunctionDefinition parseFunction(ParsingData data) {
		if (!data.checkDepth()) return null;
		if (_lookAhead(data, "VOID")) {
			TypeDefinition returnType = new TypeDefinition(data.getCurrentToken().getRawToken().getContent());
			data.nextToken();
			if (_lookAhead(data, "ID")) {
				String name = data.getCurrentToken().getRawToken().getContent();
				data.nextToken();
				if (_lookAhead(data, "LPAR")) {
					data.nextToken();
					List<ArgumentDefinition> arguments = parseArguments(ParsingData.down(data, 0));
					if (arguments != null) {
						data.nextToken();
						if (_lookAhead(data, "LBR")) {
							data.nextToken();
							List<SubFunctionDefinition> subDefinitions = parseSubFunctions(ParsingData.down(data, 0));
							if (subDefinitions != null) {
								data.nextToken();
								data.updateParent();
								return new FunctionDefinition(returnType, name, arguments, subDefinitions);
							}
						} else {
							_error(data, "Missing body of function");
							data.updateParentError();
						}
					}
				} else {
					_error(data, "Missing arguments body of function");
					data.updateParentError();
				}
			} else {
				_error(data, "Missing name of function");
				data.updateParentError();
			}
		} else {
			_error(data, "Missing return type of function");
			data.updateParentError();
		}
		return null;
	}
	
	private IfDeclarationDefinition parseIfDeclaration(ParsingData data) {
		if (!data.checkDepth()) return null;
		if (_lookAhead(data, "IF")) {
			data.nextToken();
			if (_lookAhead(data, "LPAR")) {
				data.nextToken();
				BooleanExpressionDefinition booleanExpression = parseBooleanExpression(ParsingData.down(data, 0));
				if (booleanExpression != null) {
					if (_lookAhead(data, "RPAR")) {
						data.nextToken();
						if (_lookAhead(data, "LBR")) {
							data.nextToken();
							List<SubFunctionDefinition> subDefinitions = parseSubFunctions(ParsingData.down(data, 1));
							data.nextToken();
							if (subDefinitions != null) {
								data.updateParent();
								return new IfDeclarationDefinition(booleanExpression, subDefinitions);
							}
						} else {
							_error(data, "Missing start of sub function");
							data.updateParentError();
						}
					} else {
						_error(data, "Missing end of boolean expression");
						data.updateParentError();
					}
				} else {
					data.updateParentError();
				}
			} else {
				_error(data, "Missing boolean expression in if statement");
				data.updateParentError();
			}
		} else {
			_error(data, "If statement does not start with this token");
			data.updateParentError();
		}
		return null;
	}
	
	public SubFunctionDefinition parseSubFunction(ParsingData data) {
		if (!data.checkDepth()) return null;
		SubFunctionDefinition subDefinition = parseDeclaration(ParsingData.down(data, 0));
		if (subDefinition == null) {
			data.resetIndex();
			subDefinition = parseIfDeclaration(ParsingData.down(data, 0));
		}
		if (subDefinition == null) {
			_error(data, "Could not parse any sub definition");
			data.updateParentError();
			return null;
		} else {
			data.updateParent();
			return subDefinition;
		}
	}
	
	public List<SubFunctionDefinition> parseSubFunctions(ParsingData data) {
		if (!data.checkDepth()) return null;
		List<SubFunctionDefinition> subDefinitions = new ArrayList<>();
		int safe = 0;
		while (!_lookAhead(data, "RBR")) {
			SubFunctionDefinition subDefinition = parseSubFunction(ParsingData.down(data, 0));
			if (subDefinition == null) {
				break;
			} else {
				subDefinitions.add(subDefinition);
			}
			safe++;
			if (safe > 100) {
				_error(data, "No end of sub function body");
				data.updateParentError();
				break;
			}
		}
		data.updateParent();
		return subDefinitions;
	}
}
