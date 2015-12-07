/*******************************************************************************
 * Copyright (c) 2014 Black Rook Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 ******************************************************************************/
package com.blackrook.oal;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.blackrook.commons.Common;

/**
 * Sound resource abstraction.
 * Wraps Java Sound SPI structures for ease of creating handles to decodable sound.
 * @author Matthew Tropiano
 */
public class JSPISoundHandle implements Closeable
{
	/** Name of this data stream. */
	private String dataName;
	
	/** Audio format. */
	private AudioFormat audioFormat;
	/** Audio file format. */
	private AudioFileFormat audioFileFormat;
	/** Audio input stream. */
	private AudioInputStream audioStream;

	/**
	 * Opens a sound file for reading.
	 * @param f the file.
	 * @throws IOException if the file can't be read.
	 * @throws UnsupportedAudioFileException if the audio format is not recognized.
	 */
	public JSPISoundHandle(File f) throws IOException, UnsupportedAudioFileException
	{
		this(f.getPath(), AudioSystem.getAudioInputStream(f));
	}
	
	/**
	 * Opens a URL for reading.
	 * @param url the URL.
	 * @throws IOException if the stream can't be read.
	 * @throws UnsupportedAudioFileException if the audio format is not recognized.
	 */
	public JSPISoundHandle(URL url) throws IOException, UnsupportedAudioFileException
	{
		this(url.toString(), AudioSystem.getAudioInputStream(url));
	}

	/**
	 * Opens an input stream for reading.
	 * @param name the name of this stream.
	 * @param in the input stream.
	 * @throws IOException if the stream can't be read.
	 * @throws UnsupportedAudioFileException if the audio format is not recognized.
	 */
	public JSPISoundHandle(String name, InputStream in) throws IOException, UnsupportedAudioFileException
	{
		this(name, AudioSystem.getAudioInputStream(in));
	}
	
	/**
	 * Opens an input stream for reading.
	 * @param name the name of this stream.
	 * @param in the audio input stream.
	 * @throws IOException if the stream can't be read.
	 * @throws UnsupportedAudioFileException if the audio format is not recognized.
	 */
	public JSPISoundHandle(String name, AudioInputStream in) throws IOException, UnsupportedAudioFileException
	{
		initHandle(name, in);
	}
	
	/**
	 * Opens a sound file for reading.
	 * @param filePath path to the file, URL or relative path.
	 * @throws IOException if the resource can't be read.
	 * @throws UnsupportedAudioFileException if the audio format is not recognized.
	 */
	public JSPISoundHandle(String filePath) throws IOException, UnsupportedAudioFileException
	{
		try {
			URL url = new URL(filePath);
			audioFileFormat = AudioSystem.getAudioFileFormat(url);
			initHandle(url.toString(), AudioSystem.getAudioInputStream(url));
		} catch (MalformedURLException ex) {
			File f = new File(filePath);
			audioFileFormat = AudioSystem.getAudioFileFormat(f);
			initHandle(f.getPath(), AudioSystem.getAudioInputStream(f));
		}
	}
	
	private void initHandle(String name, AudioInputStream in) throws IOException, UnsupportedAudioFileException
	{
		dataName = name;
		audioStream = in;
		audioFormat = audioStream.getFormat();
	}

	/** Closes this file on cleanup. */
	public void finalize() throws Throwable
	{
		Common.close(this);
		super.finalize();
	}

	/**
	 * Closes this SoundData.
	 * @throws IOException if an I/O exception occurs during the close.
	 */
	public void close() throws IOException
	{
		audioStream.close();
	}
	
	/**
	 * Returns a decoder class that can decode this data into PCM data.
	 */
	public Decoder getDecoder()
	{
		return new Decoder();
	}
	
	/**
	 * Returns the name of this handle.
	 */
	public String getName()	
	{
		return dataName;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getName());
		sb.append(' ');
		sb.append(audioFormat.toString());
		return sb.toString();
	}

	/**
	 * @return the dataName
	 */
	public final String getDataName()
	{
		return dataName;
	}

	/**
	 * @return the audioFormat
	 */
	public final AudioFormat getFormat()
	{
		return audioFormat;
	}
	
	public final AudioFileFormat getFileFormat()
	{
		return audioFileFormat;
	}

	/**
	 * Decoder class that decodes sound as PCM audio.
	 * @author Matthew Tropiano
	 */
	public class Decoder
	{
		/** Audio format to decode to. */
		private AudioFormat decodedAudioFormat;
		/** Audio input stream to decode to. */
		private AudioInputStream decodedAudioStream;
		
		Decoder()
		{
			decodedAudioFormat = new AudioFormat(
				audioFormat.getSampleSizeInBits() == 8 ? AudioFormat.Encoding.PCM_UNSIGNED : AudioFormat.Encoding.PCM_SIGNED, 
				audioFormat.getSampleRate(), 
				audioFormat.getSampleSizeInBits() != AudioSystem.NOT_SPECIFIED ? audioFormat.getSampleSizeInBits() : 16, 
				audioFormat.getChannels(),
				audioFormat.getChannels() * 2,
				audioFormat.getSampleRate(),
				ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN
			);
			decodedAudioStream = AudioSystem.getAudioInputStream(decodedAudioFormat, audioStream);
		}
		
		/**
		 * Reads a bunch of decoded bytes into the byte array.
		 * @param b	the byte array.
		 * @return how many bytes were written.
		 * @throws IOException if the data can't be decompressed.
		 */
		public int readPCMBytes(byte[] b) throws IOException
		{
			int i = 0;
			int buf = 0;
			while (i != b.length)
			{
				buf = decodedAudioStream.read(b, i, b.length-i);
				if (buf != -1)
				{
					i += buf;
				}
				else break;
			}

			return i;
		}

		/**
		 * @return the decodedAudioFormat
		 */
		public final AudioFormat getDecodedAudioFormat()
		{
			return decodedAudioFormat;
		}
		
		/**
		 * Closes the decoder.
		 * @throws IOException
		 */
		public void close() throws IOException
		{
			decodedAudioStream.close();
		}

	}
	
}
