/*******************************************************************************
 * Copyright (c) 2014, 2015 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     Matt Tropiano - initial API and implementation
 *******************************************************************************/
package com.blackrook.oal;

import com.blackrook.oal.exception.SoundException;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;
import com.jogamp.openal.ALExt;

/**
 * Generic OpenAL object type. 
 * @author Matthew Tropiano
 */
public abstract class OALObject
{
	/** Link to AL instance. */
	protected OALSystem system;
	/** Link to AL instance. */
	protected AL al;
	/** Link to ALC instance. */
	protected ALC alc;
	/** Link to ALExt instance. */
	protected ALExt alext;
	
	/** This object's ALId. */
	private int alId;
	/** Was this object allocated? */
	private boolean allocated;

	/**
	 * Allocates a new OpenAL object. Calls allocate().
	 */
	public OALObject(OALSystem system)
	{
		this.system = system;
		this.al = system.getAL();
		this.alc = system.getALC();
		this.alext = system.getALExt();
		alId = alloc();
	}
	
	private final int alloc() throws SoundException 
	{
		clearError(); 
		int a = allocate(); 
		allocated = true; 
		system.addObjectReference(this);
		return a;
	}

	/**
	 * Returns this OALObject's OpenAL object id.
	 */
	public final int getALId()
	{
		return alId;
	}

	public boolean equals(OALObject obj) 
	{
		return getClass().equals(obj.getClass()) && alId == obj.alId;
	}

	/**
	 * Destroys this object.
	 * @throws SoundException
	 */
	public final void destroy() throws SoundException 
	{
		if (allocated)
		{
			free();
			system.removeObjectReference(this);
		}
		allocated = false;
	}

	/**	 
	 * Allocates a new type of this object in OpenAL.
	 * Called by OALObject constructor.
	 * @return	the ALId of this new object.
	 * @throws SoundException if the allocation cannot happen.
	 */
	protected abstract int allocate() throws SoundException;
	
	/**
	 * Destroys this object (deallocates it on OpenAL).
	 * This is called by destroy().
	 * @throws SoundException if the deallocation cannot happen.
	 */
	protected abstract void free() throws SoundException;
	
	/**
	 * Convenience method for clearing the OpenAL error state.
	 */
	protected void clearError()
	{
		while (al.alGetError() != AL.AL_NO_ERROR) ;
	}

	/**
	 * Convenience method for checking for an OpenAL error and throwing a SoundException
	 * if an error is raised. 
	 */
	protected final void errorCheck()
	{
		int error = al.alGetError();
		if (error != AL.AL_NO_ERROR)
			throw new SoundException("Object " + getClass().getSimpleName() + ": AL returned \"" + al.alGetString(error) + "\"");
	}
	
	/**
	 * Frees this object from OpenAL.
	 * Safe, since OpenAL is thread-safe.
	 * AS ALWAYS, NEVER CALL DIRECTLY. 
	 */
	@Override
	public void finalize() throws Throwable
	{
		destroy();
		super.finalize();
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append(' ');
		sb.append(getALId());
		return sb.toString();
	}

}
