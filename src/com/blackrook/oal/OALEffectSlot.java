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
import com.jogamp.openal.ALExt;

/**
 * Auxiliary Effect Slot for enforcing effect mixing rules.
 * @author Matthew Tropiano
 */
public class OALEffectSlot extends OALObject
{
	/** Effect bound to this slot. */
	protected OALEffect effect;
	
	/** Effect slot gain. */
	protected float slotGain;
	/** Does this auto update itself if the Effect changes? */
	protected boolean autoUpdating;
	
	/**
	 * Creates a new auxiliary effect slot.
	 */
	OALEffectSlot(OALSystem system)
	{
		super(system);
		setEffect(null);
	}
	
	@Override
	protected int allocate() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		al.alGetError();
		alext.alGenAuxiliaryEffectSlots(1, STATE_NUMBER, 0);
		errorCheck(this);
		return STATE_NUMBER[0];
	}

	@Override
	protected final void free() throws SoundException
	{
		int[] STATE_NUMBER = new int[1];
		STATE_NUMBER[0] = getALId();
		alext.alDeleteAuxiliaryEffectSlots(1, STATE_NUMBER, 0);
		errorCheck(this);
	}

	/**
	 * Sets an Effect in this slot. Can be null to remove the Effect.
	 * @param effect		the effect to add.
	 */
	public void setEffect(OALEffect effect)
	{
		this.effect = effect;
		alext.alAuxiliaryEffectSloti(getALId(), ALExt.AL_EFFECTSLOT_EFFECT, effect == null ? ALExt.AL_EFFECT_NULL : effect.getALId());
	}
	
	/**
	 * Gets the reference of the Effect bound to this slot.
	 */
	public OALEffect getEffect()
	{
		return effect;
	}
	
	/**
	 * Removes the effect in this slot and returns it.
	 */
	public OALEffect removeEffect()
	{
		OALEffect e = effect;
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
		alext.alAuxiliaryEffectSlotf(getALId(), ALExt.AL_EFFECTSLOT_GAIN, gain);
	}
	
	/**
	 * Returns the effect slot gain.
	 */
	public float getGain()
	{
		return slotGain;
	}

	/** Does this auto update itself if the Effect changes? */
	public final boolean isAutoUpdating()
	{
		return autoUpdating;
	}

	/** Sets if this auto update itself if the Effect changes. */
	public final void setAutoUpdating(boolean autoUpdate)
	{
		this.autoUpdating = autoUpdate;
		alext.alAuxiliaryEffectSloti(getALId(), ALExt.AL_EFFECTSLOT_AUXILIARY_SEND_AUTO, autoUpdate ? AL.AL_TRUE : AL.AL_FALSE);
	}

}
