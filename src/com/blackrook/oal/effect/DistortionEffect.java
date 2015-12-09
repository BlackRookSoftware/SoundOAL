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
 * Distortion effect for sound sources.
 * @author Matthew Tropiano
 */
public class DistortionEffect extends OALEffect
{
	/** Distortion edge. */
	protected float edge;
	/** Distortion gain. */
	protected float gain;
	/** Distortion low-pass cutoff in Hertz. */
	protected float lowPassCutoff;
	/** Distortion equalizer centering in Hertz. */
	protected float eqCenter;
	/** Distortion equalizer bandwidth in Hertz. */
	protected float eqBandwidth;
	
	public DistortionEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_DISTORTION);
		setGain(ALExt.AL_DISTORTION_DEFAULT_GAIN);
		setEdge(ALExt.AL_DISTORTION_DEFAULT_EDGE);
		setLowPassCutoff(ALExt.AL_DISTORTION_DEFAULT_LOWPASS_CUTOFF);
		setEqualizerCenter(ALExt.AL_DISTORTION_DEFAULT_EQCENTER);
		setEqualizerBandwidth(ALExt.AL_DISTORTION_DEFAULT_EQBANDWIDTH);
	}
	
	/** Get distortion gain. */
	public final float getGain()
	{
		return gain;
	}

	/** Set distortion gain (0.01 to 1.0). */
	public final void setGain(float gain)
	{
		this.gain = gain;
		alext.alEffectf(getALId(), ALExt.AL_DISTORTION_GAIN, RMath.clampValue(gain, ALExt.AL_DISTORTION_MIN_GAIN, ALExt.AL_DISTORTION_MAX_GAIN));
		errorCheck();
	}

	/** Get distortion edge. */
	public final float getEdge()
	{
		return edge;
	}

	/** Set distortion edge (0.0 to 1.0). */
	public final void setEdge(float edge)
	{
		this.edge = edge;
		alext.alEffectf(getALId(), ALExt.AL_DISTORTION_EDGE, RMath.clampValue(edge, (float)ALExt.AL_DISTORTION_MIN_EDGE, ALExt.AL_DISTORTION_MAX_EDGE));
		errorCheck();
	}

	/** Get distortion equalizer bandwidth in Hertz. */
	public final float getEqualizerBandwidth()
	{
		return eqBandwidth;
	}

	/** Set distortion equalizer bandwidth in Hertz (80.0 to 24000.0). */
	public final void setEqualizerBandwidth(float eqBandwidth)
	{
		this.eqBandwidth = eqBandwidth;
		alext.alEffectf(getALId(), ALExt.AL_DISTORTION_EQBANDWIDTH, RMath.clampValue(eqBandwidth, ALExt.AL_DISTORTION_MIN_EQBANDWIDTH, ALExt.AL_DISTORTION_MAX_EQBANDWIDTH));
		errorCheck();
	}

	/** Get distortion equalizer centering in Hertz. */
	public final float getEqualizerCenter()
	{
		return eqCenter;
	}

	/** Set distortion equalizer centering in Hertz (80.0 to 24000.0). */
	public final void setEqualizerCenter(float eqCenter)
	{
		this.eqCenter = eqCenter;
		alext.alEffectf(getALId(), ALExt.AL_DISTORTION_EQCENTER, RMath.clampValue(eqCenter, ALExt.AL_DISTORTION_MIN_EQCENTER, ALExt.AL_DISTORTION_MAX_EQCENTER));
		errorCheck();
	}

	/** Get distortion low-pass cutoff in Hertz. */
	public final float getLowPassCutoff()
	{
		return lowPassCutoff;
	}

	/** Set distortion low-pass cutoff in Hertz (80.0 to 24000.0). */
	public final void setLowPassCutoff(float lowPassCutoff)
	{
		this.lowPassCutoff = lowPassCutoff;
		alext.alEffectf(getALId(), ALExt.AL_DISTORTION_LOWPASS_CUTOFF, RMath.clampValue(lowPassCutoff, ALExt.AL_DISTORTION_MIN_LOWPASS_CUTOFF, ALExt.AL_DISTORTION_MAX_LOWPASS_CUTOFF));
		errorCheck();
	}

}
