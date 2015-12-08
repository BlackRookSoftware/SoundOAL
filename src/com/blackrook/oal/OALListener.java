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

import com.blackrook.commons.math.geometry.Point3F;
import com.blackrook.commons.math.geometry.Vect3F;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

/**
 * The Listener in the sound environment.
 * @author Matthew Tropiano
 */
public class OALListener
{
	/** Link to AL instance. */
	protected AL al;
	/** Link to ALC instance. */
	protected ALC alc;
	
	/** Listener's position. */
	protected final Point3F position;
	/** Listener's velocity vector. */
	protected final Vect3F velocity;
	/** Listener's orientation vector (upward vector).*/
	protected final Vect3F orientationUp;
	/** Listener's orientation vector (facing direction).*/
	protected final Vect3F orientationFacing;

	/** Listener Gain (master gain).*/
	protected float gain;
	
	/** Is velocity automatically calculated? */
	protected boolean autoVelocity;
	
	/**
	 * Constructs a new Listener.
	 * Default position and velocity is (0,0,0), facing is -Z (0,0,-1), up is (0,1,0) (y-axis).
	 * Gain is 1.0.
	 * There should only be one of these in a sound system.
	 */
	OALListener(AL al, ALC alc)
	{
		this(al,alc, false);
	}
	
	/**
	 * Constructs a new Listener.
	 * Default position and velocity is (0,0,0), facing is -Z (0,0,-1), up is (0,1,0) (y-axis).
	 * Gain is 1.0.
	 * There should only be one of these in a sound system.
	 * @param autovel	should velocity automatically be calculated?
	 */
	OALListener(AL al, ALC alc, boolean autovel)
	{
		this.al = al;
		this.alc = alc;
		
		autoVelocity = autovel;
		
		position = new Point3F();
		velocity = new Vect3F();
		orientationUp = new Vect3F();
		orientationFacing = new Vect3F();
		gain = 1.0f;

		setPosition(0,0,0);
		setVelocity(0,0,0);
		setTop(0,1,0);
		setFacing(0,0,1);
		setGain(1f);
	}
	
	/**
	 * Sets the Listener's position attributes.
	 * @param x			the x value.
	 * @param y			the y value.
	 * @param z			the z value.
	 */
	public void setPosition(float x, float y, float z)
	{
		if (autoVelocity)
			setVelocity((float)(x-position.x), (float)(y-position.y), (float)(z-position.y));
		position.set(x,y,z);
		al.alListenerfv(AL.AL_POSITION,new float[]{(float)position.x, (float)position.y, (float)position.z},0);
	}

	/**
	 * Sets the Listener's velocity attributes.
	 * @param x			the x value.
	 * @param y			the y value.
	 * @param z			the z value.
	 */
	public void setVelocity(float x, float y, float z)
	{
		velocity.set(x,y,z);
		al.alListenerfv(AL.AL_VELOCITY,new float[]{(float)velocity.x, (float)velocity.y, (float)velocity.z},0);
	}

	/**
	 * Sets the Listener's facing attributes.
	 * @param x			the x value.
	 * @param y			the y value.
	 * @param z			the z value.
	 */
	public void setFacing(float x, float y, float z)
	{
		orientationFacing.set(x,y,z);
		al.alListenerfv(AL.AL_ORIENTATION, new float[]{
			(float)orientationFacing.x, (float)orientationFacing.y, (float)orientationFacing.z, 
			(float)orientationUp.x, (float)orientationUp.y, (float)orientationUp.z}, 
			0);
	}

	/**
	 * Sets the Listener's top-orientation.
	 * @param x			the x value.
	 * @param y			the y value.
	 * @param z			the z value.
	 */
	public void setTop(float x, float y, float z)
	{
		orientationUp.set(x,y,z);
		al.alListenerfv(AL.AL_ORIENTATION, new float[]{
			(float)orientationFacing.x, (float)orientationFacing.y, (float)orientationFacing.z, 
			(float)orientationUp.x, (float)orientationUp.y, (float)orientationUp.z}, 
			0);
	}

	/**
	 * Sets the master gain for this Listener.
	 * @param f		the new gain.
	 */
	public void setGain(float f)
	{
		gain = f;
		al.alListenerf(AL.AL_GAIN, gain);
	}
	
	/**
	 * Enables/Disables automatic velocity calculation.
	 * @param autovel	should velocity automatically be calculated?
	 */
	public void setAutoVelocity(boolean autovel)
	{
		autoVelocity = autovel;
	}

	/**
	 * Returns a reference to the Point3D containing the listener's position in space.
	 */
	public Point3F getPosition()
	{
		return position;
	}

	/**
	 * Returns a reference to the Vect3D containing the listener's velocity.
	 */
	public Vect3F getVelocity()
	{
		return velocity;
	}

	/**
	 * Returns a reference to the Vect3D containing the listener's upward orientation.
	 */
	public Vect3F getOrientationUp()
	{
		return orientationUp;
	}

	/**
	 * Returns a reference to the Vect3D containing the listener's facing orientation.
	 */
	public Vect3F getOrientationFacing()
	{
		return orientationFacing;
	}

	/**
	 * Returns this listener's gain.
	 */
	public float getGain()
	{
		return gain;
	}

}
