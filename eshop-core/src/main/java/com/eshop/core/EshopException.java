package com.eshop.core;

	public class EshopException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public EshopException()
		{
			super();
		}

		public EshopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
		{
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public EshopException(String message, Throwable cause)
		{
			super(message, cause);
		}

		public EshopException(String message)
		{
			super(message);
		}

		public EshopException(Throwable cause)
		{
			super(cause);
		}
		
	}

