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
package com.blackrook.oal.effect;

import com.blackrook.oal.OALEffect;
import com.blackrook.oal.enums.EffectType;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

/**
 * Compressor effect for sound sources.
 * THIS IS KOMPRESSOR
 * @author Matthew Tropiano
 */
public class CompressorEffect extends OALEffect
{
	/** Compressor state. */
	protected boolean enabled;
	
	public CompressorEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.COMPRESSOR);
		setEnabled(true);
	}

	/** Is the effect enabled? */
	public final boolean isEnabled()
	{
		return enabled;
	}

	/** Sets if the effect is enabled. */
	public final void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		al.alEffecti(getALId(), AL.AL_COMPRESSOR_ONOFF, enabled?1:0);
	}
	
	
}
