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

import java.io.IOException;

import com.blackrook.commons.hash.Hash;
import com.blackrook.oal.effect.AutowahEffect;
import com.blackrook.oal.effect.ChorusEffect;
import com.blackrook.oal.effect.CompressorEffect;
import com.blackrook.oal.effect.DistortionEffect;
import com.blackrook.oal.effect.EchoEffect;
import com.blackrook.oal.effect.EqualizerEffect;
import com.blackrook.oal.effect.FlangerEffect;
import com.blackrook.oal.effect.FrequencyShiftEffect;
import com.blackrook.oal.effect.PitchShiftEffect;
import com.blackrook.oal.effect.ReverbEffect;
import com.blackrook.oal.effect.RingModulatorEffect;
import com.blackrook.oal.effect.VocalMorpherEffect;
import com.blackrook.oal.enums.DistanceModel;
import com.blackrook.oal.exception.*;
import com.blackrook.oal.filter.BandPassFilter;
import com.blackrook.oal.filter.HighPassFilter;
import com.blackrook.oal.filter.LowPassFilter;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;
import com.jogamp.openal.ALCcontext;
import com.jogamp.openal.ALCdevice;
import com.jogamp.openal.ALExt;
import com.jogamp.openal.ALFactory;

/**
 * This class is a central sound system class designed to manage an OpenAL instance and environment.
 * If any class is created that references this class and throws a NullPointerException, this probably was not initialized.
 * @author Matthew Tropiano
 */
public final class OALSystem
{
	/** System AL instance. */
	private AL al;
	/** System ALC instance. */
	private ALC alc;
	/** System ALExt instance. */
	private ALExt alext;

	/** This device. */
	private ALCdevice alcDevice;
	/** This context instance. */
	private ALCcontext alcContext;
	/** Distance model. */
	private DistanceModel currentDistanceModel;

	/** Listener. */
	private OALListener listener;
	/** Object references. */
	private Hash<OALObject> createdObjects;

	/** Maximum effect slots per source. */
	private int maxEffectSlots;
	
	/**
	 * Creates a new SoundSystem with the current device as a new sound device and 
	 * the current context as its first context, made current.
	 */
	public OALSystem()
	{
		this(null);
	}
	
	/**
	 * Creates a new SoundSystem.
	 * NOTE: Passing 'null' as the device name will create a new system with the default (current) device and context.
	 * @param deviceName the name of the device.
	 */
	public OALSystem(String deviceName)
	{
		al = ALFactory.getAL();
		alc = ALFactory.getALC();
		alext = ALFactory.getALExt();
		createdObjects = new Hash<>();
		
		String dname = deviceName != null ? "device \""+deviceName+"\"" : "default device";
		
		// create device.
		alcDevice = alc.alcOpenDevice(deviceName);
		if (alcDevice == null)
			throw new SoundSystemException("The " + dname + " couldn't be opened.");

		alcContext = alc.alcCreateContext(alcDevice, null);
		if (alcContext == null)
			throw new SoundSystemException("The context for " + dname + " couldn't be created.");				

		if (!alc.alcMakeContextCurrent(alcContext))
			throw new SoundSystemException("The context for " + dname + " couldn't be made current.");

		listener = new OALListener(al, alc);
		
		// get device defaults
		maxEffectSlots = getALCInteger(ALExt.ALC_MAX_AUXILIARY_SENDS);
	}
	
	/**
	 * Returns a list of all devices on this system.
	 */
	public static String[] getDeviceNames()
	{
		return ALFactory.getALC().alcGetDeviceSpecifiers(); 
	}

	/**
	 * Returns a list of all capture devices on this system.
	 */
	public static String[] getCaptureDeviceNames()
	{
		return ALFactory.getALC().alcGetCaptureDeviceSpecifiers(); 
	}

	AL getAL()
	{
		return al;
	}
	
	ALC getALC() 
	{
		return alc;
	}
	
	ALExt getALExt()
	{
		return alext;
	}

	void addObjectReference(OALObject object)
	{
		createdObjects.put(object);
	}

	void removeObjectReference(OALObject object)
	{
		createdObjects.remove(object);
	}
	
	// Returns an AL enum integer.
	/*
	private int getALInteger(int enumerant)
	{
		int[] OUTPUT = new int[1];
		al.alGetIntegerv(enumerant, OUTPUT, 0);
		getError();
		return OUTPUT[0];
	}
	*/
	
	// Returns an ALC enum integer.
	private int getALCInteger(int enumerant)
	{
		int[] OUTPUT = new int[1];
		alc.alcGetIntegerv(alcDevice, enumerant, 1, OUTPUT, 0);
		getError();
		return OUTPUT[0];
	}
	
	/**
	 * Suspends processing of the current context.
	 */
	public void suspendCurrentContext()
	{
		alc.alcSuspendContext(alcContext);
		getError();
	}
	
	/**
	 * Resumes processing of the current context.
	 */
	public void processCurrentContext()
	{
		alc.alcProcessContext(alcContext);
		getError();
	}
	
