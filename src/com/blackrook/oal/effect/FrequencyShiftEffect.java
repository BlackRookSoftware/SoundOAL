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
 * Frequency shift effects for sources.
 * @author Matthew Tropiano
 */
public class FrequencyShiftEffect extends OALEffect
{
	/** Shift direction enumeration. */
	public static enum Direction
	{
		DOWN(AL.AL_FREQUENCY_SHIFTER_DIRECTION_DOWN),
		UP(AL.AL_FREQUENCY_SHIFTER_DIRECTION_UP),
		OFF(AL.AL_FREQUENCY_SHIFTER_DIRECTION_OFF);
		
		final int alVal;
		private Direction(int alVal) {this.alVal = alVal;}
	}
	
	/** Frequency shifter frequency. */
	protected float frequency;
	/** Frequency shifter left direction. */
	protected Direction leftDir;
	/** Frequency shifter right direction. */
	protected Direction rightDir;
	
	public FrequencyShiftEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.FREQUENCY_SHIFT);
		setFrequency(0);
		setLeftDirection(Direction.DOWN);
		setRightDirection(Direction.DOWN);
	}

	/** Get frequency shifter frequency. */
	public final float getFrequency()
	{
		return frequency;
	}

	/** Get frequency shifter left direction. */
	public final Direction getLeftDir()
	{
		return leftDir;
	}

	/** Get frequency shifter right direction. */
	public final Direction getRightDir()
	{
		return rightDir;
	}

	/** Set frequency shifter frequency (0.0 to 24000.0). */
	public final void setFrequency(float frequency)
	{
		this.frequency = frequency;
		al.alEffectf(getALId(), AL.AL_FREQUENCY_SHIFTER_FREQUENCY, RMath.clampValue(frequency, 0.0f, 24000.0f));
	}

	/** 
	 * Set frequency shifter left direction.
	 * @param leftDir	the direction type. 
	 */
	public final void setLeftDirection(Direction leftDir)
	{
		this.leftDir = leftDir;
		al.alEffecti(getALId(), AL.AL_FREQUENCY_SHIFTER_LEFT_DIRECTION, leftDir.alVal);
	}

	/** 
	 * Set frequency shifter right direction.
	 * @param rightDir	the direction type. 
	 */
	public final void setRightDirection(Direction rightDir)
	{
		this.rightDir = rightDir;
		al.alEffecti(getALId(), AL.AL_FREQUENCY_SHIFTER_RIGHT_DIRECTION, rightDir.alVal);
	}
	
	
	
}
