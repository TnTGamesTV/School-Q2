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
import java.util.List;

/**
 * @author Finn Tegeler
 */
public class NTerminalToken extends Token {
	
	private List<TokenSequence> _options;
	
	public NTerminalToken(TokenSequence... options) {
		this._options = Arrays.asList(options);
	}
	
	@Override
	public boolean matches(DataWrapper wrapper) {
		if (wrapper.in()) {
			for (TokenSequence sequence : _options) {
				wrapper.startRecording();
				boolean result = true;
				for (Token token : sequence.getOptions()) {
					if (!token.matches(wrapper)) { // If one token does not match in the sequence
						result = false; // This sequence does not match
						break; // And the next one will be tried
					}
				}
				if (!result) { // If the hole sequence failed
					int totalMoves = wrapper.getRecording();
					wrapper.stopRecording();
					wrapper.subIndex(totalMoves); // Return to start
				} else {
					// wrapper.nextRawToken(); // Otherwise prepare for next token check
					wrapper.out();
					return true;
				}
			}
			wrapper.out();
		}
		return false;
	}
}
