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
 * Vocal Modifier effect for sources.
 * @author Matthew Tropiano
 */
public class VocalMorpherEffect extends OALEffect
{
	/** WaveForm type enumeration. */
	public static enum WaveForm
	{
		SINUSOID(AL.AL_VOCAL_MORPHER_WAVEFORM_SINUSOID),
		TRIANGLE(AL.AL_VOCAL_MORPHER_WAVEFORM_TRIANGLE),
		SAWTOOTH(AL.AL_VOCAL_MORPHER_WAVEFORM_SAWTOOTH);
		
		final int alVal;
		private WaveForm(int alVal) {this.alVal = alVal;}
	}
	
	/** Phoneme type enumeration. */
	public static enum Phoneme
	{
		A(AL.AL_VOCAL_MORPHER_PHONEME_A),
		E(AL.AL_VOCAL_MORPHER_PHONEME_E),
		I(AL.AL_VOCAL_MORPHER_PHONEME_I),
		O(AL.AL_VOCAL_MORPHER_PHONEME_O),
		U(AL.AL_VOCAL_MORPHER_PHONEME_U),
		AA(AL.AL_VOCAL_MORPHER_PHONEME_AA),
		AE(AL.AL_VOCAL_MORPHER_PHONEME_AE),
		AH(AL.AL_VOCAL_MORPHER_PHONEME_AH),
		AO(AL.AL_VOCAL_MORPHER_PHONEME_AO),
		EH(AL.AL_VOCAL_MORPHER_PHONEME_EH),
		ER(AL.AL_VOCAL_MORPHER_PHONEME_ER),
		IH(AL.AL_VOCAL_MORPHER_PHONEME_IH),
		IY(AL.AL_VOCAL_MORPHER_PHONEME_IY),
		UH(AL.AL_VOCAL_MORPHER_PHONEME_UH),
		UW(AL.AL_VOCAL_MORPHER_PHONEME_UW),
		B(AL.AL_VOCAL_MORPHER_PHONEME_B),
		D(AL.AL_VOCAL_MORPHER_PHONEME_D),
		F(AL.AL_VOCAL_MORPHER_PHONEME_F),
		G(AL.AL_VOCAL_MORPHER_PHONEME_G),
		J(AL.AL_VOCAL_MORPHER_PHONEME_J),
		K(AL.AL_VOCAL_MORPHER_PHONEME_K),
		L(AL.AL_VOCAL_MORPHER_PHONEME_L),
		M(AL.AL_VOCAL_MORPHER_PHONEME_M),
		N(AL.AL_VOCAL_MORPHER_PHONEME_N),
		P(AL.AL_VOCAL_MORPHER_PHONEME_P),
		R(AL.AL_VOCAL_MORPHER_PHONEME_R),
		S(AL.AL_VOCAL_MORPHER_PHONEME_S),
		T(AL.AL_VOCAL_MORPHER_PHONEME_T),
		V(AL.AL_VOCAL_MORPHER_PHONEME_V),
		Z(AL.AL_VOCAL_MORPHER_PHONEME_Z);
		
		final int alVal;
		private Phoneme(int alVal) {this.alVal = alVal;}
	}
	
	/** Vocal morpher rate in Hertz. */
	protected float rate;
	/** Morpher phoneme A. */
	protected Phoneme phonemeA;
	/** Morpher phoneme B. */
	protected Phoneme phonemeB;
	/** Morpher phoneme A coarse tuning in semitones. */
	protected int phonemeACoarseTuning;
	/** Morpher phoneme B coarse tuning in semitones. */
	protected int phonemeBCoarseTuning;
	/** Morpher waveform. */
	protected WaveForm waveForm;

	public VocalMorpherEffect(AL al, ALC alc)
	{
		super(al,alc,EffectType.VOCAL_MORPHER);
		setPhonemeA(Phoneme.A);
		setPhonemeB(Phoneme.ER);
		setPhonemeACoarseTuning(0);
		setPhonemeBCoarseTuning(0);
		setWaveform(WaveForm.SINUSOID);
		setRate(1.41f);
	}

	/** Get morpher phoneme A. */
	public final Phoneme getPhonemeA()
	{
		return phonemeA;
	}

	/** Get morpher phoneme A coarse tuning in semitones. */
	public final int getPhonemeACoarseTuning()
	{
		return phonemeACoarseTuning;
	}

	/** Get morpher phoneme B. */
	public final Phoneme getPhonemeB()
	{
		return phonemeB;
	}

	/** Get morpher phoneme B coarse tuning in semitones. */
	public final int getPhonemeBCoarseTuning()
	{
		return phonemeBCoarseTuning;
	}

	/** Get vocal morpher rate in Hertz. */
	public final float getRate()
	{
		return rate;
	}

	/** Get morpher waveform. */
	public final WaveForm getWaveform()
	{
		return waveForm;
	}

	/** Set morpher phoneme A. */
	public final void setPhonemeA(Phoneme phonemeA)
	{
		this.phonemeA = phonemeA;
		al.alEffecti(getALId(), AL.AL_VOCAL_MORPHER_PHONEMEA, phonemeA.alVal);
	}

	/** Set morpher phoneme A coarse tuning in semitones (-24 to 24). */
	public final void setPhonemeACoarseTuning(int phonemeACoarseTuning)
	{
		this.phonemeACoarseTuning = phonemeACoarseTuning;
		al.alEffecti(getALId(), AL.AL_VOCAL_MORPHER_PHONEMEA_COARSE_TUNING, RMath.clampValue(phonemeACoarseTuning, -24, 24));
	}

	/** Set morpher phoneme B. */
	public final void setPhonemeB(Phoneme phonemeB)
	{
		this.phonemeB = phonemeB;
		al.alEffecti(getALId(), AL.AL_VOCAL_MORPHER_PHONEMEB, phonemeB.alVal);
	}

	/** Set morpher phoneme B coarse tuning in semitones (-24 to 24). */
	public final void setPhonemeBCoarseTuning(int phonemeBCoarseTuning)
	{
		this.phonemeBCoarseTuning = phonemeBCoarseTuning;
		al.alEffecti(getALId(), AL.AL_VOCAL_MORPHER_PHONEMEB_COARSE_TUNING, RMath.clampValue(phonemeBCoarseTuning, -24, 24));
	}

	/** Set vocal morpher rate in Hertz (0.0 to 10.0). */
	public final void setRate(float rate)
	{
		this.rate = rate;
		al.alEffectf(getALId(), AL.AL_VOCAL_MORPHER_RATE, RMath.clampValue(rate, 0.0f, 10.0f));
	}

	/** Set morpher waveform. */
	public final void setWaveform(WaveForm waveform)
	{
		this.waveForm = waveform;
		al.alEffecti(getALId(), AL.AL_VOCAL_MORPHER_WAVEFORM, waveform.alVal);
	}
	
}
