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
 * Pitch shifter effect for sources.
 * @author Matthew Tropiano
 */
public class PitchShiftEffect extends OALEffect
{
	/** Pitch shifter coarse tuning in semitones. */
	protected int coarse;
	/** Pitch shifter fine tuning in cents. */
	protected int fine;
	
	public PitchShiftEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.PITCH_SHIFT);
		setCoarseTuning(12);
		setFineTuning(0);
	}

	/** Pitch shifter coarse tuning in semitones. */
	public final int getCoarseTuning()
	{
		return coarse;
	}

	/** Pitch shifter fine tuning in cents. */
	public final int getFineTuning()
	{
		return fine;
	}

	/** Pitch shifter coarse tuning in semitones (-12 to 12). */
	public final void setCoarseTuning(int coarse)
	{
		this.coarse = coarse;
		al.alEffecti(getALId(), AL.AL_PITCH_SHIFTER_COARSE_TUNE, RMath.clampValue(coarse, -12, 12));
	}

	/** Pitch shifter fine tuning in cents (-50 to 50). */
	public final void setFineTuning(int fine)
	{
		this.fine = fine;
		al.alEffecti(getALId(), AL.AL_PITCH_SHIFTER_FINE_TUNE, RMath.clampValue(fine, -50, 50));
	}
	
}
