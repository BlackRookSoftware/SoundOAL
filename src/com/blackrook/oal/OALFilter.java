/*******************************************************************************
 * Copyright (c) 2014, 2015 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     Matt Tropiano - initial API and implementation
 *******************************************************************************/
package com.blackrook.oal;

import com.blackrook.oal.exception.SoundException;
import com.jogamp.openal.ALExt;

/**
 * Filter object for OpenAL sources.
 * @author Matthew Tropiano
 */
public abstract class OALFilter extends OALObject
{
	protected OALFilter(OALSystem system, int alFilterType)
	{
		super(system);
		alext.alFilteri(getALId(), ALExt.AL_FILTER_TYPE, alFilterType);
	}

	@Override
	protected int allocate() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		al.alGetError();
		alext.alGenFilters(1, STATE_NUMBER, 0);
		errorCheck();
		return STATE_NUMBER[0];
	}

	@Override
	protected void free() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		STATE_NUMBER[0] = getALId(); 
		alext.alDeleteFilters(getALId(), STATE_NUMBER, 0);
		errorCheck();
	}

}
