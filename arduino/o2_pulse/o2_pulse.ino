#include <Wire.h>
#include "MAX30105.h"

#include "heartRate.h"

#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

MAX30105 particleSensor;

const byte RATE_SIZE = 4; //Increase this for more averaging. 4 is good.
byte rates[RATE_SIZE]; //Array of heart rates
byte rateSpot = 0;
long lastBeat = 0; //Time at which the last beat occurred

float beatsPerMinute;
int beatAvg;
int reps = 0;

#define DEBUG
const char *ssid = "wireless-uem";          //Nombre de la Red Wifi
const char *password = "";                  //Password de la Red Wifi
const char* dbUser = "pr_bioguards";                //User para conectarse a la Base de Datos.
const char* dbPass = "bIoguarDs.87";                //Pasword del usuario
const char* dbName = "prbioguards";                //Base de datos de la tabla a insertar

void setup()
{
  Serial.begin(115200);
  Serial.println("Initializing...");

  initWifi();

  // Initialize sensor
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  Serial.println("Place your index finger on the sensor with steady pressure.");

  particleSensor.setup(); //Configure sensor with default settings
  particleSensor.setPulseAmplitudeRed(0x0A); //Turn Red LED to low to indicate sensor is running
  particleSensor.setPulseAmplitudeGreen(0); //Turn off Green LED

  Serial.setTimeout( 50 );

}

void loop()
{
  long irValue = particleSensor.getIR();

  if (checkForBeat(irValue) == true)
  {
    //We sensed a beat!
    long delta = millis() - lastBeat;
    lastBeat = millis();

    beatsPerMinute = 60 / (delta / 1000.0);

    if (beatsPerMinute < 255 && beatsPerMinute > 20)
    {
      rates[rateSpot++] = (byte)beatsPerMinute; //Store this reading in the array
      rateSpot %= RATE_SIZE; //Wrap variable

      //Take average of readings
      beatAvg = 0;
      for (byte x = 0 ; x < RATE_SIZE ; x++)
        beatAvg += rates[x];
      beatAvg /= RATE_SIZE;
    }
  }

  Serial.print("IR=");
  Serial.print(irValue);
  Serial.print(", BPM=");
  Serial.print(beatsPerMinute);
  Serial.print(", Avg BPM=");
  Serial.print(beatAvg);

  if (irValue < 50000)
    Serial.print(" No finger?");
  else {
    reps += 1;
    if (reps > 500) { //500 reps = 10s
      if ( ( WiFi.status() == WL_CONNECTED )) {
        insertValueBBDD(beatsPerMinute);
        Serial.println(" Insertado");
        reps = 0;
      }
      else {
        Serial.println( "[HTTP} Unable to connect" );
      }
    }
  }

  Serial.println();
}

/****************************/
//   Funcion de Inicializar Wifi.
/****************************/
void initWifi() {
#ifdef DEBUG
  Serial.setDebugOutput( true );
  Serial.println("\n\n");
#endif

  WiFi.mode( WIFI_STA );
  WiFi.begin( ssid, password );

  // Wait for connection
#ifdef DEBUG
  Serial.print( "Conectando:");
#endif
  while ( WiFi.status() != WL_CONNECTED ) {
#ifdef DEBUG
    Serial.print( ".");
#endif
    delay ( 250 );
  }
#ifdef DEBUG
  Serial.println();
  Serial.println( "[SETUP] Wifi Conectada...");
#endif
}

/****************************/
//   Funcion de insertar en la BBDD.
/****************************/
int insertValueBBDD (float value) {
  String insert = String ( "medida (valor, id_sensor) VALUES(" + String(value) + ", 3)");
#ifdef DEBUG
  Serial.print ( "insert: " ); Serial.println ( insert );
#endif
  int code = sendSQLinsert ( insert );
#ifdef DEBUG
  Serial.print ( "InserDDBB: " ); Serial.println ( code );
#endif
  return code;
}
/****************************/
/****************************/
int sendSQLinsert ( String insert ) {
  String hostDDBB = "2.139.176.212";  // IP de la Raspi BBDD
  const unsigned int TAM_BUFF = 250;
  char uri_[TAM_BUFF];
  const char *formatGET = "/insert.php?db=%s&user=%s&pass=%s&insert=%s";

  int httpCode;
  WiFiClient client;
  HTTPClient http;

  insert.trim();
  insert.replace ( " ", "%20" );
  insert.replace ( "'", "%27" );
  snprintf ( uri_, TAM_BUFF, formatGET, dbName, dbUser, dbPass, insert.c_str() );
#ifdef DEBUG
  Serial.print("URI: ");
  Serial.println(uri_);
#endif

  if ( http.begin ( client, hostDDBB, 8888, uri_, false ) )
  {
    httpCode = http.GET();  // Realizar petición
    delay ( 100 );
    http.end();
    return httpCode;
  }
  return -1;
}
/****************************/
/****************************/
