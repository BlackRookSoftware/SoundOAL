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
package com.blackrook.oal.filter;

import com.blackrook.oal.OALFilter;
import com.blackrook.oal.enums.FilterType;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

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
	
	public LowPassFilter(AL al, ALC alc)
	{
		super(al,alc,FilterType.LOWPASS);
		setGain(1);
		setHFGain(1);
	}
	
	/**
	 * Sets this filter's gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setGain(float gain)
	{
		this.gain = gain;
		al.alFilterf(getALId(), AL.AL_LOWPASS_GAIN, gain);
	}
	
	/**
	 * Get this filter's gain.
	 */
	public float getGain()
	{
		return gain;
	}
	
	/**
	 * Sets this filter's high-frequency gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setHFGain(float gain)
	{
		this.gainHF = gain;
		al.alFilterf(getALId(), AL.AL_LOWPASS_GAINHF, gain);
	}
	
	/**
	 * Get this filter's high-frequency gain.
	 */
	public float getHFGain()
	{
		return gainHF;
	}
	
}
