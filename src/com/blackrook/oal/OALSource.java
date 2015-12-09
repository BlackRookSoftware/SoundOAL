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

import com.blackrook.commons.Common;
import com.blackrook.commons.linkedlist.Queue;
import com.blackrook.commons.list.List;
import com.blackrook.commons.math.RMath;
import com.blackrook.oal.exception.*;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALExt;

/**
 * Encapsulating class for OpenAL sources.
 * @author Matthew Tropiano
 */
public final class OALSource extends OALObject
{
	public static final boolean AUTO_VELOCITY = true;
	public static final boolean NO_AUTO_VELOCITY = false;

	/** Source's position. */
	private float[] position;
	/** Source's velocity vector. */
	private float[] velocity;
	/** Source's direction vector. */
	private float[] direction;
	
	/** Source's pitch. */
	private float pitch;
	/** Source's gain. */
	private float gain;
	/** Source's minimum gain. */
	private float minGain;
	/** Source's maximum gain. */
	private float maxGain;

	/** Source's rolloff factor. */
	private float rolloff;
	/** Source's reference distance for gain attenuation. */
	private float referenceDistance;
	/** Source's maximum distance for gain attenuation. */
	private float maxDistance;
	
	/** Source's inner cone angle in degrees. */
	private float innerCone;
	/** Source's outer cone angle in degrees. */
	private float outerCone;
	/** Source's cone outer gain. */
	private float outerConeGain;
	
	/** Is this a looping source? */
	private boolean looping;
	/** Is this source relative to the listener? */
	private boolean relative;
	
	/** Is velocity automatically calculated? */
	private boolean autoVelocity;

	/** Singley-bound buffer. */
	private OALBuffer buffer;
	/** Buffer queue. */
	private Queue<OALBuffer> bufferQueue;
	
	/** Effect slots. */
	private int effectSlots;
	/** Auxilliary effect slot array. */
	private OALEffectSlot[] auxEffectSlots;
	/** Auxilliary effect slot array. */
	private OALFilter[] auxEffectSlotFilters;
	/** Direct filter bound to this source. */
	private OALFilter dryFilter;
	
	/** Source listeners. */
	private List<OALSourceListener> sourceListeners; 
	
	// ======= Temp data for value retrieval.
	private int[] STATE_DEQUEUE = new int[1];
	private int[] STATE_ENQUEUE = new int[1];
	private int[] STATE_BUFFER = new int[1];
	
	/**
	 * Creates a new source object.
	 * @param autovel should velocity automatically be calculated if position changes?
	 * @param effectSlots the effect slots to create.
	 * @throws SoundException if an OpenAL source cannot be allocated.
	 */
	OALSource(OALSystem system, boolean autovel, int effectSlots)
	{
		super(system);
		this.buffer = null;
		this.bufferQueue = new Queue<OALBuffer>();
		this.sourceListeners = new List<OALSourceListener>(3);
		this.autoVelocity = autovel;

		this.position = new float[3];
		this.velocity = new float[3];
		this.direction = new float[3];
		this.effectSlots = effectSlots;
		this.auxEffectSlots = new OALEffectSlot[effectSlots];
		this.auxEffectSlotFilters = new OALFilter[effectSlots];

		reset();
	}

	@Override
	protected final int allocate()
	{
		int[] STATE_NUMBER = new int[1];
		al.alGenSources(1, STATE_NUMBER, 0);
		errorCheck();
		return STATE_NUMBER[0];
	}
	
	/**
	 * Destroys this sound source (BUT NOT ATTACHED BUFFER(s)).
	 */
	@Override
	protected final void free()
	{
		if (isPlaying()) 
			stop();
		setBuffer(null);
		int[] STATE_NUMBER = new int[1];
		STATE_NUMBER[0] = getALId();
		al.alDeleteSources(1, STATE_NUMBER, 0);
		errorCheck();
	}