	/**
	 * Allocates a new source and assigns it internally to the current context.
	 * @return the newly allocated source.
	 * @throws SoundSystemException if the source can't be created or there is no current context selected.
	 */
	public OALSource createSource()
	{
		return createSource(false);
	}
	
	/**
	 * Allocates a new source and assigns it internally to the current context.
	 * @param autoVelocity if true, set auto velocity to on for this Source.
	 * @return the newly allocated source.
	 * @throws SoundSystemException if the source can't be created.
	 */
	public OALSource createSource(boolean autoVelocity)
	{
		return new OALSource(this, autoVelocity, maxEffectSlots);
	}

	/**
	 * Allocates a new buffer for loading data into. Buffers are independent
	 * of device context. 
	 * @return	a newly allocated buffer.
	 * @throws	SoundException	if the Buffer can't be allocated somehow.
	 */
	public OALBuffer createBuffer()
	{
		return new OALBuffer(this);
	}
	
	/**
	 * Allocates a new buffer for loading data into. Buffers are independent
	 * of device context. 
	 * @return a set of newly allocated buffers.
	 * @throws SoundException if the Buffer can't be allocated somehow.
	 */
	public OALBuffer[] createBuffers(int amount)
	{
		OALBuffer[] out = new OALBuffer[amount];
		for (int i = 0; i < amount; i++)
			out[i] = createBuffer();
		return out;
	}
	
	/**
	 * Convenience method for checking for an OpenAL error and throwing a SoundException
	 * if an error is raised. 
	 */
	public void getError()
	{
		int error = al.alGetError();
		if (error != AL.AL_NO_ERROR)
			throw new SoundException("OpenAL returned \""+al.alGetString(error)+"\".");
	}
	
	/**
	 * Convenience method for checking for an OpenAL error and throwing a SoundException
	 * if an error is raised. 
	 */
	public void getContextError()
	{
		int error = alc.alcGetError(alcDevice);
		if (error != AL.AL_NO_ERROR)
			throw new SoundException("OpenAL returned \""+alc.alcGetString(alcDevice, error)+"\".");
	}
	
	/**
	 * Allocates a new buffer with data loaded into it. All of the sound data
	 * readable by the SoundData instance is read into the buffer.
	 * If you know that the data being loaded is very long or large, you
	 * should consider using a Streaming Source to conserve memory.
	 * Buffers are independant of device context. 
	 * @param handle the handle to the sound data to load into this buffer.
	 * @return a newly allocated buffer.
	 * @throws IOException if the data can't be read.
	 * @throws SoundException if the Buffer can't be allocated somehow.
	 */
	public OALBuffer createBuffer(JSPISoundHandle handle) throws IOException
	{
		return new OALBuffer(this, handle);
	}
	
	/**
	 * Allocates a new buffer with data loaded into it. All of the sound data
	 * readable by the SoundDataDecoder instance is read into the buffer.
	 * If you know that the data being loaded is very long or large, you
	 * should consider using a Streaming Source to conserve memory.
	 * Buffers are independent of device context. 
	 * @param dataDecoder the decoder of the sound data to load into this buffer.
	 * @return a newly allocated buffer.
	 * @throws IOException if the data can't be read.
	 * @throws SoundException if the Buffer can't be allocated somehow.
	 */
	public OALBuffer createBuffer(JSPISoundHandle.Decoder dataDecoder) throws IOException
	{
		return new OALBuffer(this, dataDecoder);
	}
	
	/**
	 * Creates a new Auxiliary Effect Slot for adding a filter and effects to Sources.
	 * These slots can be added to sources. If you have more than one source 
	 * that uses the same sets of filters and effects, it might be better to
	 * bind one slot to more than one source to save memory, especially if you
	 * need to alter an effect of filter for more than one sound that is playing.
	 * @return a new AuxEffectSlot object.
	 * @throws SoundException	if the slot can't be allocated somehow.
	 */
	public OALEffectSlot createEffectSlot()
	{
		return new OALEffectSlot(this);
	}
	
	/**
	 * Creates a new Autowah effect.
	 * @return	a new effect of this type with default values set.
	 */
	public AutowahEffect createAutowahEffect()
	{
		return new AutowahEffect(this);
	}
	
	/**
	 * Creates a new Chorus effect.
	 * @return	a new effect of this type with default values set.
	 */
	public ChorusEffect createChorusEffect()
	{
		return new ChorusEffect(this);
	}
	
	/**
	 * Creates a new Compressor effect.
	 * @return	a new effect of this type with default values set.
	 */
	public CompressorEffect createCompressorEffect()
	{
		return new CompressorEffect(this);
	}
	
	/**
	 * Creates a new Distortion effect.
	 * @return	a new effect of this type with default values set.
	 */
	public DistortionEffect createDistortionEffect()
	{
		return new DistortionEffect(this);
	}
	
	/**
	 * Creates a new Echo effect.
	 * @return	a new effect of this type with default values set.
	 */
	public EchoEffect createEchoEffect()
	{
		return new EchoEffect(this);
	}
	
