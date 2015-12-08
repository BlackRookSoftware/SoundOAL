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
		setAttackTime(0.06f);
		setReleaseTime(0.06f);
		setResonance(1000);
		setPeakGain(11.22f);
	}

	/** Get autowah attack time in seconds. */
	public final float getAttackTime()
	{
		return attackTime;
	}

	/** Autowah peak gain. */
	public final float getPeakGain()
	{
		return peakGain;
	}

	/** Get autowah release time in seconds. */
	public final float getReleaseTime()
	{
		return releaseTime;
	}

	/** Get autowah resonance factor. */
	public final float getResonance()
	{
		return resonance;
	}

	/** Set autowah attack time in seconds (0.0001 to 1.0). */
	public final void setAttackTime(float attackTime)
	{
		this.attackTime = attackTime;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_ATTACK_TIME, RMath.clampValue(attackTime, 0.0001f, 1.0f));
	}

	/** Set autowah peak gain (0.00003 to 31621.0). */
	public final void setPeakGain(float peakGain)
	{
		this.peakGain = peakGain;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_PEAK_GAIN, RMath.clampValue(peakGain, 0.00003f, 31621.0f));
	}

	/** Set autowah release time in seconds (0.0001 to 1.0). */
	public final void setReleaseTime(float releaseTime)
	{
		this.releaseTime = releaseTime;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_RELEASE_TIME, RMath.clampValue(releaseTime, 0.0001f, 1.0f));
	}

	/** Set autowah resonance factor (2.0 to 1000.0). */
	public final void setResonance(float resonance)
	{
		this.resonance = resonance;
		alext.alEffectf(getALId(), ALExt.AL_AUTOWAH_RESONANCE, RMath.clampValue(resonance, 2.0f, 1000.0f));
	}
}