	/**
	 * Sets this Source's settings to defaults, and releases all buffers bound to it.
	 */
	public void reset()
	{
		setPosition(0, 0, 0);
		setVelocity(0, 0, 0);
		setDirection(0, 0, 0);
		setAutoVelocity(false);
		setLooping(false);
		setRelative(false);
		setPitch(1.0f);
		setGain(1.0f);
		setRolloff(1.0f);
		setFilter(null);
		setMinGain(0.0f);
		setMaxGain(1.0f);
		setReferenceDistance(1.0f);
		setMaxDistance(Float.MAX_VALUE);
		setInnerConeAngle(360f);
		setOuterConeAngle(360f);
		setOuterConeGain(0f);
		for (int i = 0; i < effectSlots; i++)
			setEffectSlot(i, null, null);
		setFilter(null);
		setBuffer(null);
	}
	
	/**
	 * Sets an auxiliary effect in a particular slot (nullifies the filter slot).
	 * @param slot	the slot to add this to. 
	 * @param eSlot	the effect slot to add (can be null).
	 */
	public void setEffectSlot(int slot, OALEffectSlot eSlot)
	{
		setEffectSlot(slot, eSlot, null);
	}
	
	/**
	 * Sets a filter in a particular aux slot (nullifies the effect slot).
	 * @param slot	the slot to add this to. 
	 * @param filt	the filter to add (can be null).
	 */
	public void setEffectSlotFilter(int slot, OALFilter filt)
	{
		setEffectSlot(slot, null, filt);
	}
	
	/**
	 * Sets an auxiliary effect in a particular slot.
	 * @param slot the slot to add this to. 
	 * @param effectSlot the effect slot to add (can be null).
	 * @param wetFilter	the filter to attach to this effect (can be null).
	 */
	public void setEffectSlot(int slot, OALEffectSlot effectSlot, OALFilter wetFilter)
	{
		al.alSource3i(
			getALId(), 
			ALExt.AL_AUXILIARY_SEND_FILTER, 
			effectSlot == null ? ALExt.AL_EFFECTSLOT_NULL : effectSlot.getALId(), 
			slot,
			wetFilter == null ? ALExt.AL_FILTER_NULL : wetFilter.getALId()
		);
		errorCheck();
		auxEffectSlots[slot] = effectSlot;
		auxEffectSlotFilters[slot] = wetFilter;
	}
	
	/**
	 * Sets the "dry" filter to use for the dry signal for the effects later.
	 * @param dryFilter the filter to use (can be null).
	 */
	public void setFilter(OALFilter dryFilter)
	{
		this.dryFilter = dryFilter;
		al.alSourcei(getALId(), ALExt.AL_DIRECT_FILTER, dryFilter == null ? ALExt.AL_FILTER_NULL : dryFilter.getALId());
		errorCheck();
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
			setVelocity(x - position[0], y - position[1], z - position[2]);

		position[0] = x;
		position[1] = y;
		position[2] = z;
		al.alSourcefv(getALId(), AL.AL_POSITION, position, 0);
		errorCheck();
	}

	/**
	 * Sets the Source's position attributes using an array of values. Values are copied.
	 * @param f			the float array.
	 */
	public void setPosition(float[] f)
	{
		if (autoVelocity)
			setVelocity(f[0]-position[0],f[1]-position[1],f[2]-position[2]);

		System.arraycopy(f, 0, position, 0, Math.min(f.length, 3));
		al.alSourcefv(getALId(), AL.AL_POSITION, position, 0);
		errorCheck();
	}
	
	/**
	 * Sets the Source's velocity attributes.
	 * @param x			the x value.
	 * @param y			the y value.
	 * @param z			the z value.
	 */
	public void setVelocity(float x, float y, float z)
	{
		velocity[0] = x;
		velocity[1] = y;
		velocity[2] = z;
		al.alSourcefv(getALId(), AL.AL_VELOCITY, velocity, 0);
		errorCheck();
	}

