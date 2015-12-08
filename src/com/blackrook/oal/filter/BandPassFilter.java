/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal.filter;

import com.blackrook.oal.OALFilter;
import com.blackrook.oal.OALSystem;
import com.jogamp.openal.ALExt;

/**
 * Band-pass filter object for band-pass filtering.
 * @author Matthew Tropiano
 */
public class BandPassFilter extends OALFilter
{
	/** Band-pass gain. */
	protected float gain;
	/** Low-frequency band-pass gain. */
	protected float gainLF;
	/** High-frequency band-pass gain. */
	protected float gainHF;
	
	public BandPassFilter(OALSystem system)
	{
		super(system, ALExt.AL_FILTER_BANDPASS);
		setGain(1f);
		setLFGain(1f);
		setHFGain(1f);
	}
	
	/**
	 * Sets this filter's gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setGain(float gain)
	{
		this.gain = gain;
		alext.alFilterf(getALId(), ALExt.AL_BANDPASS_GAIN, gain);
	}
	
	/**
	 * Get this filter's gain.
	 */
	public float getGain()
	{
		return gain;
	}
	
	/**
	 * Sets this filter's low-frequency gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setLFGain(float gain)
	{
		this.gainLF = gain;
		alext.alFilterf(getALId(), ALExt.AL_BANDPASS_GAINLF, gain);
	}
	
	/**
	 * Get this filter's low-frequency gain.
	 */
	public float getLFGain()
	{
		return gainLF;
	}
	
	/**
	 * Sets this filter's high-frequency gain.
	 * @param gain	the gain value (0.0 to 1.0).
	 */
	public void setHFGain(float gain)
	{
		this.gainHF = gain;
		alext.alFilterf(getALId(), ALExt.AL_BANDPASS_GAINHF, gain);
	}
	
	/**
	 * Get this filter's high-frequency gain.
	 */
	public float getHFGain()
	{
		return gainHF;
	}
	

}
