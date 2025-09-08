package com.lsuncar.securityhub.model.results;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude ( JsonInclude.Include.NON_EMPTY )
public class Result
{
	private final Boolean success;
	private String message;

	public Result ( boolean success )
	{
		this.success = success;
	}

	public Result ( Boolean success, String message )
	{
		this( success );
		this.message = message;
	}

	public Boolean isSuccess ()
	{
		return this.success;
	}

	public String getMessage ()
	{
		return this.message;
	}
}
