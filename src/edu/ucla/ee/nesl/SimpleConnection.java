package edu.ucla.ee.nesl;

/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/**
 * 
 * Represents a {@link MqttAndroidClient} and the actions it has performed
 *
 */
public class SimpleConnection {

	String TAG = "SimpleConnection";
  /** The clientId of the client associated with this <code>Connection</code> object **/
  private String clientID = null;
  
  /** The host that the {@link MqttAndroidClient} represented by this <code>Connection</code> is represented by **/
  private String host = null;
  
  /** The port on the server this client is connecting to **/
  private int port = 0;
  

  /** The {@link MqttAndroidClient} instance this class represents**/
  private MqttClient client = null;

  
  public static SimpleConnection createConnection(String clientID, String server, int port) {
	  String uri = null;
	  uri = "tcp://" + server + ":" + port;
	  try {
		  MqttClient client = new MqttClient(uri, clientID);
		  client.connect();
		  return new SimpleConnection(clientID, server, port, client);
	  }
	  catch (MqttException e) {
		  e.printStackTrace();
		  return null;
	  }
	  
  }
  public SimpleConnection(String clientID, String server,
      int port, MqttClient client) {
	  
    this.clientID = clientID;
    this.host = server;
    this.port = port;
    this.client = client;
  }

  public void publish(String topic, String message) {
	  MqttMessage mqttMessage = new MqttMessage();
	  mqttMessage.setPayload(message.getBytes());
	  try {
		  this.client.publish(topic, mqttMessage);
	  }
	  catch(Exception e) {
		  Log.d(TAG, "Unable to destroy connection");
		  e.printStackTrace();
	  }
  }
  public void destroy() {
	  try{
		  client.disconnect();
	  }
	  catch(Exception e) {
		  Log.d(TAG, "Unable to destroy connection");
		  e.printStackTrace();
	  }
  }
  
}