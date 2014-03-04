/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal.enums;

import com.jogamp.openal.AL;

/**
 * Enumaertation of Filter types.
 * @author Matthew Tropiano
 */
public enum FilterType
{
	BANDPASS(AL.AL_FILTER_BANDPASS),
	HIGHPASS(AL.AL_FILTER_HIGHPASS),
	LOWPASS(AL.AL_FILTER_LOWPASS);
	
	public final int alVal;
	private FilterType(int al) {alVal = al;}
}