	/**
	 * Sets the Source's velocity attributes using an array of values. Values are copied.
	 * @param f			the float array.
	 */
	public void setVelocity(float[] f)
	{
		System.arraycopy(f,0,velocity,0,Math.min(f.length,3));
		al.alSourcefv(getALId(), AL.AL_VELOCITY, velocity, 0);
		errorCheck();
	}
		
	/**
	 * Sets the Source's direction attributes.
	 * @param x			the x value.
	 * @param y			the y value.
	 * @param z			the z value.
	 */
	public void setDirection(float x, float y, float z)
	{
		direction[0] = x;
		direction[1] = y;
		direction[2] = z;
		al.alSourcefv(getALId(), AL.AL_DIRECTION, direction, 0);
		errorCheck();
	}

	/**
	 * Sets the Source's direction attributes using an array of values. Values are copied.
	 * @param f			the float array.
	 */
	public void setDirection(float[] f)
	{
		System.arraycopy(f, 0, direction, 0, Math.min(f.length, 3));
		al.alSourcefv(getALId(), AL.AL_DIRECTION, direction, 0);
		errorCheck();
	}
	
	/**
	 * Sets the gain for this Source.
	 * @param f		the new gain.
	 */
	public void setGain(float f)
	{
		gain = RMath.clampValue(f, 0f, 1f);
		al.alSourcef(getALId(), AL.AL_GAIN, gain);
		errorCheck();
	}

	/**
	 * Sets the minimum gain for this Source. 
	 * Used in gain attenuation by distance.
	 * @param f		the new gain.
	 */
	public void setMinGain(float f)
	{
		minGain = RMath.clampValue(f, 0f, 1f);
		al.alSourcef(getALId(), AL.AL_MIN_GAIN, minGain);
		errorCheck();
	}

	/**
	 * Sets the maximum gain for this Source.
	 * Used in gain attenuation by distance.
	 * @param f		the new gain.
	 */
	public void setMaxGain(float f)
	{
		maxGain = RMath.clampValue(f, 0f, 1f);
		al.alSourcef(getALId(), AL.AL_MAX_GAIN, maxGain);
		errorCheck();
	}

	/**
	 * Sets the pitch for this Source.
	 * @param f		the new pitch.
	 */
	public void setPitch(float f)
	{
		pitch = Math.max(0, f);
		al.alSourcef(getALId(), AL.AL_PITCH, pitch);
		errorCheck();
	}

	/**
	 * Sets the pitch by semitones.
	 * Setting this to 0 is equivalent to setting the pitch to 1.
	 * Every 12 semitones is one octave.
	 */
	public void setPitchUsingSemitones(int semitones)
	{
		setPitchUsingCents(semitones * 100);
	}
	
	/**
	 * Sets the pitch by cents.
	 * Setting this to 0 is equivalent to setting the pitch to 1.
	 * Every 1200 cents is one octave.
	 */
	public void setPitchUsingCents(int cents)
	{
		setPitch((float)(Math.pow(2, cents / 1200.0)));
	}

	/**
	 * Sets the rolloff factor for this Source.
	 * @param f		the new rolloff factor.
	 */
	public void setRolloff(float f)
	{
		rolloff = Math.max(0, f);
		al.alSourcef(getALId(), AL.AL_ROLLOFF_FACTOR, rolloff);
		errorCheck();
	}

	/**
	 * Sets the gain of this Source when the Listener
	 * lies outside of this Source's cone.
	 * @param f	the gain value.	
	 */
	public void setOuterConeGain(float f)
	{
		outerConeGain = f;
		al.alSourcef(getALId(), AL.AL_CONE_OUTER_GAIN, outerConeGain);
		errorCheck();
	}

