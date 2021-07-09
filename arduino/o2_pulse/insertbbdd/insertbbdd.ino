/*

    EJEMPLO: NO EJECUTAR, es un código de ejemplo para insertar valores en nuestra BBDD sin estar asociado a ningún sensor.

    Configurar ARduino IDE como Generic ESP8266 Module.
    Si no esta inatalada, añadir esta lista de descargas en Archivo-preferencias-Gestor de tarjetas
    http://arduino.esp8266.com/stable/package_esp8266com_index.json
*/

#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

#define DEBUG
const char *ssid = "wireless-uem";          //Nombre de la Red Wifi
const char *password = "";                  //Password de la Red Wifi
const char* dbUser = "pr_bioguards";                //User para conectarse a la Base de Datos.
const char* dbPass = "bIoguarDs.87";                //Pasword del usuario
const char* dbName = "prbioguards";                //Base de datos de la tabla a insertar

/********************************
 ********************************/
void setup() {
  Serial.begin( 115200 );
  Serial.setTimeout( 50 );

  initWifi();
}

/********************************
 ********************************/
void loop() {
  String numero;
  float numeroF;
  int tamNumeroS;

  Serial.print( ">> " );
  if ( ( WiFi.status() == WL_CONNECTED ) ) {
    insertValueBBDD( );
  }
  else {
    Serial.println( "[HTTP} Unable to connect" );
  }
  delay( 10000 );
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
int insertValueBBDD () {
  //String insert = String( "seminariodemo(Nombre, valor) VALUES('Sensor A', 22.0)" );
  //String insert = String ( "medida (valor, fecha, idsensor) VALUES( " + 3 + ", '" + "now()" + "', " + 3 + ")";
  String insert = String ( "medida (valor, id_sensor) VALUES(3.5, 3)");
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
