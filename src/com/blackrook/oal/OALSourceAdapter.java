/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal;

/**
 * Adapter class with methods that do nothing for easy
 * listener implementation.
 * @author Matthew Tropiano
 */
public class OALSourceAdapter implements OALSourceListener
{

	@Override
	public void sourceBufferDequeued(OALSource source, OALBuffer buffer)
	{
	}

	@Override
	public void sourceBufferEnqueued(OALSource source, OALBuffer buffer)
	{
	}

	@Override
	public void sourcePaused(OALSource source)
	{
	}

	@Override
	public void sourcePlayed(OALSource source)
	{
	}

	@Override
	public void sourceRewound(OALSource source)
	{
	}

	@Override
	public void sourceStopped(OALSource source)
	{
	}
}