	/**
	 * Sets the angle of this Source's outer cone.
	 * Sounds heard by the listener outside of this Source's conic projection
	 * are attenuated.  
	 * @param f the angle in degrees.
	 */
	public void setOuterConeAngle(float f)
	{
		outerCone = f;
		al.alSourcef(getALId(), AL.AL_CONE_OUTER_ANGLE, outerCone);
		errorCheck();
	}

	/**
	 * Sets the angle of this Source's inner cone.
	 * Sounds heard by the listener inside of this Source's conic projection
	 * are not attenuated.  
	 * @param f the angle in degrees.
	 */
	public void setInnerConeAngle(float f)
	{
		innerCone = f;
		al.alSourcef(getALId(), AL.AL_CONE_INNER_ANGLE, innerCone);
		errorCheck();
	}

	/**
	 * Sets the maximum distance that Source attenuation can occur.
	 * The algorithm in determining this Source's final gain is dependent
	 * upon the current distance model.
	 * @param f the maximum distance in units.
	 */
	public void setMaxDistance(float f)
	{
		maxDistance = Math.max(0, f);
		al.alSourcef(getALId(), AL.AL_MAX_DISTANCE, maxDistance);
		errorCheck();
	}

	/**
	 * Sets the minimum (reference) distance that Source attenuation can occur.
	 * The algorithm in determining this Source's final gain is dependent
	 * upon the current distance model.
	 * @param f the reference distance in units.
	 */
	public void setReferenceDistance(float f)
	{
		referenceDistance = Math.max(0, f);
		al.alSourcef(getALId(),	AL.AL_REFERENCE_DISTANCE, referenceDistance);
		errorCheck();
	}

	/**
	 * Sets the looping attribute for this Source.
	 * Looping Sources loop the current queued buffer.
	 */
	public void setLooping(boolean loop)
	{
		looping = loop;
		al.alSourcei(getALId(), AL.AL_LOOPING, looping ? AL.AL_TRUE : AL.AL_FALSE);
		errorCheck();
	}
	
	/**
	 * Sets the "relative to listener" attribute for this Source.
	 */
	public void setRelative(boolean r)
	{
		relative = r;
		al.alSourcei(getALId(), AL.AL_SOURCE_RELATIVE, relative ? AL.AL_TRUE : AL.AL_FALSE);
		errorCheck();
	}

	/**
	 * Returns true if "auto velocity" is on.
	 */
	public boolean isAutoVelocityOn()
	{
		return autoVelocity;
	}

	/**
	 * Gets the gain for this Source.
	 */
	public float getGain()
	{
		return gain;
	}

	/**
	 * Gets if this source loops the playing buffer.
	 */
	public boolean isLooping()
	{
		return looping;
	}

	/**
	 * Gets the pitch factor for this Source.
	 */
	public float getPitch()
	{
		return pitch;
	}

	/**
	 * @return the relative flag
	 */
	public boolean isRelative()
	{
		return relative;
	}

	/**
	 * @return the rolloff factor
	 */
	public float getRolloff()
	{
		return rolloff;
	}

	public float getMinGain()
	{
		return minGain;
	}

	public float getMaxGain()
	{
		return maxGain;
	}

	public float getReferenceDistance()
	{
		return referenceDistance;
	}

	public float getMaxDistance()
	{
		return maxDistance;
	}

	public float getInnerCone()
	{
		return innerCone;
	}

	public float getOuterCone()
	{
		return outerCone;
	}

	public float getOuterConeGain()
	{
		return outerConeGain;
	}

	public OALFilter getFilter()
	{
		return dryFilter;
	}
	
	/**
	 * Returns the buffer dequeued, or null if no Buffer is queued (you silly, silly person).
	 * This does NOT check if the Source is currently playing.
	 */
	public synchronized OALBuffer dequeueBuffer()
	{
		OALBuffer out = bufferQueue.dequeue();
		if (out != null)
		{
			STATE_DEQUEUE[0] = out.getALId();
			clearError();
			al.alSourceUnqueueBuffers(getALId(), 1, STATE_DEQUEUE, 0);
			errorCheck();
			fireSourceBufferDequeuedEvent(this, out);
		}
		return out;
	}

