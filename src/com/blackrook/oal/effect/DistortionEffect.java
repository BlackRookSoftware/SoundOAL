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
package com.blackrook.oal.effect;

import com.blackrook.commons.math.RMath;
import com.blackrook.oal.OALEffect;
import com.blackrook.oal.enums.EffectType;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

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
	
	public DistortionEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.DISTORTION);
		setGain(0.05f);
		setEdge(0.2f);
		setLowPassCutoff(8000);
		setEqualizerCenter(3600);
		setEqualizerBandwidth(3600);
	}
	
	/** Get distortion gain. */
	public final float getGain()
	{
		return gain;
	}

	/** Get distortion edge. */
	public final float getEdge()
	{
		return edge;
	}

	/** Get distortion equalizer bandwidth in Hertz. */
	public final float getEqualizerBandwidth()
	{
		return eqBandwidth;
	}

	/** Get distortion equalizer centering in Hertz. */
	public final float getEqualizerCenter()
	{
		return eqCenter;
	}

	/** Get distortion low-pass cutoff in Hertz. */
	public final float getLowPassCutoff()
	{
		return lowPassCutoff;
	}

	/** Set distortion gain (0.01 to 1.0). */
	public final void setGain(float gain)
	{
		this.gain = gain;
		al.alEffectf(getALId(), AL.AL_DISTORTION_GAIN, RMath.clampValue(gain, 0.0f, 1.0f));
	}

	/** Set distortion edge (0.0 to 1.0). */
	public final void setEdge(float edge)
	{
		this.edge = edge;
		al.alEffectf(getALId(), AL.AL_DISTORTION_EDGE, RMath.clampValue(edge, 0.0f, 1.0f));
	}

	/** Set distortion equalizer bandwidth in Hertz (80.0 to 24000.0). */
	public final void setEqualizerBandwidth(float eqBandwidth)
	{
		this.eqBandwidth = eqBandwidth;
		al.alEffectf(getALId(), AL.AL_DISTORTION_EQBANDWIDTH, RMath.clampValue(eqBandwidth, 80.0f, 24000.0f));
	}

	/** Set distortion equalizer centering in Hertz (80.0 to 24000.0). */
	public final void setEqualizerCenter(float eqCenter)
	{
		this.eqCenter = eqCenter;
		al.alEffectf(getALId(), AL.AL_DISTORTION_EQCENTER, RMath.clampValue(eqCenter, 80.0f, 24000.0f));
	}

	/** Set distortion low-pass cutoff in Hertz (80.0 to 24000.0). */
	public final void setLowPassCutoff(float lowPassCutoff)
	{
		this.lowPassCutoff = lowPassCutoff;
		al.alEffectf(getALId(), AL.AL_DISTORTION_LOWPASS_CUTOFF, RMath.clampValue(lowPassCutoff, 80.0f, 24000.0f));
	}

}
