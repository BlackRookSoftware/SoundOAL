/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal.enums;

import com.jogamp.openal.AL;

/**
 * Enumeration of Effect types.
 * @author Matthew Tropiano
 */
public enum EffectType
{
	AUTOWAH(AL.AL_EFFECT_AUTOWAH),
	CHORUS(AL.AL_EFFECT_CHORUS),
	COMPRESSOR(AL.AL_EFFECT_COMPRESSOR),
	DISTORTION(AL.AL_EFFECT_DISTORTION),
	ECHO(AL.AL_EFFECT_ECHO),
	EQUALIZER(AL.AL_EFFECT_EQUALIZER),
	FLANGER(AL.AL_EFFECT_FLANGER),
	FREQUENCY_SHIFT(AL.AL_EFFECT_FREQUENCY_SHIFTER),
	PITCH_SHIFT(AL.AL_EFFECT_PITCH_SHIFTER),
	REVERB(AL.AL_EFFECT_REVERB),
	RING_MODULATOR(AL.AL_EFFECT_RING_MODULATOR),
	VOCAL_MORPHER(AL.AL_EFFECT_VOCAL_MORPHER);
	
	public final int alVal;
	private EffectType(int al) {alVal = al;}
	
}
