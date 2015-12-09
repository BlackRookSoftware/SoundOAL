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
 * Chorus effect for sound sources.
 * @author Matthew Tropiano
 */
public class ChorusEffect extends OALEffect
{
	/** WaveForm type enumeration. */
	public static enum WaveForm
	{
		SINUSOID(ALExt.AL_CHORUS_WAVEFORM_SINUSOID),
		TRIANGLE(ALExt.AL_CHORUS_WAVEFORM_TRIANGLE);
		
		final int alVal;
		private WaveForm(int alVal) {this.alVal = alVal;}
	}
	
	/** Chorus waveform type. */
	protected WaveForm waveForm;
	/** Chorus phase in degrees.  */
	protected int phase;
	/** Chorus rate in Hz. */
	protected float rate;
	/** Chorus depth. */
	protected float depth;
	/** Chorus feedback. */
	protected float feedback;
	/** Chorus delay in seconds. */
	protected float delay;
	
	public ChorusEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_CHORUS);
		setWaveForm(WaveForm.TRIANGLE);
		setPhase(ALExt.AL_CHORUS_DEFAULT_PHASE);
		setRate(ALExt.AL_CHORUS_DEFAULT_RATE);
		setDepth(ALExt.AL_CHORUS_DEFAULT_DEPTH);
		setFeedback(ALExt.AL_CHORUS_DEFAULT_FEEDBACK);
		setDelay(ALExt.AL_CHORUS_DEFAULT_DELAY);
	}

	/**
	 * Gets this effect's delay in seconds.
	 */
	public final float getDelay()
	{
		return delay;
	}

	/**
	 * Sets this effect's delay in seconds (0.0 to 0.016).
	 */
	public final void setDelay(float delay)
	{
		this.delay = delay;
		alext.alEffectf(getALId(), ALExt.AL_CHORUS_DELAY, RMath.clampValue(delay, (float)ALExt.AL_CHORUS_MIN_DELAY, ALExt.AL_CHORUS_MAX_DELAY));
		errorCheck();
	}

	/**
	 * Gets this effect's depth.
	 */
	public final float getDepth()
	{
		return depth;
	}

	/**
	 * Sets this effect's depth (0.0 to 1.0).
	 */
	public final void setDepth(float depth)
	{
		this.depth = depth;
		alext.alEffectf(getALId(), ALExt.AL_CHORUS_DEPTH, RMath.clampValue(depth, (float)ALExt.AL_CHORUS_MIN_DEPTH, ALExt.AL_CHORUS_MAX_DEPTH));
		errorCheck();
	}

	/**
	 * Gets this effect's feedback.
	 */
	public final float getFeedback()
	{
		return feedback;
	}

	/**
	 * Sets this effect's feedback (-1.0 to 1.0).
	 */
	public final void setFeedback(float feedback)
	{
		this.feedback = feedback;
		alext.alEffectf(getALId(), ALExt.AL_CHORUS_FEEDBACK, RMath.clampValue(feedback, ALExt.AL_CHORUS_MIN_FEEDBACK, ALExt.AL_CHORUS_MAX_FEEDBACK));
		errorCheck();
	}

	/**
	 * Gets this effect's phase in degrees.
	 */
	public final int getPhase()
	{
		return phase;
	}

	/**
	 * Sets this effect's phase in degrees (-180 to 180).
	 */
	public final void setPhase(int phase)
	{
		this.phase = phase;
		alext.alEffecti(getALId(), ALExt.AL_CHORUS_PHASE, RMath.clampValue(phase, ALExt.AL_CHORUS_MIN_PHASE, ALExt.AL_CHORUS_MAX_PHASE));
		errorCheck();
	}

	/**
	 * Gets this effect's rate in Hz.
	 */
	public final float getRate()
	{
		return rate;
	}

	/**
	 * Sets this effect's rate in Hz (0.0 to 10.0).
	 */
	public final void setRate(float rate)
	{
		this.rate = rate;
		alext.alEffectf(getALId(), ALExt.AL_CHORUS_RATE, RMath.clampValue(rate, (float)ALExt.AL_CHORUS_MIN_RATE, ALExt.AL_CHORUS_MAX_RATE));
		errorCheck();
	}

	/**
	 * Gets this effect's waveform type.
	 */
	public final WaveForm getWaveForm()
	{
		return waveForm;
	}

	/**
	 * Sets this effect's waveform type.
	 * @param waveForm	the waveform enumerant.
	 */
	public final void setWaveForm(WaveForm waveForm)
	{
		this.waveForm = waveForm;
		alext.alEffecti(getALId(), ALExt.AL_CHORUS_WAVEFORM, waveForm.alVal);
		errorCheck();
	}
}
