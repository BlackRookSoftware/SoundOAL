/*******************************************************************************
 * Copyright (c) 2009-2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *  
 * Contributors:
 *     Matt Tropiano - initial API and implementation
 ******************************************************************************/
package com.blackrook.oal;

import com.blackrook.oal.enums.EffectType;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

/**
 * Effect object for OpenAL sources.
 * @author Matthew Tropiano
 */
public abstract class OALEffect extends OALObject
{
	/** AL Effect type. */
	private EffectType effectType;
	
	protected OALEffect(AL al, ALC alc, EffectType type)
	{
		super(al,alc);
		effectType = type;
		al.alEffecti(getALId(), AL.AL_EFFECT_TYPE, effectType.alVal);
	}
	
	protected final int allocate()
	{
		int[] STATE_NUMBER = new int[1];
		al.alGetError();
		al.alGenEffects(1,STATE_NUMBER,0);
		errorCheck(this);
		return STATE_NUMBER[0];
	}
	
	protected final void free()
	{
		int[] STATE_NUMBER = {getALId()};
		al.alDeleteEffects(1, STATE_NUMBER, 0);
	}
	
}
