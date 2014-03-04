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
import com.blackrook.oal.enums.EffectType;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

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
	
	public EqualizerEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.EQUALIZER);
		setLowGain(1);
		setLowCutoff(200);
		
		setMid1Gain(1);
		setMid1Center(500);
		setMid1Width(1);
		
		setMid2Gain(1);
		setMid2Center(3000);
		setMid2Width(1);
		
		setHighGain(1);
		setHighCutoff(6000);
	}

	/** Get equalizer high cutoff in Hertz. */
	public final float getHighCutoff() 
	{
		return highCutoff;
	}

	/** Get equalizer high gain. */
	public final float getHighGain() 
	{
		return highGain;
	}

	/** Get equalizer low cutoff in Hertz. */
	public final float getLowCutoff() 
	{
		return lowCutoff;
	}

	/** Get equalizer low gain. */
	public final float getLowGain() 
	{
		return lowGain;
	}

	/** Get equalizer first mid center in Hertz. */
	public final float getMid1Center() 
	{
		return mid1Center;
	}

	/** Get equalizer first mid gain. */
	public final float getMid1Gain() 
	{
		return mid1Gain;
	}

	/** Get equalizer first mid width. */
	public final float getMid1Width() 
	{
		return mid1Width;
	}

	/** Get equalizer second mid center in Hertz. */
	public final float getMid2Center() 
	{
		return mid2Center;
	}

	/** Get equalizer second mid gain. */
	public final float getMid2Gain() 
	{
		return mid2Gain;
	}

	/** Get equalizer second mid width. */
	public final float getMid2Width() 
	{
		return mid2Width;
	}

	/** Set equalizer high cutoff in Hertz (4000.0 to 16000.0). */
	public final void setHighCutoff(float highCutoff) 
	{
		this.highCutoff = highCutoff;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_HIGH_CUTOFF, RMath.clampValue(highCutoff, 4000.0f, 16000.0f));
	}

	/** Set equalizer high gain (0.126 to 7.943). */
	public final void setHighGain(float highGain)
	{
		this.highGain = highGain;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_HIGH_GAIN, RMath.clampValue(highGain, 0.126f, 7.943f));
	}

	/** Set equalizer low cutoff in Hertz (50.0 to 800.0). */
	public final void setLowCutoff(float lowCutoff) 
	{
		this.lowCutoff = lowCutoff;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_LOW_CUTOFF, RMath.clampValue(lowCutoff, 50.0f, 800.0f));
	}

	/** Set equalizer low gain (0.126 to 7.943). */
	public final void setLowGain(float lowGain) 
	{
		this.lowGain = lowGain;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_LOW_GAIN, RMath.clampValue(lowGain, 0.126f, 7.943f));
	}

	/** Set equalizer first mid center in Hertz (200.0 to 3000.0). */
	public final void setMid1Center(float mid1Center) 
	{
		this.mid1Center = mid1Center;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_MID1_CENTER, RMath.clampValue(mid1Center, 200.0f, 3000.0f));
	}

	/** Set equalizer first mid gain (0.126 to 7.943). */
	public final void setMid1Gain(float mid1Gain) 
	{
		this.mid1Gain = mid1Gain;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_MID1_GAIN, RMath.clampValue(mid1Gain, 0.126f, 7.943f));
	}

	/** Set equalizer first mid width (0.01 to 1.0). */
	public final void setMid1Width(float mid1Width) 
	{
		this.mid1Width = mid1Width;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_MID1_WIDTH, RMath.clampValue(mid1Width, 0.1f, 1.0f));
	}

	/** Set equalizer second mid center in Hertz (1000.0 to 8000.0). */
	public final void setMid2Center(float mid2Center) 
	{
		this.mid2Center = mid2Center;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_MID2_CENTER, RMath.clampValue(mid2Center, 1000.0f, 8000.0f));
	}

	/** Set equalizer second mid gain (0.126 to 7.943). */
	public final void setMid2Gain(float mid2Gain) 
	{
		this.mid2Gain = mid2Gain;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_MID2_GAIN, RMath.clampValue(mid2Gain, 0.126f, 7.943f));
	}

	/** Set equalizer second mid width (0.01 to 1.0). */
	public final void setMid2Width(float mid2Width) 
	{
		this.mid2Width = mid2Width;
		al.alEffectf(getALId(), AL.AL_EQUALIZER_MID2_WIDTH, RMath.clampValue(mid2Width, 0.1f, 1.0f));
	}
	
}