	/**
	 * Creates a new Equalizer effect.
	 * @return	a new effect of this type with default values set.
	 */
	public EqualizerEffect createEqualizerEffect()
	{
		return new EqualizerEffect(this);
	}
	
	/**
	 * Creates a new Flanger effect.
	 * @return	a new effect of this type with default values set.
	 */
	public FlangerEffect createFlangerEffect()
	{
		return new FlangerEffect(this);
	}
	
	/**
	 * Creates a new Frequency Shift effect.
	 * @return	a new effect of this type with default values set.
	 */
	public FrequencyShiftEffect createFrequencyShiftEffect()
	{
		return new FrequencyShiftEffect(this);
	}
	
	/**
	 * Creates a new Pitch Shift effect.
	 * @return	a new effect of this type with default values set.
	 */
	public PitchShiftEffect createPitchShiftEffect()
	{
		return new PitchShiftEffect(this);
	}
	
	/**
	 * Creates a new Reverb effect.
	 * @return	a new effect of this type with default values set.
	 */
	public ReverbEffect createReverbEffect()
	{
		return new ReverbEffect(this);
	}
	
	/**
	 * Creates a new Ring Modulator effect.
	 * @return	a new effect of this type with default values set.
	 */
	public RingModulatorEffect createRingModulatorEffect()
	{
		return new RingModulatorEffect(this);
	}
	
	/**
	 * Creates a new Vocal Morpher effect.
	 * @return	a new effect of this type with default values set.
	 */
	public VocalMorpherEffect createVocalMorpherEffect()
	{
		return new VocalMorpherEffect(this);
	}
	
	/**
	 * Creates a new High Pass filter.
	 * @return	a new filter of this type with default values set.
	 */
	public HighPassFilter createHighPassFilter()
	{
		return new HighPassFilter(this);
	}
	
	/**
	 * Creates a new Low Pass filter.
	 * @return	a new filter of this type with default values set.
	 */
	public LowPassFilter createLowPassFilter()
	{
		return new LowPassFilter(this);
	}
	
	/**
	 * Creates a new Band Pass filter.
	 * @return	a new filter of this type with default values set.
	 */
	public BandPassFilter createBandPassFilter()
	{
		return new BandPassFilter(this);
	}
	
	/**
	 * Get the reference to this system's listener.
	 * @return		the Listener of this environment.
	 */
	public OALListener getListener()
	{
		return listener;
	}

	/**
	 * Returns the name of the current OpenAL renderer (device). 
	 */
	public String getALRendererName()
	{
		return al.alGetString(AL.AL_RENDERER);
	}
	
	/**
	 * Returns OpenAL's version string. 
	 */
	public String getALVersionName()
	{
		return al.alGetString(AL.AL_VERSION);
	}

	/**
	 * Returns the name of the current OpenAL vendor. 
	 */
	public String getALVendorName()
	{
		return al.alGetString(AL.AL_VENDOR);
	}
	
	/**
	 * Returns the names of all available OpenAL extensions (newline-separated). 
	 */
	public String getALExtensions()
	{
		return al.alGetString(AL.AL_EXTENSIONS);
	}
	
	/**
	 * Sets the sound environment's Doppler Factor.
	 * 0 = disabled.
	 * @param f		the Doppler factor. 
	 */
	public void setDopplerFactor(float f)
	{
		al.alDopplerFactor(f);
		getError();
	}

	/**
	 * Returns the sound environment's Doppler Factor.
	 */
	public float getDopplerFactor()
	{
		return al.alGetFloat(AL.AL_DOPPLER_FACTOR);
	}

	/**
	 * Sets the sound environment's speed of sound factor.
	 * @param s		the speed of sound. 
	 */
	public void setSpeedOfSound(float s)
	{
		al.alDopplerVelocity(s);
		getError();
	}

	/**
	 * Returns the sound environment's speed of sound factor.
	 */
	public float getSpeedOfSound()
	{
		return al.alGetFloat(AL.AL_DOPPLER_VELOCITY);
	}
	
	/**
	 * Sets the current context's distance model.
	 * By default, this is DistanceModel.INVERSE_DISTANCE_CLAMPED. 
	 * @param model the distance model to use.
	 */
	public void setDistanceModel(DistanceModel model)
	{
		al.alDistanceModel(model.alVal);
		getError();
		currentDistanceModel = model;
	}

	/**
	 * Gets the current context's distance model.
	 */
	public DistanceModel getDistanceModel(DistanceModel model)
	{
		return currentDistanceModel;
	}

	/**
	 * Runs all Shut Down hooks, destroys all contexts and closes all open devices.
	 */
	public void shutDown()
	{
		for (OALObject object : createdObjects)
			object.destroy();
		createdObjects.clear();
		
		//suspendCurrentContext();
		alc.alcMakeContextCurrent(null);
		getContextError();
		alc.alcDestroyContext(alcContext);
		getContextError();
		alcContext = null;

		alc.alcCloseDevice(alcDevice);
		getContextError();
		alcDevice = null;
	}

	@Override
	public void finalize() throws Throwable
	{
		shutDown();
		super.finalize();
	}
	
}