	/**
	 * Dequeues all of the buffers from this Source.
	 */
	public synchronized OALBuffer[] dequeueAllBuffers()
	{
		OALBuffer[] out = new OALBuffer[bufferQueue.size()];
		int i = 0;
		while (!bufferQueue.isEmpty())
			out[i++] = dequeueBuffer();
		return out;
	}
	
	/**
	 * Enqueues a bunch of buffers onto this one.
	 */
	public synchronized void enqueueBuffers(OALBuffer[] buffers)
	{
		for (OALBuffer b : buffers)
			enqueueBuffer(b);
	}
	
	/**
	 * Returns the foremost buffer in the queue.
	 */
	public OALBuffer peekBuffer()
	{
		return bufferQueue.head();
	}
	
	/**
	 * Enqueues a buffer on this Source.
	 */
	public synchronized void enqueueBuffer(OALBuffer b)
	{
		if (buffer != null)
			setBuffer(null);
		if (b != null)
		{
			STATE_ENQUEUE[0] = b.getALId();
			clearError();
			al.alSourceQueueBuffers(getALId(), 1, STATE_ENQUEUE, 0);
			errorCheck();
			bufferQueue.add(b);
			fireSourceBufferEnqueuedEvent(this, b);
		}
	}
	
	/**
	 * Enqueues a single buffer on this Source, making sure that it is the only
	 * Buffer enqueued by it. 
	 * If <code>null</code> is provided, this clears the buffer assignment.
	 * This does NOT check if the Source is currently playing before dequeuing
	 * the bound buffers.
	 * @param b	the Buffer to bind to this Source.
	 */
	public synchronized void setBuffer(OALBuffer b)
	{
		if (!bufferQueue.isEmpty())
			dequeueAllBuffers();
		if (b == null)
		{
			al.alSourcei(getALId(), AL.AL_BUFFER, 0);
			errorCheck();
			buffer = null;
		}
		else
		{
			al.alSourcei(getALId(), AL.AL_BUFFER, b.getALId());
			errorCheck();
			buffer = b;
		}
	}

	/**
	 * How many buffers are queued up for this source?
	 */
	public int getQueuedBufferCount()
	{
		int[] out = new int[1];
		al.alGetSourcei(getALId(), AL.AL_BUFFERS_QUEUED, out, 0);
		errorCheck();
		return out[0];
	}
	
	/**
	 * Gets many buffers were processed on this source.
	 * @return the amount of buffers processed.
	 */
	public int getProcessedBufferCount()
	{
		int[] out = new int[1];  
		al.alGetSourcei(getALId(), AL.AL_BUFFERS_PROCESSED, out, 0);
		errorCheck();
		return out[0];
	}
	
	/**
	 * Adds a source listener to this Source.
	 * @param listener	the SourceListener to add.
	 */
	public void addSourceListener(OALSourceListener listener)
	{
		sourceListeners.add(listener);
	}
	
	/**
	 * Removes a source listener from this Source.
	 * @param listener	the SourceListener to remove.
	 */
	public void removeSourceListener(OALSourceListener listener)
	{
		sourceListeners.remove(listener);
	}
	
	/**
	 * Removes all source listeners from the Source. 
	 */
	public void removeAllListeners()
	{
		sourceListeners.clear();
	}
	
	/**
	 * Fires a sourcePlayed(). 
	 */
	private void fireSourcePlayedEvent(OALSource source)
	{
		for (OALSourceListener sl : sourceListeners)
			sl.sourcePlayed(source);
	}

	/**
	 * Fires a sourcePaused(). 
	 */
	private void fireSourcePausedEvent(OALSource source)
	{
		for (OALSourceListener sl : sourceListeners)
			sl.sourcePaused(source);
	}

