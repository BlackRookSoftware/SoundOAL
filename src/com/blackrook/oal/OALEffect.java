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

import com.jogamp.openal.ALExt;

/**
 * Effect object for OpenAL sources.
 * @author Matthew Tropiano
 */
public abstract class OALEffect extends OALObject
{
	protected OALEffect(OALSystem system, int alEffectType)
	{
		super(system);
		alext.alEffecti(getALId(), ALExt.AL_EFFECT_TYPE, alEffectType);
	}
	
	@Override
	protected final int allocate()
	{
		int[] STATE_NUMBER = new int[1];
		al.alGetError();
		alext.alGenEffects(1, STATE_NUMBER, 0);
		errorCheck();
		return STATE_NUMBER[0];
	}
	
	@Override
	protected final void free()
	{
		int[] STATE_NUMBER = {getALId()};
		alext.alDeleteEffects(1, STATE_NUMBER, 0);
		errorCheck();
	}
	
}
