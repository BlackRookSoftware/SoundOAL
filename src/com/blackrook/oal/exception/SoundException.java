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
package com.blackrook.oal.exception;

/**
 * This is commonly thrown when a sound buffer couldn't be allocated.
 * @author Matthew Tropiano
 */
public class SoundException extends RuntimeException {

	private static final long serialVersionUID = -4698043866886434891L;

	public SoundException()
	{
		super("A new sound buffer couldn't be allocated.");
	}

	public SoundException(String message)
	{
		super(message);
	}
}
