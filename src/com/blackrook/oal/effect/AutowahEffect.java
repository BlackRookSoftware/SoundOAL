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
 * Autowah effect for sound sources.
 * @author Matthew Tropiano
 */
public class AutowahEffect extends OALEffect
{
	/** Autowah attack time in seconds. */
	protected float attackTime;
	/** Autowah release time in seconds. */
	protected float releaseTime;
	/** Autowah resonance factor. */
	protected float resonance;
	/** Autowah peak gain. */
	protected float peakGain;

	public AutowahEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_AUTOWAH);
		setAttackTime(ALExt.AL_AUTOWAH_DEFAULT_ATTACK_TIME);
		setReleaseTime(ALExt.AL_AUTOWAH_DEFAULT_RELEASE_TIME);
		setResonance(ALExt.AL_AUTOWAH_DEFAULT_RESONANCE);
		setPeakGain(ALExt.AL_AUTOWAH_DEFAULT_PEAK_GAIN);
	}

	/** Get autowah attack time in seconds. */
	public final float getAttackTime()
	{
		return attackTime;
	}

	/** Set autowah attack time in seconds (0.0001 to 1.0). */
	public final void setAttackTime(float attackTime)
	{
		this.attackTime = attackTime;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_ATTACK_TIME, RMath.clampValue(attackTime, ALExt.AL_AUTOWAH_MIN_ATTACK_TIME, ALExt.AL_AUTOWAH_MAX_ATTACK_TIME));
		errorCheck();
	}

	/** Autowah peak gain. */
	public final float getPeakGain()
	{
		return peakGain;
	}

	/** Set autowah peak gain (0.00003 to 31621.0). */
	public final void setPeakGain(float peakGain)
	{
		this.peakGain = peakGain;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_PEAK_GAIN, RMath.clampValue(peakGain, ALExt.AL_AUTOWAH_MIN_PEAK_GAIN, ALExt.AL_AUTOWAH_MAX_PEAK_GAIN));
		errorCheck();
	}

	/** Get autowah release time in seconds. */
	public final float getReleaseTime()
	{
		return releaseTime;
	}

	/** Set autowah release time in seconds (0.0001 to 1.0). */
	public final void setReleaseTime(float releaseTime)
	{
		this.releaseTime = releaseTime;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_RELEASE_TIME, RMath.clampValue(releaseTime, ALExt.AL_AUTOWAH_MIN_RELEASE_TIME, ALExt.AL_AUTOWAH_MAX_RELEASE_TIME));
		errorCheck();
	}

	/** Get autowah resonance factor. */
	public final float getResonance()
	{
		return resonance;
	}

	/** Set autowah resonance factor (2.0 to 1000.0). */
	public final void setResonance(float resonance)
	{
		this.resonance = resonance;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_RESONANCE, RMath.clampValue(resonance, ALExt.AL_AUTOWAH_MIN_RESONANCE, ALExt.AL_AUTOWAH_MAX_RESONANCE));
		errorCheck();
	}
	
}
