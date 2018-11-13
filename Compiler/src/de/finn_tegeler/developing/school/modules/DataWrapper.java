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

import de.finn_tegeler.developing.school.RawToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Finn Tegeler
 */
public class DataWrapper {
	
	public static final int	MAX_DEPTH				= 100;
	private int				_currentRecordingIndex	= 0;
	private int				_depth					= 0;
	private String			_globalErrorMessage;
	private int				_index					= 0;
	private List<RawToken>	_rawTokens;
	private List<Integer>	_recordings				= new ArrayList<>();
	private String			errorMessage;
	
	public DataWrapper(List<RawToken> rawTokens) {
		this._rawTokens = rawTokens;
	}
	
	public void addIndex(int toAdd) {
		_index += toAdd;
		if (_index < 0 || _index >= _rawTokens.size()) {
			System.err.println("Could not perform this subtracton on index as it is out of range");
			_index -= toAdd;
		} else {
			if (this._recordings.size() > 0) {
				this._recordings.set(_currentRecordingIndex, this._recordings.get(_currentRecordingIndex) + toAdd);
			}
		}
	}
	
	public String get() {
		return _rawTokens.get(_index).getContent();
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * @return the globalErrorMessage
	 */
	public String getGlobalErrorMessage() {
		return this._globalErrorMessage;
	}
	
	public int getRecording(int index) {
		return this._recordings.get(index);
	}
	
	public RawToken getToken() {
		return _rawTokens.get(_index);
	}
	
	public boolean in() {
		if ((_depth + 1) < MAX_DEPTH) {
			_depth++;
			return true;
		} else {
			// System.out.println("[INFO] Reached maximum depth of 100");
			return false;
		}
	}
	
	public void nextRawToken() {
		_index++;
		if (_index < 0 || _index >= _rawTokens.size()) {
			System.err.println("Could not move to next raw token as this is the end.");
			_index--;
		} else {
			if (this._recordings.size() > 0) {
				this._recordings.set(_currentRecordingIndex, this._recordings.get(_currentRecordingIndex) + 1);
			}
		}
	}
	
	public void out() {
		if (_depth > 0) {
			_depth--;
		}
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @param globalErrorMessage
	 *            the globalErrorMessage to set
	 */
	public void setGlobalErrorMessage(String globalErrorMessage) {
		this._globalErrorMessage = globalErrorMessage;
	}
	
	public int startRecording() {
		this._recordings.add(0);
		int id = this._recordings.size() - 1;
		this._currentRecordingIndex = id;
		return id;
	}
	
	public void stopRecording(int index) {
		this._recordings.set(index, -1);
		if (_currentRecordingIndex > 0) {
			this._currentRecordingIndex--;
		}
	}
	
	public void subIndex(int toSub) {
		_index -= toSub;
		if (_index < 0 || _index >= _rawTokens.size()) {
			System.err.println("Could not perform this subtracton on index as it is out of range");
			_index += toSub;
		} else {
			if (this._recordings.size() > 0) {
				this._recordings.set(_currentRecordingIndex, this._recordings.get(_currentRecordingIndex) - toSub);
			}
		}
	}
}
