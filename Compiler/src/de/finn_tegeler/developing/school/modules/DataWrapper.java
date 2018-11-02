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

import java.util.List;

/**
 * @author Finn Tegeler
 */
public class DataWrapper {
	
	public static final int	MAX_DEPTH	= 25;
	private int				_depth		= 0;
	private int				_index		= 0;
	private List<String>	_rawTokens;
	private int				_recorded	= -1;
	
	public DataWrapper(List<String> rawTokens) {
		this._rawTokens = rawTokens;
	}
	
	public void addIndex(int toAdd) {
		_index += toAdd;
		if (_index < 0 || _index >= _rawTokens.size()) {
			System.err.println("Could not perform this subtracton on index as it is out of range");
			_index -= toAdd;
		} else {
			if (this._recorded >= 0) {
				this._recorded += toAdd;
			}
		}
	}
	
	public String get() {
		return _rawTokens.get(_index);
	}
	
	public int getRecording() {
		return this._recorded;
	}
	
	public boolean in() {
		if ((_depth + 1) < MAX_DEPTH) {
			_depth++;
			return true;
		} else {
			System.out.println("[INFO] Reached maximum depth of 100");
			return false;
		}
	}
	
	public void nextRawToken() {
		_index++;
		if (_index < 0 || _index >= _rawTokens.size()) {
			System.err.println("Could not move to next raw token as this is the end.");
			_index--;
		} else {
			if (this._recorded >= 0) {
				this._recorded++;
			}
		}
	}
	
	public void out() {
		if (_depth > 0) {
			_depth--;
		}
	}
	
	public void startRecording() {
		this._recorded = 0;
	}
	
	public void stopRecording() {
		this._recorded = -1;
	}
	
	public void subIndex(int toSub) {
		_index -= toSub;
		if (_index < 0 || _index >= _rawTokens.size()) {
			System.err.println("Could not perform this subtracton on index as it is out of range");
			_index += toSub;
		} else {
			if (this._recorded >= 0) {
				this._recorded -= toSub;
			}
		}
	}
}
