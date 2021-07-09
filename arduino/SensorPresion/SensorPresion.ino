#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

int ledPin = 2;                // Connect LED on pin GPIO2 // D4
int KEY = D1;                 // Connect Touch sensor on Digital Pin GPIO5 // D1
int reps = 0;

#define DEBUG
const char *ssid = "wireless-uem";          //Nombre de la Red Wifi
const char *password = "";                  //Password de la Red Wifi
const char* dbUser = "pr_bioguards";                //User para conectarse a la Base de Datos.
const char* dbPass = "bIoguarDs.87";                //Pasword del usuario
const char* dbName = "prbioguards";                //Base de datos de la tabla a insertar


void setup() {
  pinMode(ledPin, OUTPUT);      // Set ledPin to output mode
  pinMode(KEY, INPUT);       //Set touch sensor pin to input mode
  Serial.begin(115200);
  initWifi();
  Serial.setTimeout( 50 );
}

void loop() {
  
  if (digitalRead(KEY) == HIGH) {   //Read Touch sensor signal
    digitalWrite(ledPin, LOW);   // if Touch sensor is HIGH, then turn on
    reps += 1;
    if (reps > 500000) {   // 500.000 = 5s aprox
      if ((WiFi.status() == WL_CONNECTED )) {
        insertValueBBDD();
        Serial.println(" Insertado");
        reps = 0;
      }
      else {
        Serial.println( "[HTTP} Unable to connect" );
      }
    }
  }
  else {
    digitalWrite(ledPin, HIGH);    // if Touch sensor is LOW, then turn off the led
    reps = 0;
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
int insertValueBBDD () {
  String insert = String ( "medida (valor, id_sensor) VALUES(1,2)");
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
