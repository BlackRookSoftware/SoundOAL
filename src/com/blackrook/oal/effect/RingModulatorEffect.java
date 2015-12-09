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
 * Ring modulator effect for sources.
 * @author Matthew Tropiano
 */
public class RingModulatorEffect extends OALEffect 
{
	/** WaveForm type enumeration. */
	public static enum WaveForm
	{
		SINUSOID(ALExt.AL_RING_MODULATOR_SINUSOID),
		SAWTOOTH(ALExt.AL_RING_MODULATOR_SAWTOOTH),
		SQUARE(ALExt.AL_RING_MODULATOR_SQUARE);
		
		final int alVal;
		private WaveForm(int alVal) {this.alVal = alVal;}
	}
	
	/** Ring modulator frequency. */
	protected float frequency;
	/** Ring modulator high-pass cutoff in Hertz. */
	protected float highPassCutoff;
	/** Ring modulator waveform. */
	protected WaveForm waveForm;

	public RingModulatorEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_RING_MODULATOR);
		setWaveform(WaveForm.SINUSOID);
		setFrequency(ALExt.AL_RING_MODULATOR_DEFAULT_FREQUENCY);
		setHighPassCutoff(ALExt.AL_RING_MODULATOR_DEFAULT_HIGHPASS_CUTOFF);
	}
	
	/** Get ring modulator waveform. */
	public final WaveForm getWaveform()
	{
		return waveForm;
	}

	/** Set ring modulator waveform. */
	public final void setWaveform(WaveForm waveform)
	{
		this.waveForm = waveform;
		alext.alEffecti(getALId(), ALExt.AL_RING_MODULATOR_WAVEFORM, waveform.alVal);
		errorCheck();
	}

	/** Get ring modulator shifter frequency. */
	public final float getFrequency()
	{
		return frequency;
	}

	/** Set frequency shifter frequency (0.0 to 8000.0). */
	public final void setFrequency(float frequency)
	{
		this.frequency = frequency;
		alext.alEffectf(getALId(), ALExt.AL_RING_MODULATOR_FREQUENCY, RMath.clampValue(frequency, (float)ALExt.AL_RING_MODULATOR_MIN_FREQUENCY, ALExt.AL_RING_MODULATOR_MAX_FREQUENCY));
		errorCheck();
	}

	/** Get ring modulator high-pass cutoff in Hertz. */
	public final float getHighPassCutoff()
	{
		return highPassCutoff;
	}

	/** Set ring modulator high-pass cutoff in Hertz (0.0 to 24000.0). */
	public final void setHighPassCutoff(float highPassCutoff)
	{
		this.highPassCutoff = highPassCutoff;
		alext.alEffectf(getALId(), ALExt.AL_RING_MODULATOR_HIGHPASS_CUTOFF, RMath.clampValue(highPassCutoff, (float)ALExt.AL_RING_MODULATOR_MIN_HIGHPASS_CUTOFF, ALExt.AL_RING_MODULATOR_MAX_HIGHPASS_CUTOFF));
		errorCheck();
	}

}
