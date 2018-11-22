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
package de.finn_tegeler.developing.school.compiler.parser;

import de.finn_tegeler.developing.school.compiler.parser.structure.ContentDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Finn Tegeler
 */
public class TableManager {
	
	public static final int										CONST_RESULT_ADD_ALREADY_DEFINED	= -2;
	public static final int										CONST_RESULT_ADD_SUCCESS			= 1;
	public static final int										CONST_RESULT_ADD_UNKNOWN_SCOPE		= -1;
	public static final int										CONST_RESULT_GET_SUCCESS_FALSE		= 0;
	public static final int										CONST_RESULT_GET_SUCCESS_TRUE		= 1;
	public static final int										CONST_RESULT_GET_UNKNOWN_SCOPE		= -1;
	public static Map<Integer, Map<String, ContentDefinition>>	mapping;
	
	public static int addIdentifier(int scopeLevel, String identifier, ContentDefinition definition) {
		if (mapping.containsKey(scopeLevel)) {
			if (!mapping.get(scopeLevel).containsKey(identifier)) {
				mapping.get(scopeLevel).put(identifier, definition);
				return CONST_RESULT_ADD_SUCCESS;
			} else {
				return CONST_RESULT_ADD_ALREADY_DEFINED;
			}
		} else {
			return CONST_RESULT_ADD_UNKNOWN_SCOPE;
		}
	}
	
	public static void addScopeLevel(int scopeLevel) {
		mapping.put(scopeLevel, new HashMap<>());
	}
	
	public static ContentDefinition getIdentifier(int scopeLevel, String identifier) {
		if (isIdentifierKnown(scopeLevel, identifier) == CONST_RESULT_GET_SUCCESS_TRUE) {
			return mapping.get(scopeLevel).get(identifier);
		} else {
			return null;
		}
	}
	
	public static void init() {
		mapping = new HashMap<>();
	}
	
	public static int isIdentifierKnown(int scopeLevel, String identifier) {
		if (mapping.containsKey(scopeLevel)) {
			if (mapping.get(scopeLevel).containsKey(identifier)) {
				return CONST_RESULT_GET_SUCCESS_TRUE;
			} else {
				return CONST_RESULT_GET_SUCCESS_FALSE;
			}
		}
		return CONST_RESULT_GET_UNKNOWN_SCOPE;
	}
}