	/**
	 * Fires a sourceRewound(). 
	 */
	private void fireSourceRewoundEvent(OALSource source)
	{
		for (OALSourceListener sl : sourceListeners)
			sl.sourceRewound(source);
	}

	/**
	 * Fires a sourceStopped(). 
	 */
	private void fireSourceStoppedEvent(OALSource source)
	{
		for (OALSourceListener sl : sourceListeners)
			sl.sourceStopped(source);
	}

	/**
	 * Fires a sourceBufferDequeued(). 
	 */
	private void fireSourceBufferDequeuedEvent(OALSource source, OALBuffer buffer)
	{
		for (OALSourceListener sl : sourceListeners)
			sl.sourceBufferDequeued(source, buffer);
	}

	/**
	 * Fires a sourceBufferEnqueued(). 
	 */
	private void fireSourceBufferEnqueuedEvent(OALSource source, OALBuffer buffer)
	{
		for (OALSourceListener sl : sourceListeners)
			sl.sourceBufferEnqueued(source, buffer);
	}

	/**
	 * Makes the calling thread wait for this source to stop playing.
	 * Pausing this does not make the thread continue.
	 * @see #stop()
	 */
	public void waitForEnd()
	{
		while (isPlaying())
			Common.sleep(1);
	}
	
	/** 
	 * Plays this source.
	 * This does nothing if this is not bound to a buffer.
	 */
	public void play()
	{
		if (isBoundToABuffer())
		{
			al.alSourcePlay(getALId());
			errorCheck();
			fireSourcePlayedEvent(this);
		}
	}
		
	/** 
	 * Plays this source and makes the calling thread wait until it ends.
	 * Pausing this does not make the thread continue.
	 * @see #play()
	 * @see #waitForEnd()
	 * @see #stop()
	 */
	public void playAndWait()
	{
		play();
		waitForEnd();
	}
		
	/** 
	 * Pauses this source. 
	 */
	public void pause()
	{
		if (isBoundToABuffer())
		{
			al.alSourcePause(getALId());
			errorCheck();
			fireSourcePausedEvent(this);
		}
	}

	/** 
	 * Stops playing this source.
	 */
	public void stop()
	{
		if (isBoundToABuffer())
		{
			boolean event = !isStopped();
			al.alSourceStop(getALId());
			errorCheck();
			if (event)
				fireSourceStoppedEvent(this);
		}
	}
	
	/** Rewinds this source. */
	public void rewind()
	{
		if (isBoundToABuffer())
		{
			al.alSourceRewind(getALId());
			errorCheck();
			fireSourceRewoundEvent(this);
		}
	}

	/** Is this source playing? */
	public boolean isPlaying()
	{
		return getState() == AL.AL_PLAYING;
	}
	
	/** Is this source ready to play? */
	public boolean isReady()
	{
		return getState() == AL.AL_INITIAL;
	}

	/** Is this source stopped? */
	public boolean isStopped()
	{
		return getState() == AL.AL_STOPPED;
	}

	/** Is this source paused? */
	public boolean isPaused()
	{
		return getState() == AL.AL_PAUSED;
	}

	/** Is this Source bound to a Buffer object? */
	public boolean isBoundToABuffer()
	{
		return !bufferQueue.isEmpty() || buffer != null;
	}
	
	/**
	 * Get this source's state.
	 */
	protected final int getState()
	{
		al.alGetSourcei(getALId(), AL.AL_SOURCE_STATE, STATE_BUFFER, 0);
		errorCheck();
		return STATE_BUFFER[0];		
	}

	/**
	 * Enables/Disables automatic velocity calculation.
	 * @param autovel	should velocity automatically be calculated?
	 */
	public void setAutoVelocity(boolean autovel)
	{
		autoVelocity = autovel;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Source ");
		sb.append(getALId()+" ");
		if (bufferQueue.size() > 0)
			sb.append(bufferQueue.toString());
		else if (buffer != null)
			sb.append(buffer.toString());
		return sb.toString();
	}

}
