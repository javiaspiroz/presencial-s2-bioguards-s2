#include <DHT.h>
#include <DHT_U.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <Wire.h>

#define DEBUG
const char *ssid = "wireless-uem";          //Nombre de la Red Wifi
const char *password = "";                  //Password de la Red Wifi
const char* dbUser = "pr_bioguards";                //User para conectarse a la Base de Datos.
const char* dbPass = "bIoguarDs.87";                //Pasword del usuario
const char* dbName = "prbioguards";                //Base de datos de la tabla a insertar
int reps=0;
DHT dht(D4, DHT11);
float temp, hume;
void setup() {
  Serial.println("Initializing...");

  initWifi();
  dht.begin();
  Serial.begin(9600);
}

void loop() {
  hume = dht.readHumidity();
  temp = dht.readTemperature();
  Serial.print("Hace una temperatura de: ");
  Serial.println(temp);
  Serial.print("Hay una humedad de: ");
  Serial.println(hume);
  delay(5000);
   reps += 1;
   if (reps > 5) { //30s
  if ( ( WiFi.status() == WL_CONNECTED )) {
        insertValueBBDD(temp);
        Serial.println(" Insertado");
        reps = 0;
      }
      else {
        Serial.println( "[HTTP} Unable to connect" );
      }
}
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
  String insert = String ( "medida (valor, id_sensor) VALUES(" + String(value) + ", 1)");
#ifdef DEBUG
  Serial.print ( "insert: " ); Serial.println ( insert );
#endif
  int code = sendSQLinsert ( insert );
#ifdef DEBUG
  Serial.print ( "InserDDBB: " ); Serial.println ( code );
#endif
  return code;
}
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
