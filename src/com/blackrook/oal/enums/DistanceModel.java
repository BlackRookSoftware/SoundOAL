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
package com.blackrook.oal.enums;

import com.jogamp.openal.AL;

/**
 * Distance Model enumeration for internal OpenAL distance models for attenuating
 * the final gain of a Source in relation to the position/direction of the listener.
 * @author Matthew Tropiano
 */
public enum DistanceModel
{
	NONE(AL.AL_NONE),
	INVERSE_DISTANCE(AL.AL_INVERSE_DISTANCE),
	INVERSE_DISTANCE_CLAMPED(AL.AL_INVERSE_DISTANCE_CLAMPED),
	LINEAR_DISTANCE(AL.AL_LINEAR_DISTANCE),
	LINEAR_DISTANCE_CLAMPED(AL.AL_LINEAR_DISTANCE_CLAMPED),
	EXPONENT_DISTANCE(AL.AL_EXPONENT_DISTANCE),
	EXPONENT_DISTANCE_CLAMPED(AL.AL_EXPONENT_DISTANCE_CLAMPED);
	
	public final int alVal;
	
	private DistanceModel(int val) 
	{alVal = val;}

}
