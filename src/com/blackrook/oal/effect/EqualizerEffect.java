/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal.effect;

import com.blackrook.commons.math.RMath;
import com.blackrook.oal.OALEffect;
import com.blackrook.oal.OALSystem;
import com.jogamp.openal.ALExt;

/**
 * Equalizer effect for sound sources.
 * @author Matthew Tropiano
 */
public class EqualizerEffect extends OALEffect
{
	/** Equalizer low gain. */
	protected float lowGain;
	/** Equalizer low cutoff in Hertz. */
	protected float lowCutoff;
	/** Equalizer first mid gain. */
	protected float mid1Gain;
	/** Equalizer first mid center in Hertz. */
	protected float mid1Center;
	/** Equalizer first mid width. */
	protected float mid1Width;
	/** Equalizer second mid gain. */
	protected float mid2Gain;
	/** Equalizer second mid center in Hertz. */
	protected float mid2Center;
	/** Equalizer second mid width. */
	protected float mid2Width;
	/** Equalizer high gain. */
	protected float highGain;
	/** Equalizer high cutoff in Hertz. */
	protected float highCutoff;
	
	public EqualizerEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_EQUALIZER);
		setLowGain(ALExt.AL_EQUALIZER_DEFAULT_LOW_GAIN);
		setLowCutoff(ALExt.AL_EQUALIZER_DEFAULT_LOW_CUTOFF);
		
		setMid1Gain(ALExt.AL_EQUALIZER_DEFAULT_MID1_GAIN);
		setMid1Center(ALExt.AL_EQUALIZER_DEFAULT_MID1_CENTER);
		setMid1Width(ALExt.AL_EQUALIZER_DEFAULT_MID1_WIDTH);
		
		setMid2Gain(ALExt.AL_EQUALIZER_DEFAULT_MID2_GAIN);
		setMid2Center(ALExt.AL_EQUALIZER_DEFAULT_MID2_CENTER);
		setMid2Width(ALExt.AL_EQUALIZER_DEFAULT_MID2_WIDTH);
		
		setHighGain(ALExt.AL_EQUALIZER_DEFAULT_HIGH_GAIN);
		setHighCutoff(ALExt.AL_EQUALIZER_DEFAULT_HIGH_CUTOFF);
	}

	/** Get equalizer high cutoff in Hertz. */
	public final float getHighCutoff() 
	{
		return highCutoff;
	}

	/** Set equalizer high cutoff in Hertz (4000.0 to 16000.0). */
	public final void setHighCutoff(float highCutoff) 
	{
		this.highCutoff = highCutoff;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_HIGH_CUTOFF, RMath.clampValue(highCutoff, ALExt.AL_EQUALIZER_MIN_HIGH_CUTOFF, ALExt.AL_EQUALIZER_MAX_HIGH_CUTOFF));
		errorCheck();
	}

	/** Get equalizer high gain. */
	public final float getHighGain() 
	{
		return highGain;
	}

	/** Set equalizer high gain (0.126 to 7.943). */
	public final void setHighGain(float highGain)
	{
		this.highGain = highGain;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_HIGH_GAIN, RMath.clampValue(highGain, ALExt.AL_EQUALIZER_MIN_HIGH_GAIN, ALExt.AL_EQUALIZER_MAX_HIGH_GAIN));
		errorCheck();
	}

	/** Get equalizer low cutoff in Hertz. */
	public final float getLowCutoff() 
	{
		return lowCutoff;
	}

	/** Set equalizer low cutoff in Hertz (50.0 to 800.0). */
	public final void setLowCutoff(float lowCutoff) 
	{
		this.lowCutoff = lowCutoff;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_LOW_CUTOFF, RMath.clampValue(lowCutoff, ALExt.AL_EQUALIZER_MIN_LOW_CUTOFF, ALExt.AL_EQUALIZER_MAX_LOW_CUTOFF));
		errorCheck();
	}

	/** Get equalizer low gain. */
	public final float getLowGain() 
	{
		return lowGain;
	}

	/** Set equalizer low gain (0.126 to 7.943). */
	public final void setLowGain(float lowGain) 
	{
		this.lowGain = lowGain;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_LOW_GAIN, RMath.clampValue(lowGain, ALExt.AL_EQUALIZER_MIN_LOW_GAIN, ALExt.AL_EQUALIZER_MAX_LOW_GAIN));
		errorCheck();
	}

	/** Get equalizer first mid center in Hertz. */
	public final float getMid1Center() 
	{
		return mid1Center;
	}

	/** Set equalizer first mid center in Hertz (200.0 to 3000.0). */
	public final void setMid1Center(float mid1Center) 
	{
		this.mid1Center = mid1Center;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_MID1_CENTER, RMath.clampValue(mid1Center, ALExt.AL_EQUALIZER_MIN_MID1_CENTER, ALExt.AL_EQUALIZER_MAX_MID1_CENTER));
		errorCheck();
	}

	/** Get equalizer first mid gain. */
	public final float getMid1Gain() 
	{
		return mid1Gain;
	}

	/** Set equalizer first mid gain (0.126 to 7.943). */
	public final void setMid1Gain(float mid1Gain) 
	{
		this.mid1Gain = mid1Gain;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_MID1_GAIN, RMath.clampValue(mid1Gain, ALExt.AL_EQUALIZER_MIN_MID1_GAIN, ALExt.AL_EQUALIZER_MAX_MID1_GAIN));
		errorCheck();
	}

	/** Get equalizer first mid width. */
	public final float getMid1Width() 
	{
		return mid1Width;
	}

	/** Set equalizer first mid width (0.01 to 1.0). */
	public final void setMid1Width(float mid1Width) 
	{
		this.mid1Width = mid1Width;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_MID1_WIDTH, RMath.clampValue(mid1Width, ALExt.AL_EQUALIZER_MIN_MID1_WIDTH, ALExt.AL_EQUALIZER_MAX_MID1_WIDTH));
		errorCheck();
	}

	/** Get equalizer second mid center in Hertz. */
	public final float getMid2Center() 
	{
		return mid2Center;
	}

	/** Set equalizer second mid center in Hertz (1000.0 to 8000.0). */
	public final void setMid2Center(float mid2Center) 
	{
		this.mid2Center = mid2Center;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_MID2_CENTER, RMath.clampValue(mid2Center, ALExt.AL_EQUALIZER_MIN_MID2_CENTER, ALExt.AL_EQUALIZER_MAX_MID2_CENTER));
		errorCheck();
	}

	/** Get equalizer second mid gain. */
	public final float getMid2Gain() 
	{
		return mid2Gain;
	}

	/** Set equalizer second mid gain (0.126 to 7.943). */
	public final void setMid2Gain(float mid2Gain) 
	{
		this.mid2Gain = mid2Gain;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_MID2_GAIN, RMath.clampValue(mid2Gain, ALExt.AL_EQUALIZER_MIN_MID2_GAIN, ALExt.AL_EQUALIZER_MAX_MID2_GAIN));
		errorCheck();
	}

	/** Get equalizer second mid width. */
	public final float getMid2Width() 
	{
		return mid2Width;
	}

	/** Set equalizer second mid width (0.01 to 1.0). */
	public final void setMid2Width(float mid2Width) 
	{
		this.mid2Width = mid2Width;
		alext.alEffectf(getALId(), ALExt.AL_EQUALIZER_MID2_WIDTH, RMath.clampValue(mid2Width, ALExt.AL_EQUALIZER_MIN_MID2_WIDTH, ALExt.AL_EQUALIZER_MAX_MID2_WIDTH));
		errorCheck();
	}
	
}
