package com.lsuncar.securityhub.model.results;

public class ErrorResult extends  Result
{
	public ErrorResult ( boolean success )
	{
		super( false );
	}

	public ErrorResult ( String message )
	{
		super( false, message );
	}
}
