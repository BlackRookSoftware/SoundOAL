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
package com.blackrook.oal;

import com.blackrook.oal.exception.SoundException;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;

/**
 * Auxiliary Effect Slot for enforcing effect mixing rules.
 * @author Matthew Tropiano
 */
public class OALAuxEffectSlot extends OALObject
{
	/** Effect bound to this slot. */
	protected OALEffect boundEffect;
	
	/** Effect slot gain. */
	protected float slotGain;
	/** Does this auto update itself if the Effect changes? */
	protected boolean slotAutoUpdate;
	
	/**
	 * Creates a new auxiliary effect slot.
	 */
	OALAuxEffectSlot(AL al, ALC alc)
	{
		super(al,alc);
		setEffect(null);
	}
	
	@Override
	protected int allocate() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		al.alGetError();
		al.alGenAuxiliaryEffectSlots(1, STATE_NUMBER, 0);
		errorCheck(this);
		return STATE_NUMBER[0];
	}

	@Override
	protected final void free() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		STATE_NUMBER[0] = getALId();
		al.alDeleteBuffers(1, STATE_NUMBER, 0);
		errorCheck(this);
	}

	/**
	 * Sets an Effect in this slot. Can be null to remove the Effect.
	 * @param e		the effect to add.
	 */
	public void setEffect(OALEffect e)
	{
		boundEffect = e;
		al.alAuxiliaryEffectSloti(getALId(), 
				AL.AL_EFFECTSLOT_EFFECT, e == null ? AL.AL_EFFECT_NULL : e.getALId());
	}
	
	/**
	 * Gets the reference of the Effect bound to this slot.
	 */
	public OALEffect getEffect()
	{
		return boundEffect;
	}
	
	/**
	 * Removes the effect in this slot and returns it.
	 */
	public OALEffect removeEffect()
	{
		OALEffect e = boundEffect;
		setEffect(null);
		return e;
	}
	
	/**
	 * Sets effect slot gain.
	 * @param gain	the gain to use.
	 */
	public void setGain(float gain)
	{
		slotGain = gain;
		al.alAuxiliaryEffectSlotf(getALId(), AL.AL_EFFECTSLOT_GAIN, gain);
	}
	
	/**
	 * Returns the effect slot gain.
	 */
	public float getGain()
	{
		return slotGain;
	}

	/** Does this auto update itself if the Effect changes? */
	public final boolean isSlotAutoUpdateOn()
	{
		return slotAutoUpdate;
	}

	/** Sets if this auto update itself if the Effect changes. */
	public final void setSlotAutoUpdate(boolean slotAutoUpdate)
	{
		this.slotAutoUpdate = slotAutoUpdate;
		al.alAuxiliaryEffectSloti(getALId(), 
				AL.AL_EFFECTSLOT_AUXILIARY_SEND_AUTO, 
				slotAutoUpdate ? AL.AL_TRUE : AL.AL_FALSE);
	}

	
}
