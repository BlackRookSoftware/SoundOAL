/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal.effect;

import com.blackrook.oal.OALEffect;
import com.blackrook.oal.OALSystem;
import com.jogamp.openal.ALExt;

/**
 * Compressor effect for sound sources.
 * @author Matthew Tropiano
 */
public class CompressorEffect extends OALEffect
{
	/** Compressor state. */
	protected boolean enabled;
	
	public CompressorEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_COMPRESSOR);
		setEnabled(ALExt.AL_COMPRESSOR_DEFAULT_ONOFF != 0);
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
		alext.alEffecti(getALId(), ALExt.AL_COMPRESSOR_ONOFF, enabled ? 1 : 0);
		errorCheck();
	}
	
	
}
