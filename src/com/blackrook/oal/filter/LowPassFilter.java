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
 * Low-pass filter object for low-pass filtering.
 * @author Matthew Tropiano
 */
public class LowPassFilter extends OALFilter
{
	/** Low-pass gain. */
	protected float gain;
	/** High-frequency low-pass gain. */
	protected float gainHF;
	
	public LowPassFilter(OALSystem system)
	{
		super(system, ALExt.AL_FILTER_LOWPASS);
		setGain(ALExt.AL_LOWPASS_DEFAULT_GAIN);
		setHFGain(ALExt.AL_LOWPASS_DEFAULT_GAINHF);
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
		alext.alFilterf(getALId(), ALExt.AL_LOWPASS_GAIN, RMath.clampValue(gain, (float)ALExt.AL_LOWPASS_MIN_GAIN, ALExt.AL_LOWPASS_MAX_GAIN));
		errorCheck();
	}
	
	/**
	 * Get this filter's high-frequency gain.
	 */
	public float getHFGain()
	{
		return gainHF;
	}

	/**
	 * Sets this filter's high-frequency gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setHFGain(float gain)
	{
		this.gainHF = gain;
		alext.alFilterf(getALId(), ALExt.AL_LOWPASS_GAINHF, RMath.clampValue(gain, (float)ALExt.AL_LOWPASS_MIN_GAINHF, ALExt.AL_LOWPASS_MAX_GAINHF));
		errorCheck();
	}
	
}
