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
 * Reverb effect for sound and stuff.
 * @author Matthew Tropiano
 */
public class ReverbEffect extends OALEffect
{
	/** Reverb density factor. */
	protected float density;
	/** Reverb diffusion factor. */
	protected float diffusion;
	/** Reverb gain. */
	protected float gain;
	/** Reverb high-frequency gain. */
	protected float gainHF;
	/** Reverb decay time in seconds. */
	protected float decayTime;
	/** Reverb high-frequency ratio. */
	protected float decayHFRatio;
	/** Reverb reflection gain. */
	protected float reflectionGain;
	/** Reverb reflection delay in seconds. */
	protected float reflectionDelay;
	/** Late reverb gain. */
	protected float lateGain;
	/** Late reverb delay. */
	protected float lateDelay;
	/** Reverb high-frequency air absorption gain. */
	protected float airAbsorptionGainHF;
	/** Reverb room rolloff factor. */
	protected float roomRolloffFactor;
	/** Reverb decay high-frequency limit? */
	protected boolean HFLimit;
	
	/**
	 * Constructs a new ReverbEffect object with default settings. 
	 */
	public ReverbEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.REVERB);
		setDensity(1);
		setDiffusion(1);
		setGain(0.32f);
		setHFGain(0.89f);
		setDecayTime(1.49f);
		setDecayHFRatio(0.83f);
		setReflectionGain(0.05f);
		setReflectionDelay(0.007f);
		setLateGain(1.26f);
		setLateDelay(0.011f);
		setAirAbsorptionGainHF(0.994f);
		setRoomRolloffFactor(0);
		setHFLimit(true);
	}

	/** Get reverb high-frequency air absorption gain. */
	public final float getAirAbsorptionGainHF()
	{
		return airAbsorptionGainHF;
	}

	/** Get reverb high-frequency ratio. */
	public final float getDecayHFRatio()
	{
		return decayHFRatio;
	}

	/** Get reverb decay time in seconds. */
	public final float getDecayTime()
	{
		return decayTime;
	}

	/** Get reverb density factor. */
	public final float getDensity()
	{
		return density;
	}

	/** Get reverb diffusion factor. */
	public final float getDiffusion()
	{
		return diffusion;
	}

	/** Get reverb gain. */
	public final float getGain()
	{
		return gain;
	}

	/** Get reverb high-frequency gain. */
	public final float getGainHF()
	{
		return gainHF;
	}

	/** Is the reverb decay high-frequency limit set? */
	public final boolean isHFLimitSet()
	{
		return HFLimit;
	}

	/** Get reverb reflection delay in seconds. */
	public final float getReflectionDelay()
	{
		return reflectionDelay;
	}

	/** Set reverb reflection gain. */
	public final float getReflectionGain()
	{
		return reflectionGain;
	}

	/** Get reverb room rolloff factor. */
	public final float getRoomRolloffFactor()
	{
		return roomRolloffFactor;
	}

	/** Get late reverb delay. */
	public final float getLateDelay()
	{
		return lateDelay;
	}

	/** Get late reverb gain. */
	public final float getLateGain()
	{
		return lateGain;
	}

	/** Set reverb high-frequency air absorption gain (0.892 to 1.0). */
	public final void setAirAbsorptionGainHF(float airAbsorptionGainHF)
	{
		this.airAbsorptionGainHF = airAbsorptionGainHF;
		al.alEffectf(getALId(), AL.AL_REVERB_AIR_ABSORPTION_GAINHF, RMath.clampValue(airAbsorptionGainHF, 0.892f, 1.0f));
	}

	/** Get reverb high-frequency ratio (0.1 to 2.0). */
	public final void setDecayHFRatio(float decayHFRatio)
	{
		this.decayHFRatio = decayHFRatio;
		al.alEffectf(getALId(), AL.AL_REVERB_DECAY_HFRATIO, RMath.clampValue(decayHFRatio, 0.1f, 2.0f));
	}

	/** Set reverb decay time in seconds (0.1 to 20.0). */
	public final void setDecayTime(float decayTime)
	{
		this.decayTime = decayTime;
		al.alEffectf(getALId(), AL.AL_REVERB_DECAY_TIME, RMath.clampValue(decayTime, 0.1f, 20.0f));
	}

	/** Set reverb density factor (0.0 to 1.0). */
	public final void setDensity(float density)
	{
		this.density = density;
		al.alEffectf(getALId(), AL.AL_REVERB_DENSITY, RMath.clampValue(density, 0.0f, 1.0f));
	}

	/** Set reverb diffusion factor (0.0 to 1.0). */
	public final void setDiffusion(float diffusion)
	{
		this.diffusion = diffusion;
		al.alEffectf(getALId(), AL.AL_REVERB_DIFFUSION, RMath.clampValue(diffusion, 0.0f, 1.0f));
	}

	/** Set reverb gain (0.0 to 1.0). */
	public final void setGain(float gain)
	{
		this.gain = gain;
		al.alEffectf(getALId(), AL.AL_REVERB_GAIN, RMath.clampValue(gain, 0.0f, 1.0f));
	}

	/** Set reverb high-frequency gain (0.0 to 1.0). */
	public final void setHFGain(float gainHF)
	{
		this.gainHF = gainHF;
		al.alEffectf(getALId(), AL.AL_REVERB_GAINHF, RMath.clampValue(gainHF, 0.0f, 1.0f));
	}

	/** Sets the reverb decay high-frequency limit. True = limit on, false = off. */
	public final void setHFLimit(boolean limit)
	{
		HFLimit = limit;
		al.alEffectf(getALId(), AL.AL_REVERB_DECAY_HFLIMIT, limit?AL.AL_TRUE:AL.AL_FALSE);
	}

	/** Set late reverb delay (0.0 to 0.1). */
	public final void setLateDelay(float lateDelay)
	{
		this.lateDelay = lateDelay;
		al.alEffectf(getALId(), AL.AL_REVERB_LATE_REVERB_DELAY, RMath.clampValue(lateDelay, 0.0f, 0.1f));
	}

	/** Set late reverb gain (0.0 to 10.0). */
	public final void setLateGain(float lateGain)
	{
		this.lateGain = lateGain;
		al.alEffectf(getALId(), AL.AL_REVERB_LATE_REVERB_GAIN, RMath.clampValue(lateGain, 0.0f, 10.0f));
	}

	/** Set reverb reflection delay in seconds (0.0 to 0.3). */
	public final void setReflectionDelay(float reflectionDelay)
	{
		this.reflectionDelay = reflectionDelay;
		al.alEffectf(getALId(), AL.AL_REVERB_REFLECTIONS_DELAY, RMath.clampValue(reflectionDelay, 0.0f, 0.3f));
	}

	/** Set reverb reflection gain (0.0 to 3.16). */
	public final void setReflectionGain(float reflectionGain)
	{
		this.reflectionGain = reflectionGain;
		al.alEffectf(getALId(), AL.AL_REVERB_REFLECTIONS_GAIN, RMath.clampValue(reflectionGain, 0.0f, 3.16f));
	}

	/** Set reverb room rolloff factor (0.0 to 10.0). */
	public final void setRoomRolloffFactor(float roomRolloffFactor)
	{
		this.roomRolloffFactor = roomRolloffFactor;
		al.alEffectf(getALId(), AL.AL_REVERB_ROOM_ROLLOFF_FACTOR, RMath.clampValue(roomRolloffFactor, 0.0f, 10.0f));
	}

}
