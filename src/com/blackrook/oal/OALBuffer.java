/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.io.*;

import javax.sound.sampled.AudioFormat;

import com.blackrook.oal.enums.SoundFormat;
import com.blackrook.oal.exception.SoundException;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;


/**
 * Sound sample buffer class.
 * @author Matthew Tropiano
 */
public final class OALBuffer extends OALObject
{
	public static final int FREQ_8KHZ =	8000;
	public static final int FREQ_11KHZ = 11025;
	public static final int FREQ_16KHZ = 16000;
	public static final int FREQ_22KHZ = 22050;
	public static final int FREQ_32KHZ = 32000;
	public static final int FREQ_44KHZ = 44100;
	public static final int FREQ_48KHZ = 48000;
	
	/** The sizes of each of the buffers. */
	protected int bufferSize;
	/** Sound format. */
	protected SoundFormat bufferFormat;
	/** Sound sampling rate. */
	protected int bufferRate;

	/**
	 * Constructs a new sound buffer.
	 * @throws SoundException if an OpenAL buffer cannot be allocated.
	 * @throws IOException if the SoundFile can't be opened.
	 */
	OALBuffer(AL al, ALC alc)
	{
		super(al,alc);
		bufferSize = 0;
		bufferFormat = SoundFormat.MONO8;
		bufferRate = FREQ_11KHZ;
	}

	/**
	 * Constructs a new sound buffer with an entire
	 * buffer filled with data, decoded.
	 * @param data	the data to use.
	 */
	OALBuffer(AL al, ALC alc, JSPISoundHandle data) throws IOException
	{
		this(al,alc,data.getDecoder());
	}
	
	/**
	 * Constructs a new sound buffer with an entire
	 * buffer filled with a decoder's contents.
	 * @param decoder the decoder to use.
	 */
	OALBuffer(AL al, ALC alc, JSPISoundHandle.Decoder decoder) throws IOException
	{
		super(al,alc);
		AudioFormat df = decoder.getDecodedAudioFormat();
		setFrequencyAndFormat(df);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] by = new byte[8192];
		int l = 0;
		do {
			l = decoder.readPCMBytes(by);
			bos.write(by, 0, l);
		} while (l == by.length);

		loadPCMData(ByteBuffer.wrap(bos.toByteArray()));
	}
	
	/**
	 * Sets the audio frequency and format of this buffer using
	 * an {@link AudioFormat} info object.
	 * @param format
	 */
	public void setFrequencyAndFormat(AudioFormat format)
	{
		setSamplingRate((int)format.getSampleRate());
		setFormatByChannelsAndBits(format.getChannels(), format.getSampleSizeInBits());
	}
	
	protected final int allocate()
	{
		int[] STATE_NUMBER = new int[1];
		al.alGetError();
		al.alGenBuffers(1, STATE_NUMBER, 0);
		errorCheck(this);
		return STATE_NUMBER[0];
	}
	
	/**
	 * Loads this buffer with PCM bytes.
	 * @param pcmData the PCM data to load into it.
	 * @param len amount of bytes to load.
	 */
	public synchronized void loadPCMData(Buffer pcmData, int len)
	{
		bufferSize = len;
		clearError();
		al.alBufferData(getALId(), bufferFormat.alVal, pcmData, len, bufferRate);
		errorCheck(this);
	}

	/**
	 * Loads this buffer with PCM bytes.
	 * @param pcmData the array of PCM bytes to load into it.
	 */
	public synchronized void loadPCMData(Buffer pcmData)
	{
		loadPCMData(pcmData,pcmData.capacity());
	}

	/**
	 * Destroys this sound buffer.
	 */
	protected final void free()
	{
		int[] STATE_NUMBER = new int[1];
		STATE_NUMBER[0] = getALId();
		al.alDeleteBuffers(1, STATE_NUMBER, 0);
	}

	/** Get the buffer size. */
	public int getSize()
	{
		return bufferSize;
	}

	/**
	 * Sets this buffer's bitrate format.
	 * @param format This buffer's format.
	 * @throws IllegalArgumentException if this is not set using a valid constant.
	 */
	public void setFormat(SoundFormat format)
	{
		bufferFormat = format;
	}
	
	/**
	 * Returns this buffer's format (AL constant).
	 */
	public SoundFormat getFormat()
	{
		return bufferFormat;
	}
	
	/**
	 * Sets this buffer's frequency.
	 * @param freq	frequency in kHz.
	 */
	public void setSamplingRate(int freq)
	{
		bufferRate = freq;
	}
	
	/**
	 * Returns this buffer's frequency.
	 */
	public int getFrequency()
	{
		return bufferRate;
	}
	
	/**
	 * Sets format by channels and bits.
	 * @param channels amount of channels.
	 * @param bits bit per sample.
	 * @throws IllegalArgumentException if the combination of channels and bits create an unsupported format.
	 */
	public void setFormatByChannelsAndBits(int channels, int bits)
	{
		switch(channels)
		{
			case 1:
				switch (bits)
				{
					case 8:
						setFormat(SoundFormat.MONO8);
						return;
					case 16:
						setFormat(SoundFormat.MONO16);
						return;
				}
				break;
				
			case 2:
				switch (bits)
				{
					case 8:
						setFormat(SoundFormat.STEREO8);
						return;
					case 16:
						setFormat(SoundFormat.STEREO16);
						return;
				}
				break;
		}
		throw new IllegalArgumentException(
				"Unsupported set of channels and bytes. "+channels+" channels, "+bits+"-bits.");
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Buffer ");
		sb.append(getALId()+" ");
		switch (getFormat())
		{
			case MONO8:
				sb.append("Mono 8-bit");
				break;
			case MONO16:
				sb.append("Mono 16-bit");
				break;
			case STEREO8:
				sb.append("Stereo 8-bit");
				break;
			case STEREO16:
				sb.append("Stereo 16-bit");
				break;
		}
		sb.append(' ');
		sb.append(getFrequency()+"Hz ");
		sb.append(getSize()+" bytes");
		return sb.toString();
	}
	
}
