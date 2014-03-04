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
 * Ring modulator effect for sources.
 * @author Matthew Tropiano
 */
public class RingModulatorEffect extends OALEffect 
{
	/** WaveForm type enumeration. */
	public static enum WaveForm
	{
		SINUSOID(AL.AL_RING_MODULATOR_SINUSOID),
		SAWTOOTH(AL.AL_RING_MODULATOR_SAWTOOTH),
		SQUARE(AL.AL_RING_MODULATOR_SQUARE);
		
		final int alVal;
		private WaveForm(int alVal) {this.alVal = alVal;}
	}
	
	/** Ring modulator frequency. */
	protected float frequency;
	/** Ring modulator high-pass cutoff in Hertz. */
	protected float highPassCutoff;
	/** Ring modulator waveform. */
	protected WaveForm waveForm;

	public RingModulatorEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.RING_MODULATOR);
		setWaveform(WaveForm.SINUSOID);
		setFrequency(440);
		setHighPassCutoff(800);
	}
	
	/** Get ring modulator waveform. */
	public final WaveForm getWaveform()
	{
		return waveForm;
	}

	/** Get ring modulator shifter frequency. */
	public final float getFrequency()
	{
		return frequency;
	}

	/** Get ring modulator high-pass cutoff in Hertz. */
	public final float getHighPassCutoff()
	{
		return highPassCutoff;
	}

	/** Set ring modulator waveform. */
	public final void setWaveform(WaveForm waveform)
	{
		this.waveForm = waveform;
		al.alEffecti(getALId(), AL.AL_RING_MODULATOR_WAVEFORM, waveform.alVal);
	}
	
	/** Set frequency shifter frequency (0.0 to 8000.0). */
	public final void setFrequency(float frequency)
	{
		this.frequency = frequency;
		al.alEffectf(getALId(), AL.AL_RING_MODULATOR_FREQUENCY, RMath.clampValue(frequency, 0.0f, 8000.0f));
	}

	/** Set ring modulator high-pass cutoff in Hertz (0.0 to 24000.0). */
	public final void setHighPassCutoff(float highPassCutoff)
	{
		this.highPassCutoff = highPassCutoff;
		al.alEffectf(getALId(), AL.AL_RING_MODULATOR_HIGHPASS_CUTOFF, RMath.clampValue(highPassCutoff, 0.0f, 24000.0f));
	}



}
