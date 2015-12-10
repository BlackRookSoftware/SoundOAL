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
import com.jogamp.openal.AL;
import com.jogamp.openal.ALExt;

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
	protected boolean hfLimit;
	
	/**
	 * Constructs a new ReverbEffect object with default settings. 
	 */
	public ReverbEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_REVERB);
		setDensity(ALExt.AL_REVERB_DEFAULT_DENSITY);
		setDiffusion(ALExt.AL_REVERB_DEFAULT_DIFFUSION);
		setGain(ALExt.AL_REVERB_DEFAULT_GAIN);
		setHFGain(ALExt.AL_REVERB_DEFAULT_GAINHF);
		setDecayTime(ALExt.AL_REVERB_DEFAULT_DECAY_TIME);
		setDecayHFRatio(ALExt.AL_REVERB_DEFAULT_DECAY_HFRATIO);
		setReflectionGain(ALExt.AL_REVERB_DEFAULT_REFLECTIONS_GAIN);
		setReflectionDelay(ALExt.AL_REVERB_DEFAULT_REFLECTIONS_DELAY);
		setLateGain(ALExt.AL_REVERB_DEFAULT_LATE_REVERB_GAIN);
		setLateDelay(ALExt.AL_REVERB_DEFAULT_LATE_REVERB_DELAY);
		setAirAbsorptionGainHF(ALExt.AL_REVERB_DEFAULT_AIR_ABSORPTION_GAINHF);
		setRoomRolloffFactor((float)ALExt.AL_REVERB_DEFAULT_ROOM_ROLLOFF_FACTOR);
		setDecayHFLimit(true);
	}

	/** Get reverb high-frequency air absorption gain. */
	public final float getAirAbsorptionGainHF()
	{
		return airAbsorptionGainHF;
	}

	/** Set reverb high-frequency air absorption gain (0.892 to 1.0). */
	public final void setAirAbsorptionGainHF(float airAbsorptionGainHF)
	{
		this.airAbsorptionGainHF = airAbsorptionGainHF;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_AIR_ABSORPTION_GAINHF, RMath.clampValue(airAbsorptionGainHF, ALExt.AL_REVERB_MIN_AIR_ABSORPTION_GAINHF, ALExt.AL_REVERB_MAX_AIR_ABSORPTION_GAINHF));
		errorCheck();
	}

	/** Get reverb high-frequency ratio. */
	public final float getDecayHFRatio()
	{
		return decayHFRatio;
	}

	/** Get reverb high-frequency ratio (0.1 to 2.0). */
	public final void setDecayHFRatio(float decayHFRatio)
	{
		this.decayHFRatio = decayHFRatio;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_DECAY_HFRATIO, RMath.clampValue(decayHFRatio, ALExt.AL_REVERB_MIN_DECAY_HFRATIO, ALExt.AL_REVERB_MAX_DECAY_HFRATIO));
		errorCheck();
	}

	/** Get reverb decay time in seconds. */
	public final float getDecayTime()
	{
		return decayTime;
	}

	/** Set reverb decay time in seconds (0.1 to 20.0). */
	public final void setDecayTime(float decayTime)
	{
		this.decayTime = decayTime;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_DECAY_TIME, RMath.clampValue(decayTime, ALExt.AL_REVERB_MIN_DECAY_TIME, ALExt.AL_REVERB_MAX_DECAY_TIME));
		errorCheck();
	}

	/** Get reverb density factor. */
	public final float getDensity()
	{
		return density;
	}

	/** Set reverb density factor (0.0 to 1.0). */
	public final void setDensity(float density)
	{
		this.density = density;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_DENSITY, RMath.clampValue(density, (float)ALExt.AL_REVERB_MIN_DENSITY, ALExt.AL_REVERB_MAX_DENSITY));
		errorCheck();
	}

	/** Get reverb diffusion factor. */
	public final float getDiffusion()
	{
		return diffusion;
	}

	/** Set reverb diffusion factor (0.0 to 1.0). */
	public final void setDiffusion(float diffusion)
	{
		this.diffusion = diffusion;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_DIFFUSION, RMath.clampValue(diffusion, (float)ALExt.AL_REVERB_MIN_DIFFUSION, ALExt.AL_REVERB_MAX_DIFFUSION));
		errorCheck();
	}

	/** Get reverb gain. */
	public final float getGain()
	{
		return gain;
	}

	/** Set reverb gain (0.0 to 1.0). */
	public final void setGain(float gain)
	{
		this.gain = gain;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_GAIN, RMath.clampValue(gain, (float)ALExt.AL_REVERB_MIN_GAIN, ALExt.AL_REVERB_MAX_GAIN));
		errorCheck();
	}

	/** Get reverb high-frequency gain. */
	public final float getHFGain()
	{
		return gainHF;
	}

	/** Set reverb high-frequency gain (0.0 to 1.0). */
	public final void setHFGain(float gainHF)
	{
		this.gainHF = gainHF;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_GAINHF, RMath.clampValue(gainHF, (float)ALExt.AL_REVERB_MIN_GAINHF, ALExt.AL_REVERB_MAX_GAINHF));
		errorCheck();
	}

	/** Is the reverb decay high-frequency limit set? */
	public final boolean isDecayHFLimit()
	{
		return hfLimit;
	}

	/** Sets the reverb decay high-frequency limit. True = limit on, false = off. */
	public final void setDecayHFLimit(boolean limit)
	{
		hfLimit = limit;
		alext.alEffecti(getALId(), ALExt.AL_REVERB_DECAY_HFLIMIT, limit ? AL.AL_TRUE : AL.AL_FALSE);
		errorCheck();
	}

	/** Get reverb reflection delay in seconds. */
	public final float getReflectionDelay()
	{
		return reflectionDelay;
	}

	/** Set reverb reflection delay in seconds (0.0 to 0.3). */
	public final void setReflectionDelay(float reflectionDelay)
	{
		this.reflectionDelay = reflectionDelay;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_REFLECTIONS_DELAY, RMath.clampValue(reflectionDelay, (float)ALExt.AL_REVERB_MIN_REFLECTIONS_DELAY, ALExt.AL_REVERB_MAX_REFLECTIONS_DELAY));
		errorCheck();
	}

	/** Set reverb reflection gain. */
	public final float getReflectionGain()
	{
		return reflectionGain;
	}

	/** Set reverb reflection gain (0.0 to 3.16). */
	public final void setReflectionGain(float reflectionGain)
	{
		this.reflectionGain = reflectionGain;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_REFLECTIONS_GAIN, RMath.clampValue(reflectionGain, (float)ALExt.AL_REVERB_MIN_REFLECTIONS_GAIN, ALExt.AL_REVERB_MAX_REFLECTIONS_GAIN));
		errorCheck();
	}

	/** Get reverb room rolloff factor. */
	public final float getRoomRolloffFactor()
	{
		return roomRolloffFactor;
	}

	/** Set reverb room rolloff factor (0.0 to 10.0). */
	public final void setRoomRolloffFactor(float roomRolloffFactor)
	{
		this.roomRolloffFactor = roomRolloffFactor;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_ROOM_ROLLOFF_FACTOR, RMath.clampValue(roomRolloffFactor, (float)ALExt.AL_REVERB_MIN_ROOM_ROLLOFF_FACTOR, ALExt.AL_REVERB_MAX_ROOM_ROLLOFF_FACTOR));
		errorCheck();
	}

	/** Get late reverb delay. */
	public final float getLateDelay()
	{
		return lateDelay;
	}

	/** Set late reverb delay (0.0 to 0.1). */
	public final void setLateDelay(float lateDelay)
	{
		this.lateDelay = lateDelay;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_LATE_REVERB_DELAY, RMath.clampValue(lateDelay, (float)ALExt.AL_REVERB_MIN_LATE_REVERB_DELAY, ALExt.AL_REVERB_MAX_LATE_REVERB_DELAY));
		errorCheck();
	}

	/** Get late reverb gain. */
	public final float getLateGain()
	{
		return lateGain;
	}

	/** Set late reverb gain (0.0 to 10.0). */
	public final void setLateGain(float lateGain)
	{
		this.lateGain = lateGain;
		alext.alEffectf(getALId(), ALExt.AL_REVERB_LATE_REVERB_GAIN, RMath.clampValue(lateGain, (float)ALExt.AL_REVERB_MIN_LATE_REVERB_GAIN, ALExt.AL_REVERB_MAX_LATE_REVERB_GAIN));
		errorCheck();
	}

}
