/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal;

import com.blackrook.oal.enums.FilterType;
import com.blackrook.oal.exception.SoundException;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

/**
 * Filter object for OpenAL sources.
 * @author Matthew Tropiano
 */
public abstract class OALFilter extends OALObject
{
	/** AL filter type. */
	private FilterType filterType;

	protected OALFilter(AL al, ALC alc, FilterType type)
	{
		super(al,alc);
		filterType = type;
		al.alFilteri(getALId(), AL.AL_FILTER_TYPE, filterType.alVal);
	}

	@Override
	protected int allocate() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		al.alGetError();
		al.alGenFilters(1, STATE_NUMBER, 0);
		errorCheck(this);
		return STATE_NUMBER[0];
	}

	@Override
	protected void free() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		STATE_NUMBER[0] = getALId(); 
		al.alDeleteFilters(getALId(), STATE_NUMBER, 0);
	}

}
