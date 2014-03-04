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
 * Sound format enumeration.
 * @author Matthew Tropiano
 */
public enum SoundFormat
{
	MONO8(AL.AL_FORMAT_MONO8,8,1),
	MONO16(AL.AL_FORMAT_MONO16,16,1),
	STEREO8(AL.AL_FORMAT_STEREO8,8,2),
	STEREO16(AL.AL_FORMAT_STEREO16,16,2);
	
	public final int alVal;
	/** Sample resolution. */
	public final int bits;
	/** Number of channels. */
	public final int channels;
	
	private SoundFormat(int val, int bits, int channels) 
	{alVal = val; this.bits = bits; this.channels = channels;}

	@Override
	public String toString()
	{
		return bits + "-bit, " + channels + " ch.";
	}
	
}
