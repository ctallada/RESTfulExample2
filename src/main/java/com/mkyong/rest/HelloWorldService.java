package com.mkyong.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWorldService {

	@GET
	@Path("/world/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;

		return Response.status(200).entity(output).build();

	}

	@GET
	@Path("/world/service/{vehicle}")
	public Response getVehicle(@PathParam("vehicle") String vehicle,
			@QueryParam("wheels") String wheels) {

		String output = "Vehicle Type::::  " + vehicle;
		output = output + "\n";
		output = "No Of Wheels:::" + wheels;

		return Response.status(200).entity(output).build();

	}
}