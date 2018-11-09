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
package de.finn_tegeler.developing.school;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import de.finn_tegeler.developing.school.modules.DataWrapper;
import de.finn_tegeler.developing.school.modules.Token;
import de.finn_tegeler.developing.school.modules.TokenManager;

/**
 * @author Finn Tegeler
 */
public class Scanner {
	
	private List<RawToken>	_allRawTokens;
	private StreamTokenizer	_tokenizer;
	private DataWrapper	wrapper;
	
	public Scanner(StreamTokenizer tokenizer) throws IOException {
		tokenizer.ordinaryChar('+');
		tokenizer.ordinaryChar('-');
		tokenizer.ordinaryChar('*');
		tokenizer.ordinaryChar('/');
		this._tokenizer = tokenizer;
		this._allRawTokens = new ArrayList<>();
		_init();
	}
	
	private void _addRawToken(String rawToken) {
		_allRawTokens.add(new RawToken(rawToken, _tokenizer.lineno(), 0));
	}
	
	private boolean _advance() throws IOException {
		return _tokenizer.ttype != StreamTokenizer.TT_EOF && _tokenizer.nextToken() != StreamTokenizer.TT_EOF;
	}
	
	private void _init() throws IOException {
		// Iterate over all rawTokens
		while (_advance()) {
			int type = _tokenizer.ttype;
			if (type == StreamTokenizer.TT_EOL) {
				continue;
			} else if (type == StreamTokenizer.TT_NUMBER) {
				_addRawToken("" + _tokenizer.nval);
			} else if (type == StreamTokenizer.TT_WORD) {
				_addRawToken(_tokenizer.sval);
			} else {
				_addRawToken(new Character((char) _tokenizer.ttype).toString());
			}
		}
	}
	
	public void check() {
		wrapper = new DataWrapper(_allRawTokens);
		Token methodToken = TokenManager.getTokenById("methodToken");
		System.out.println("Matches: " + methodToken.matches(wrapper));
	}
}
