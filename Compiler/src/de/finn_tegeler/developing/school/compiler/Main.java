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

import de.finn_tegeler.developing.school.compiler.parser.ErrorManager;
import de.finn_tegeler.developing.school.compiler.parser.Parser;
import de.finn_tegeler.developing.school.compiler.parser.TableManager;
import de.finn_tegeler.developing.school.compiler.parser.structure.FunctionDefinition;

/**
 * @author Finn Tegeler
 */
public class Main {
	
	public static String	INPUT_INVALID	= "voi x() {\n" + "if(true){\n" + "int y = 2 + 3;\n" + "}\n int x = 3; \n"
	        + "}";
	public static String	INPUT_VALID		= "void x() {\n" + "if(true){\n" + "int y = 2 + 3;\n" + "}\n int x = 3; \n"
	        + "}";
	public static boolean	TEST_VALID		= false;
	
	private static void init() {
		IdentificationManager.init();
		TableManager.init();
		ErrorManager.init();
	}
	
	public static void main(String[] args) {
		init();
		TokenManager tokenManager = new TokenManager(TEST_VALID ? INPUT_VALID : INPUT_INVALID);
		tokenManager.start();
		Parser parser = new Parser(tokenManager.getIdentifiedTokens());
		FunctionDefinition functionDefinition = (FunctionDefinition) parser.parse();
		if (functionDefinition != null) {
			System.out.println("PARSING COMPLETED:");
			System.out.println(functionDefinition.toString());
		} else {
			System.out.println("PARSING FAILED!");
			parser.outputError(null);
		}
	}
}
