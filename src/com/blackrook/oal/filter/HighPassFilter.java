/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal.filter;

import com.blackrook.commons.math.RMath;
import com.blackrook.oal.OALFilter;
import com.blackrook.oal.OALSystem;
import com.jogamp.openal.ALExt;

/**
 * High-pass filter object for high-pass filtering.
 * @author Matthew Tropiano
 */
public class HighPassFilter extends OALFilter
{
	/** High-pass gain. */
	protected float gain;
	/** Low-frequency high-pass gain. */
	protected float gainLF;
	
	public HighPassFilter(OALSystem system)
	{
		super(system, ALExt.AL_FILTER_HIGHPASS);
		setGain(ALExt.AL_HIGHPASS_DEFAULT_GAIN);
		setLFGain(ALExt.AL_HIGHPASS_DEFAULT_GAINLF);
	}
	
	/**
	 * Get this filter's gain.
	 */
	public float getGain()
	{
		return gain;
	}

	/**
	 * Sets this filter's gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setGain(float gain)
	{
		this.gain = gain;
		alext.alFilterf(getALId(), ALExt.AL_HIGHPASS_GAIN, RMath.clampValue(gain, (float)ALExt.AL_HIGHPASS_MIN_GAIN, ALExt.AL_HIGHPASS_MAX_GAIN));
		errorCheck();
	}
	
	/**
	 * Get this filter's low-frequency gain.
	 */
	public float getLFGain()
	{
		return gainLF;
	}

	/**
	 * Sets this filter's low-frequency gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setLFGain(float gain)
	{
		this.gainLF = gain;
		alext.alFilterf(getALId(), ALExt.AL_HIGHPASS_GAINLF, RMath.clampValue(gain, (float)ALExt.AL_HIGHPASS_MIN_GAINLF, ALExt.AL_HIGHPASS_MAX_GAINLF));
		errorCheck();
	}
	
}
