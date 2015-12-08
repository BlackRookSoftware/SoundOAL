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
 * Echo effect for sound sources.
 * @author Matthew Tropiano
 */
public class EchoEffect extends OALEffect
{
	/** Echo delay in seconds. */
	protected float delay;
	/** Echo LR delay in seconds. */
	protected float lrDelay;
	/** Echo damping. */
	protected float damping;
	/** Echo feedback. */
	protected float feedback;
	/** Echo spread. */
	protected float spread;
	
	public EchoEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_ECHO);
		setDelay(0.1f);
		setLRDelay(0.1f);
		setDamping(0.5f);
		setFeedback(0.5f);
		setSpread(-1);
	}
	
	/**
	 * Gets this effect's delay in seconds.
	 */
	public final float getDelay()
	{
		return delay;
	}

	/**
	 * Gets this effect's LR delay in seconds.
	 */
	public final float getLRDelay()
	{
		return lrDelay;
	}

	/**
	 * Gets this effect's feedback.
	 */
	public final float getFeedback()
	{
		return feedback;
	}

	/**
	 * Gets this effect's damping.
	 */
	public final float getDamping()
	{
		return damping;
	}

	/**
	 * Gets this effect's spread.
	 */
	public final float getSpread()
	{
		return spread;
	}

	/**
	 * Sets this effect's spread (-1.0 to 1.0).
	 */
	public final void setSpread(float spread)
	{
		this.spread = spread;
		alext.alEffectf(getALId(), ALExt.AL_ECHO_SPREAD, RMath.clampValue(spread, -1.0f, 1.0f));
	}

	/**
	 * Sets this effect's feedback (0.0 to 1.0).
	 */
	public final void setFeedback(float feedback)
	{
		this.feedback = feedback;
		alext.alEffectf(getALId(), ALExt.AL_ECHO_FEEDBACK, RMath.clampValue(feedback, 0.0f, 1.0f));
	}

	/**
	 * Sets this effect's damping (0.0 to 0.99).
	 */
	public final void setDamping(float damping)
	{
		this.damping = damping;
		alext.alEffectf(getALId(), ALExt.AL_ECHO_DAMPING, RMath.clampValue(damping, 0.0f, 0.99f));
	}

	/**
	 * Sets this effect's delay in seconds (0.0 to 0.207).
	 */
	public final void setDelay(float delay)
	{
		this.delay = delay;
		alext.alEffectf(getALId(), ALExt.AL_ECHO_DELAY, RMath.clampValue(delay, 0.0f, 0.207f));
	}

	/**
	 * Sets this effect's LR delay in seconds (0.0 to 0.404).
	 */
	public final void setLRDelay(float lrDelay)
	{
		this.lrDelay = lrDelay;
		alext.alEffectf(getALId(), ALExt.AL_ECHO_LRDELAY, RMath.clampValue(lrDelay, 0.0f, 0.404f));
	}


	
}
