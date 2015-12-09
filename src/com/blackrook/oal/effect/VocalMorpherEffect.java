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
import com.blackrook.oal.OALSystem;
import com.jogamp.openal.ALExt;

/**
 * Vocal Modifier effect for sources.
 * @author Matthew Tropiano
 */
public class VocalMorpherEffect extends OALEffect
{
	/** WaveForm type enumeration. */
	public static enum WaveForm
	{
		SINUSOID(ALExt.AL_VOCAL_MORPHER_WAVEFORM_SINUSOID),
		TRIANGLE(ALExt.AL_VOCAL_MORPHER_WAVEFORM_TRIANGLE),
		SAWTOOTH(ALExt.AL_VOCAL_MORPHER_WAVEFORM_SAWTOOTH);
		
		final int alVal;
		private WaveForm(int alVal) {this.alVal = alVal;}
	}
	
	/** Phoneme type enumeration. */
	public static enum Phoneme
	{
		A(ALExt.AL_VOCAL_MORPHER_PHONEME_A),
		E(ALExt.AL_VOCAL_MORPHER_PHONEME_E),
		I(ALExt.AL_VOCAL_MORPHER_PHONEME_I),
		O(ALExt.AL_VOCAL_MORPHER_PHONEME_O),
		U(ALExt.AL_VOCAL_MORPHER_PHONEME_U),
		AA(ALExt.AL_VOCAL_MORPHER_PHONEME_AA),
		AE(ALExt.AL_VOCAL_MORPHER_PHONEME_AE),
		AH(ALExt.AL_VOCAL_MORPHER_PHONEME_AH),
		AO(ALExt.AL_VOCAL_MORPHER_PHONEME_AO),
		EH(ALExt.AL_VOCAL_MORPHER_PHONEME_EH),
		ER(ALExt.AL_VOCAL_MORPHER_PHONEME_ER),
		IH(ALExt.AL_VOCAL_MORPHER_PHONEME_IH),
		IY(ALExt.AL_VOCAL_MORPHER_PHONEME_IY),
		UH(ALExt.AL_VOCAL_MORPHER_PHONEME_UH),
		UW(ALExt.AL_VOCAL_MORPHER_PHONEME_UW),
		B(ALExt.AL_VOCAL_MORPHER_PHONEME_B),
		D(ALExt.AL_VOCAL_MORPHER_PHONEME_D),
		F(ALExt.AL_VOCAL_MORPHER_PHONEME_F),
		G(ALExt.AL_VOCAL_MORPHER_PHONEME_G),
		J(ALExt.AL_VOCAL_MORPHER_PHONEME_J),
		K(ALExt.AL_VOCAL_MORPHER_PHONEME_K),
		L(ALExt.AL_VOCAL_MORPHER_PHONEME_L),
		M(ALExt.AL_VOCAL_MORPHER_PHONEME_M),
		N(ALExt.AL_VOCAL_MORPHER_PHONEME_N),
		P(ALExt.AL_VOCAL_MORPHER_PHONEME_P),
		R(ALExt.AL_VOCAL_MORPHER_PHONEME_R),
		S(ALExt.AL_VOCAL_MORPHER_PHONEME_S),
		T(ALExt.AL_VOCAL_MORPHER_PHONEME_T),
		V(ALExt.AL_VOCAL_MORPHER_PHONEME_V),
		Z(ALExt.AL_VOCAL_MORPHER_PHONEME_Z);
		
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

	public VocalMorpherEffect(OALSystem system)
	{
		super(system, ALExt.AL_EFFECT_VOCAL_MORPHER);
		setPhonemeA(Phoneme.A);
		setPhonemeB(Phoneme.ER);
		setPhonemeACoarseTuning(ALExt.AL_VOCAL_MORPHER_DEFAULT_PHONEMEA_COARSE_TUNING);
		setPhonemeBCoarseTuning(ALExt.AL_VOCAL_MORPHER_DEFAULT_PHONEMEB_COARSE_TUNING);
		setWaveform(WaveForm.SINUSOID);
		setRate(ALExt.AL_VOCAL_MORPHER_DEFAULT_RATE);
	}

	/** Get morpher phoneme A. */
	public final Phoneme getPhonemeA()
	{
		return phonemeA;
	}

	/** Set morpher phoneme A. */
	public final void setPhonemeA(Phoneme phonemeA)
	{
		this.phonemeA = phonemeA;
		alext.alEffecti(getALId(), ALExt.AL_VOCAL_MORPHER_PHONEMEA, phonemeA.alVal);
		errorCheck();
	}

	/** Get morpher phoneme A coarse tuning in semitones. */
	public final int getPhonemeACoarseTuning()
	{
		return phonemeACoarseTuning;
	}

	/** Set morpher phoneme A coarse tuning in semitones (-24 to 24). */
	public final void setPhonemeACoarseTuning(int phonemeACoarseTuning)
	{
		this.phonemeACoarseTuning = phonemeACoarseTuning;
		alext.alEffecti(getALId(), ALExt.AL_VOCAL_MORPHER_PHONEMEA_COARSE_TUNING, RMath.clampValue(phonemeACoarseTuning, ALExt.AL_VOCAL_MORPHER_MIN_PHONEMEA_COARSE_TUNING, ALExt.AL_VOCAL_MORPHER_MAX_PHONEMEA_COARSE_TUNING));
		errorCheck();
	}

	/** Get morpher phoneme B. */
	public final Phoneme getPhonemeB()
	{
		return phonemeB;
	}

	/** Set morpher phoneme B. */
	public final void setPhonemeB(Phoneme phonemeB)
	{
		this.phonemeB = phonemeB;
		alext.alEffecti(getALId(), ALExt.AL_VOCAL_MORPHER_PHONEMEB, phonemeB.alVal);
		errorCheck();
	}

	/** Get morpher phoneme B coarse tuning in semitones. */
	public final int getPhonemeBCoarseTuning()
	{
		return phonemeBCoarseTuning;
	}

	/** Set morpher phoneme B coarse tuning in semitones (-24 to 24). */
	public final void setPhonemeBCoarseTuning(int phonemeBCoarseTuning)
	{
		this.phonemeBCoarseTuning = phonemeBCoarseTuning;
		alext.alEffecti(getALId(), ALExt.AL_VOCAL_MORPHER_PHONEMEB_COARSE_TUNING, RMath.clampValue(phonemeBCoarseTuning, ALExt.AL_VOCAL_MORPHER_MIN_PHONEMEB_COARSE_TUNING, ALExt.AL_VOCAL_MORPHER_MAX_PHONEMEB_COARSE_TUNING));
		errorCheck();
	}

	/** Get vocal morpher rate in Hertz. */
	public final float getRate()
	{
		return rate;
	}

	/** Set vocal morpher rate in Hertz (0.0 to 10.0). */
	public final void setRate(float rate)
	{
		this.rate = rate;
		alext.alEffectf(getALId(), ALExt.AL_VOCAL_MORPHER_RATE, RMath.clampValue(rate, (float)ALExt.AL_VOCAL_MORPHER_MIN_RATE, ALExt.AL_VOCAL_MORPHER_MAX_RATE));
		errorCheck();
	}

	/** Get morpher waveform. */
	public final WaveForm getWaveform()
	{
		return waveForm;
	}

	/** Set morpher waveform. */
	public final void setWaveform(WaveForm waveform)
	{
		this.waveForm = waveform;
		alext.alEffecti(getALId(), ALExt.AL_VOCAL_MORPHER_WAVEFORM, waveform.alVal);
		errorCheck();
	}
	
}
